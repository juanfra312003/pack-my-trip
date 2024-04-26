package dev.pack_my_trip.ConectionBack.Interfaces

import dev.pack_my_trip.models.data_model.PaqueteTuristico
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface IUrlFinal {
    @GET("usuario")
    fun getUsuario(@Query("usuario") usuario: String, @Query("contrasenha") contrasenha: String): Call<Usuario>
    @POST("usuario")
    fun crearUsuario(@Body usuario: Usuario): Call<Boolean>
    @PUT("usuario")
    fun actualizarRegion(@Body usuario: Usuario): Call<Boolean>
    @PATCH("usuario")
    fun actualizarUsuario(@Body usuario: Usuario): Call<Boolean>
    @GET("servicio")
    fun getServicios(@Query("correoOperador") correoOperador: String): Call<List<Servicio>>
    @GET("serviciogetall")
    fun getServicios(): Call<List<Servicio>>
    @POST("servicio")
    fun crearServicio(@Body servicio: Servicio): Call<Boolean>
    @PUT("servicio")
    fun editarServicio(@Body servicio: Servicio): Call<Boolean>
    @GET("paqueteturistico/lista")
    fun getPaquetesTuristicos(@Query("correoIntermediario") correoIntermediario: String) : Call<List<PaqueteTuristico>>
    @GET("usuariogetpaquete")
    fun getPaquetesTuristicosUsuario(@Query("correoUsuario") correoUsuario: String) : Call<List<PaqueteTuristico>>
    @POST("paqueteturistico")
    fun crearPaquete(@Body paquete: PaqueteTuristico) : Call<Boolean>
    @PUT("paqueteturistico")
    fun updatePaquete(@Body paquete: PaqueteTuristico) : Call<Boolean>
}