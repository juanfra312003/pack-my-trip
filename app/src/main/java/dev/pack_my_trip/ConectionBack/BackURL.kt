package dev.pack_my_trip.ConectionBack

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BackURL {
    companion object{
        private lateinit var retrofit : Retrofit

        fun conectarBackURL() : Retrofit {
            try{
                retrofit = Retrofit.Builder()
                    .baseUrl("https://packyourtrip.azurewebsites.net/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }catch(e : Exception){
                println("ERROR: ${e.message}")
            }
            return retrofit
        }
    }
}