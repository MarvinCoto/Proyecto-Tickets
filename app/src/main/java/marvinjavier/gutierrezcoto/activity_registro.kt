package marvinjavier.gutierrezcoto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class activity_registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombreRegistro = findViewById<EditText>(R.id.txtNombreRegistro)
        val txtCorreoRegistro = findViewById<EditText>(R.id.txtCorreoLogin)
        val txtContrasenaRegistro = findViewById<EditText>(R.id.txtContrasenaLogin)
        val btnRegistrarse = findViewById<Button>(R.id.btnIniciarSesion)
        val btnIrAlLogin = findViewById<Button>(R.id.btnIrAIRegistro)


        btnRegistrarse.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                //Creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //Creo una variable que contenga un PrepareStatement
                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbtUsuarios(UUID_usuario, Nombre, Correo, Contrasena) VALUES (?, ?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtNombreRegistro.text.toString())
                crearUsuario.setString(3, txtCorreoRegistro.text.toString())
                crearUsuario.setString(4, txtContrasenaRegistro.text.toString())
                crearUsuario.executeUpdate()
                withContext(Dispatchers.Main) {
                    //Abro otra corrutina o "Hilo" para mostrar un mensaje y limpiar los campos
                    //Lo hago en el Hilo Main por que el hilo IO no permite mostrar nada en pantalla
                    Toast.makeText(
                        this@activity_registro,
                        "Usuario creado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    txtNombreRegistro.setText("")
                    txtCorreoRegistro.setText("")
                    txtContrasenaRegistro.setText("")
                }
            }

        }

        btnIrAlLogin.setOnClickListener{
            val pantallaLogin = Intent(this, activity_login::class.java)
            startActivity(pantallaLogin)
        }
    }
}