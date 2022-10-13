package com.example.missingcats.repository

import com.example.missingcats.models.Cat
import retrofit2.Call
import retrofit2.http.*

interface CatService {
        @GET("cats")
        fun getAllCats(): Call<List<Cat>>

        @GET("cats/{id}")
        fun getCatById(@Path("id") id: Int): Call<Cat>

        @POST("cats")
        fun saveCat(@Body cat: Cat): Call<Cat>

        @DELETE("cats/{id}")
        fun deleteCat(@Path("id") id: Int): Call<Cat>

        @PUT("cats/{id}")
        fun updateCat(@Path("id") id: Int, @Body cat: Cat): Call<Cat>
}