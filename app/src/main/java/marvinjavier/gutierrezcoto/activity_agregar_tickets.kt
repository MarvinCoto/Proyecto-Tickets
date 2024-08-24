package marvinjavier.gutierrezcoto

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class activity_agregar_tickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_tickets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtTitulo = findViewById<EditText>(R.id.txtTituloCard)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtResponsable = findViewById<EditText>(R.id.txtResponsable)
        val txtEmailAutor = findViewById<EditText>(R.id.txtEmailAutor)
        val txtTelefonoAutor = findViewById<EditText>(R.id.txtTelefonoAutor)
        val txtUbicacion = findViewById<EditText>(R.id.txtUbicacion)
        val spEstado = findViewById<Spinner>(R.id.spEstado)
        val btnAgregarTicket = findViewById<Button>(R.id.btnAgregarTicket)
        val btnVerTickets = findViewById<Button>(R.id.btnVerTickets)

        //Lista de datos de mi spinner
        val estadoTicket = arrayOf("Activo", "Finalizado")

        //Configuro el adaptador
        val miAdaptador = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estadoTicket)

        //Asigno adaptador
        spEstado.adapter = miAdaptador

        btnAgregarTicket.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //1- Creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2- Creo una variable que contenga un PrepareStatement
                val addTicket = objConexion?.prepareStatement("insert into tbtTickets(UUID_Ticket, Titulo, Descripcion, Responsable, Email_Autor, Telefono_Autor, Ubicacion, Estado) values(?, ?, ?, ?, ?, ?, ?, ?)")!!
                addTicket.setString(1, UUID.randomUUID().toString())
                addTicket.setString(2, txtTitulo.text.toString())
                addTicket.setString(3, txtDescripcion.text.toString())
                addTicket.setString(4, txtResponsable.text.toString())
                addTicket.setString(5, txtEmailAutor.text.toString())
                addTicket.setString(6, txtTelefonoAutor.text.toString())
                addTicket.setString(7, txtUbicacion.text.toString())
                addTicket.setString(8, spEstado.selectedItem.toString())

                addTicket.executeUpdate()

                withContext(Dispatchers.Main){
                    txtTitulo.setText("")
                    txtDescripcion.setText("")
                    txtResponsable.setText("")
                    txtEmailAutor.setText("")
                    txtTelefonoAutor.setText("")
                    txtUbicacion.setText("")
                    Toast.makeText(this@activity_agregar_tickets, "¡Se ha registrado tu ticket satisfactoriamente!", Toast.LENGTH_SHORT).show()
                }

                }
            }

        btnVerTickets.setOnClickListener{
            val pantallaTickets = Intent(this, activity_tickets::class.java)
            startActivity(pantallaTickets)
        }


        }


    }
