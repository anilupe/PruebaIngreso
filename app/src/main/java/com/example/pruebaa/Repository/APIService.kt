package com.example.pruebaa.Repository

import com.example.pruebaa.Model.PostsData
import com.example.pruebaa.Model.User
import retrofit2.Call
import retrofit2.http.*

interface APIService {
  //metodo Listar Usuarios
    @GET
  fun getUsers(@Url url:String): Call<List<User>>

  //metodo listar post por id de Usuario
  @GET
  fun getPost(@Url url:String, @Query("userId") id:Int): Call<List<PostsData>>


}