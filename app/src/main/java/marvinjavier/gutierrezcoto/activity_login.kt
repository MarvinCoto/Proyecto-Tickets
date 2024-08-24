package marvinjavier.gutierrezcoto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreoLogin = findViewById<EditText>(R.id.txtCorreoLogin)
        val txtContrasenaLogin = findViewById<EditText>(R.id.txtContrasenaLogin)
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val btnIrAlRegistro = findViewById<Button>(R.id.btnIrAIRegistro)

        btnIniciarSesion.setOnClickListener {
            //preparo el intent para cambiar a la pantalla de bienvenida
            val pantallaTickets = Intent(this, activity_agregar_tickets::class.java)
            //Dentro de una corrutina hago un select en la base de datos
            GlobalScope.launch(Dispatchers.IO) {
                //1-Creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()
                //2- Creo una variable que contenga un PrepareStatement
                //MUCHA ATENCION! hace un select where el correo y la contraseña sean iguales a
                //los que el usuario escribe
                //Si el select encuentra un resultado es por que el usuario y contraseña si están
                //en la base de datos, si se equivoca al escribir algo, no encontrará nada el select
                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tbtUsuarios WHERE Correo = ? AND Contrasena = ?")!!
                comprobarUsuario.setString(1, txtCorreoLogin.text.toString())
                comprobarUsuario.setString(2, txtContrasenaLogin.text.toString())
                val resultado = comprobarUsuario.executeQuery()
                //Si encuentra un resultado
                if (resultado.next()) {
                    startActivity(pantallaTickets)
                } else {
                    println("Usuario no encontrado, verifique las credenciales")
                }
            }
        }


        btnIrAlRegistro.setOnClickListener{
            val pantallaRegistro = Intent(this, activity_registro::class.java)
            startActivity(pantallaRegistro)
        }

    }
}