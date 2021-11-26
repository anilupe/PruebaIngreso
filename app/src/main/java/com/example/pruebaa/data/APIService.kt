package com.example.pruebaa.data

import com.example.pruebaa.ui.Model.PostsData
import com.example.pruebaa.ui.Model.User
import retrofit2.Call
import retrofit2.http.*

interface APIService {
  //metodo Listar Usuarios
    @GET
  fun getUsers(@Url url:String): Call<List<User>>



  //metodo listar posts por id de Usuario
  @GET
  fun getPost(@Url url:String, @Query("userId") id:Int): Call<List<PostsData>>


}