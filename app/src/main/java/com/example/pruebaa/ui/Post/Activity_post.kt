package com.example.pruebaa.ui.Post

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebaa.Adapter.PostsAdapter
import com.example.pruebaa.Model.PostsData
import com.example.pruebaa.Repository.APIService
import com.example.pruebaa.RestClient.Retrofit
import com.example.pruebaa.databinding.ActivityPostBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Activity_post : AppCompatActivity() {
    private lateinit var adapter: PostsAdapter
    private lateinit var itemList: List<PostsData>

    //instanciar la clase retrofit donde se encuentra el cliente de servidores Rest
    val retrofit = Retrofit()

    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //implementacion del binding
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataActivity()
    }

    private fun getDataActivity() {
        val objetIntent: Intent = intent
        var idUser: Int = 0
        var nameUser: String?
        var phoneUser: String?
        var emailUser: String?

        idUser = objetIntent.getIntExtra("idUser", 0)
        nameUser = objetIntent.getStringExtra("nameUser")
        phoneUser = objetIntent.getStringExtra("phoneUser")
        emailUser = objetIntent.getStringExtra("emailUser")
        binding.tvName.setText(nameUser)
        binding.tvPhone.setText(phoneUser)
        binding.tvMail.setText(emailUser)
        getpostByUserId(idUser)
    }


    private fun getpostByUserId(idUser: Int) {
        doAsync {
            //variable que sera encargada de llamar al metodo qe nos devuelve RETROFIT
            val call = retrofit.getRetrofit().create(APIService::class.java)
                .getPost("posts?userId=", idUser).execute()
            print(call)
            uiThread {
                if (call.code() == 200) {
                    print(call.body())
                    showData(call.body()!!)
                    itemList = call.body()!!
                    //si la respuesta es correcta

                } else {

                }

            }
        }
    }

    //llamar al adaptador para llenar los datos en el recyclerView
    private fun showData(post: List<PostsData>) {
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(this@Activity_post)
            adapter =
                PostsAdapter(
                    post
                )
        }
    }
}