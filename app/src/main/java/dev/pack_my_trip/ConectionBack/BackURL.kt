package dev.pack_my_trip.ConectionBack

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class BackURL {
    companion object{
        private lateinit var retrofit : Retrofit
        private val urlServerAzure = "https://packyourtrip.azurewebsites.net/api/"
        private val urlLocalHost = "https://10.0.2.2:7047/api/"

        fun conectarBackURL() : Retrofit {
            try{
               /* val trustAllCerts = arrayOf<TrustManager>(TrustAllCertificates()) Lineas para probar localhost
                val sslContext = SSLContext.getInstance("SSL").apply {
                    init(null, trustAllCerts, SecureRandom())
                }

                val clientBuilder = OkHttpClient.Builder().apply {
                    sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                    hostnameVerifier { _, _ -> true }
                }

                val client = clientBuilder.build()
                */
                retrofit = Retrofit.Builder()
                    .baseUrl(urlServerAzure)
                    //.client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }catch(e : Exception){
                println("ERROR: ${e.message}")
            }
            return retrofit
        }
    }
}