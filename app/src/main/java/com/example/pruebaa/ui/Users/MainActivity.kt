package com.example.pruebaa.ui.Users

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import androidx.activity.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebaa.R
import com.example.pruebaa.data.Prefs
import com.example.pruebaa.databinding.ActivityMainBinding
import com.example.pruebaa.ui.Adapter.UsersAdapter
import com.example.pruebaa.ui.Model.User
import com.example.pruebaa.viewModel.usersViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.alert
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    var gson = Gson()
    private lateinit var adapter: UsersAdapter
    private lateinit var itemList:List<User>
    var allDataList:MutableList<User> = ArrayList()
    //forma actual de acceder a las vistas desde las clases
    private lateinit var binding: ActivityMainBinding

    private val viewModelL:usersViewModel by viewModels()


    companion object {
        lateinit var prefs: Prefs
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs= Prefs(applicationContext)
       //implementacion del binding
        binding= ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        binding.svUsers.setOnQueryTextListener(this)
        checkUsersData()
        //Dialogo de carga
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Por favor espere...")
        viewModelL.listUsers.observe(this, {
            binding.rvUsers.apply{
                layoutManager=LinearLayoutManager(this@MainActivity)
                allDataList.addAll(it)
                itemList = it
                adapter =
                    UsersAdapter(
                        allDataList
                    )
            }
            progressDialog.dismiss()
        })
    }

    //verificamos si hay datos en local
    fun checkUsersData(){
        if (prefs.getUsers().isNotEmpty()){
            //convertimos de String a JSON data
            val listUserType = object : TypeToken<List<User>>() {}.type
            var localUsers: List<User> = gson.fromJson(prefs.getUsers(), listUserType)
             viewModelL.listUsers.postValue(localUsers)
        }
        else{
            //Dialogo de carga
            val progressDialog = ProgressDialog(this@MainActivity)
            progressDialog.setMessage("Por favor espere...")
            viewModelL.getUsers(progressDialog)
            if (viewModelL.errorMessage.equals("Error")){
                showErrorDialog()
            }

        }
    }

    //cuando el usuario de click
    override fun onQueryTextSubmit(newText: String?): Boolean {
        filterList(newText.toString())
        return true
    }

    //cuando el texto vaya cambiando
    override fun onQueryTextChange(newText: String?): Boolean {
        filterList(newText.toString())
        return true

    }

    fun filterList(filterUser: String) {
        if (filterUser.isNotEmpty()){
            allDataList.clear()
            for (d in itemList){
                if (filterUser in d.name){
                        allDataList.add(d)
                    binding.tvListEmpty.visibility = INVISIBLE
                }
            }
            if (allDataList.size==0){
                if (binding.tvListEmpty.visibility==INVISIBLE){
                    binding.tvListEmpty.setText(getString(R.string.listEmpty))
                    binding.tvListEmpty.setVisibility(View.VISIBLE)

                }

            }else{
                adapter =
                    UsersAdapter(
                        allDataList
                    )
                binding.tvListEmpty.visibility = INVISIBLE
            }

            binding.rvUsers.adapter!!.notifyDataSetChanged()
        }
        else{
            allDataList.clear()
            allDataList.addAll(itemList)
            adapter= UsersAdapter(allDataList)
            binding.rvUsers.adapter!!.notifyDataSetChanged()

        }

    }
    //mostrar mensaje de error si hay en la consulta
    private fun showErrorDialog() {
        alert("Ha ocurrido un error, int??ntelo de nuevo.") {
            yesButton { }
        }.show()

    }
}