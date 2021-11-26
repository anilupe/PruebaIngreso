package com.example.pruebaa.viewModel

import android.app.ProgressDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pruebaa.RestClient.Retrofit
import com.example.pruebaa.data.APIService
import com.example.pruebaa.ui.Model.User
import com.example.pruebaa.ui.Users.MainActivity
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class usersViewModel:ViewModel() {

    var listUsers=MutableLiveData<List<User>>()
    var errorMessage:String=""
    //instanciar la clase retrofit donde se encuentra el cliente de servidores Rest
    val retrofit = Retrofit()
    var gson = Gson()

    //traemos la lista de usuarios
     fun getUsers(progressDialog: ProgressDialog){
        progressDialog.show()
        doAsync {
            //variable que sera encargada de llamar al metodo qe nos devuelve RETROFIT
            val call = retrofit.getRetrofit().create(APIService::class.java).getUsers("users").execute()
            uiThread{
                if( call.code()==200) {
                    //si la respuesta es correcta
                    listUsers.postValue(call.body())
                    savePrefs(call.body()!!)
                    progressDialog.dismiss()
                }else{
                    errorMessage="Error"
                }
            }
        }
    }

     fun savePrefs(body: List<User>) {
        val jsontut:String =gson.toJson(body)
        MainActivity.prefs.saveUsers(jsontut)
    }





}