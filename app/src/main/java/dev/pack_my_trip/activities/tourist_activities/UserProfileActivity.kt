import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.pack_my_trip.R
import dev.pack_my_trip.models.models_tourist.Turista

class UserProfileActivity : AppCompatActivity() {

    private lateinit var textViewUserName: TextView
    private lateinit var textViewUserEmail: TextView
    private lateinit var imageViewProfile: ImageView
    private lateinit var imageViewSettings: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inicializa las vistas
        textViewUserName = findViewById(R.id.textViewUserName)
        textViewUserEmail = findViewById(R.id.textViewUserEmail)
        imageViewProfile = findViewById(R.id.imageViewProfile)
        imageViewSettings = findViewById(R.id.imageViewSettings)

        cargarPerfilUsuario()

        imageViewSettings.setOnClickListener {
            // Aquí puedes abrir una nueva actividad de configuración o un fragmento
        }
    }

    private fun cargarPerfilUsuario() {
        // Supongamos que obtenerUsuarioLogueado() es un método que recuperas desde SharedPreferences
        val usuarioLogueado = obtenerUsuarioLogueado(this)
        if (usuarioLogueado != null) {
            textViewUserName.text = usuarioLogueado.nombre
            textViewUserEmail.text = usuarioLogueado.correo
            // Configurar imageViewProfile si tienes una URL o recurso para la imagen del perfil
            // Si el usuario tiene un rol que altera la interfaz, también podrías configurarlo aquí
        } else {
            // No hay usuario logueado. Podrías redirigir al login o mostrar contenido por defecto.
        }
    }

    // Este método simula la obtención de un usuario logueado.
    // Deberías implementar la lógica de recuperación del usuario de SharedPreferences o tu base de datos aquí.
    private fun obtenerUsuarioLogueado(context: Context): Turista? {
        // Recuperación de los detalles del usuario de SharedPreferences o base de datos
        val sharedPreferences = context.getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE)
        val nombre = sharedPreferences.getString("nombre", "") ?: return null
        val correo = sharedPreferences.getString("correo", "") ?: return null
        // Omitimos la contraseña por razones de seguridad
        // Otros campos como diaNacimiento, mesNacimiento, añoNacimiento, y rol pueden ser agregados si es necesario
        return Turista(nombre, correo, "", 1, 1, 1990, "Rol no especificado")
    }
}
