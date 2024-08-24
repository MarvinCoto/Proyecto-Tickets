package marvinjavier.gutierrezcoto

import RecyclerViewHelpers.Adaptador
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tbTickets

class activity_tickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tickets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)
        val icRegresar = findViewById<ImageView>(R.id.icVolver)


        //Mando a llamar a mi recyclerView
        val recvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        //Agrego un layout a mi recyclerView
        rcvTickets.layoutManager = LinearLayoutManager(this)

        //Funcion para mostrar mis datos

        fun obtenerTickets(): List<tbTickets> {

            //1- Creo el objeto de la clase conexión
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Creo mi statement
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from tbtTickets")!!

            val listadoPacientes = mutableListOf<tbTickets>()

            while (resultSet.next()) {
                val uuid_Ticket = resultSet.getString("UUID_Ticket")
                val titulo = resultSet.getString("Titulo")
                val descripcion = resultSet.getString("Descripcion")
                val responsable = resultSet.getString("Responsable")
                val email_Autor = resultSet.getString("Email_Autor")
                val telefono_Autor = resultSet.getString("Telefono_Autor")
                val direccion = resultSet.getString("Ubicacion")
                val estado = resultSet.getString("Estado")

                val valoresJuntos = tbTickets(uuid_Ticket, titulo, descripcion, responsable, email_Autor, telefono_Autor, direccion, estado)

                listadoPacientes.add(valoresJuntos)
            }
            return listadoPacientes
        }


        //Asignar el adaptador a mi recyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val ticketsDB = obtenerTickets()

            withContext(Dispatchers.Main){
                val adapter = Adaptador(ticketsDB)
                recvTickets.adapter = adapter
            }
        }

        icRegresar.setOnClickListener{
            val pantallaTickets = Intent(this, activity_agregar_tickets::class.java)
            startActivity(pantallaTickets)
        }

    }
}