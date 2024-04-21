package dev.pack_my_trip.ConectionBack

import android.content.Context
import android.widget.Toast
import dev.pack_my_trip.models.data_model.Servicio
import dev.pack_my_trip.models.data_model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository() {
    private var urlFinal: IUrlFinal

    init {
        val retrofit = BackURL.conectarBackURL()
        urlFinal = retrofit.create(IUrlFinal::class.java)
    }

    fun getUsuario(usuario: String, contrasenha: String, context: Context){
        val getUsuario = urlFinal.getUsuario(usuario, contrasenha)

        getUsuario.enqueue(object: Callback<Usuario>{
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if(!response.isSuccessful){
                    Toast.makeText(context, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show()
                }
                if(response.body() != null){

                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(context, "El sistema no esta disponible, inténtelo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun crearUsuario(usuario: Usuario, context: Context){
        val crearUsuario = urlFinal.crearUsuario(usuario)

        crearUsuario.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(!response.isSuccessful){
                    Toast.makeText(context, "Hubo un error a la hora de registrarse", Toast.LENGTH_SHORT).show()
                }
                if(response.body() != null){

                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, "El sistema no esta disponible, inténtelo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun actualizarRegion(usuario: Usuario, context: Context){
        val actualizarRegion = urlFinal.actualizarRegion(usuario)

        actualizarRegion.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(!response.isSuccessful){
                    Toast.makeText(context, "No se pudo actualizar la región", Toast.LENGTH_SHORT).show()
                }
                if(response.body() != null){

                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, "El sistema no esta disponible, inténtelo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun actualizarUsuario(usuario: Usuario, context: Context){
        val actualizarUsuario = urlFinal.actualizarUsuario(usuario)

        actualizarUsuario.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(!response.isSuccessful){
                    Toast.makeText(context, "No se pudo actualizar el usuario", Toast.LENGTH_SHORT).show()
                }
                if(response.body() != null){

                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, "El sistema no esta disponible, inténtelo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getServicios(correoOperador: String, context: Context, onGetServiciosPresenter: OnGetServiciosPresenter){
        val getServicios = urlFinal.getServicios(correoOperador)

        getServicios.enqueue(object: Callback<List<Servicio>>{
            override fun onResponse(call: Call<List<Servicio>>, response: Response<List<Servicio>>) {
                if(!response.isSuccessful){
                    Toast.makeText(context, "No se pudo obtener servicios", Toast.LENGTH_SHORT).show()
                }
                if(response.body() != null){
                    onGetServiciosPresenter.onGetServiciosPresenter(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<Servicio>>, t: Throwable) {
                Toast.makeText(context, "El sistema no esta disponible, inténtelo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun crearServicio(servicio: Servicio, context: Context, onCrearServicioPresenter: OnCrearServicioPresenter){
        val crearServicio = urlFinal.crearServicio(servicio)

        crearServicio.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(!response.isSuccessful){
                    Toast.makeText(context, "No se pudo registrar el servicio", Toast.LENGTH_SHORT).show()
                }
                if(response.body() != null){
                    onCrearServicioPresenter.onCrearServicioPresenter(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, "El sistema no esta disponible, inténtelo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun editarServicio(servicio: Servicio, context: Context, onEditarServicioPresenter: OnEditarServicioPresenter){
        val editarServicio = urlFinal.editarServicio(servicio)

        editarServicio.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(!response.isSuccessful){
                    Toast.makeText(context, "No se pudo editar el servicio", Toast.LENGTH_SHORT).show()
                }
                if(response.body() != null){
                    onEditarServicioPresenter.onEditarServicioPresenter(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, "El sistema no esta disponible, inténtelo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })
    }
}