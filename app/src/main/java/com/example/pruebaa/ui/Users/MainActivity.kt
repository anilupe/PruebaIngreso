package com.example.pruebaa.ui.Users

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebaa.Adapter.UsersAdapter
import com.example.pruebaa.Model.User
import com.example.pruebaa.R
import com.example.pruebaa.Repository.APIService
import com.example.pruebaa.RestClient.Retrofit
import com.example.pruebaa.databinding.ActivityMainBinding
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    //instanciar la clase retrofit donde se encuentra el cliente de servidores Rest
    val retrofit = Retrofit()


    private lateinit var adapter: UsersAdapter
    private lateinit var itemList:List<User>
    var allDataList:MutableList<User> = ArrayList()

    //forma actual de acceder a las vistas desde las clases
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //implementacion del binding
       binding= ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)

        //Dialogo de carga
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Por favor espere...")

        getUsers(progressDialog)
        binding.svUsers.setOnQueryTextListener(this)
    }
    private fun getUsers(progressDialog:ProgressDialog){
        progressDialog.show()

        doAsync {
        //variable que sera encargada de llamar al metodo qe nos devuelve RETROFIT
             val call = retrofit.getRetrofit().create(APIService::class.java).getUsers("users").execute()
            uiThread{
                if( call.code()==200) {
                    //si la respuesta es correcta
                    showData(call.body()!!)
                    itemList = call.body()!!
                    System.out.println("DETALLE DE DATOS"+itemList)

                    progressDialog.dismiss()
                }else{
                    progressDialog.dismiss()
                    showErrorDialog()
                }
                hideKeyboard()
            }
        }
    }

    private fun showErrorDialog() {
        alert("Ha ocurrido un error, inténtelo de nuevo.") {
            yesButton { }
        }.show()

    }

    //llamar al adaptador para llenar los datos en el recyclerView
    private fun showData(user:List<User>){
        binding.rvUsers.apply{
            layoutManager=LinearLayoutManager(this@MainActivity)
            allDataList.addAll(user)
            adapter =
                UsersAdapter(
                    allDataList
                )
        }
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        filterList(newText.toString())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filterList(newText.toString())
        return true

    }

    private fun filterList(filterUser: String) {
        if (filterUser.isNotEmpty()){
            allDataList.clear()
            for (d in itemList){
                if (filterUser in d.name){
                        allDataList.add(d)
                    binding.tvListEmpty.setVisibility(View.INVISIBLE)
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
                binding.tvListEmpty.setVisibility(View.INVISIBLE)
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


    //ocultar teclado luego de busqueda
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
}