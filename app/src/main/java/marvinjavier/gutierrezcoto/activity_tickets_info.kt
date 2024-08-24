package marvinjavier.gutierrezcoto

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_tickets_info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tickets_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mando a llamar a los elementos de la vista
        val txtTituloTicket = findViewById<TextView>(R.id.txtTituloTicket)
        val txtDescripcionTicket = findViewById<TextView>(R.id.txtDescripcionTicket)
        val txtResponsableTicket = findViewById<TextView>(R.id.txtResponsableTicket)
        val txtEmailTicket = findViewById<TextView>(R.id.txtEmailTicket)
        val txtTelefonoTicket = findViewById<TextView>(R.id.txtTelefonoTicket)
        val txtEstadoTicket = findViewById<TextView>(R.id.txtEstadoTicket)
        val txtUbicacionTicket = findViewById<TextView>(R.id.txtUbicacionTicket)
        val txtIDticket = findViewById<TextView>(R.id.txtIDticket)
        val icRegresar = findViewById<ImageView>(R.id.icRegresar)


        //2- Recibo los valores
        val tituloRecibido = intent.getStringExtra("Titulo")
        val descripcionRecibida = intent.getStringExtra("Descripcion")
        val responsableRecibido = intent.getStringExtra("Responsable")
        val emailRecibido = intent.getStringExtra("Email_Autor")
        val telefonoRecibido = intent.getStringExtra("Telefono_Autor")
        val estadoRecibido = intent.getStringExtra("Estado")
        val ubicacionRecibida = intent.getStringExtra("Ubicacion")
        val idRecibido = intent.getStringExtra("UUID_Ticket")

        //3- Asigno los valores a los textviews
        txtTituloTicket.text = tituloRecibido
        txtDescripcionTicket.text = descripcionRecibida
        txtResponsableTicket.text = responsableRecibido
        txtEmailTicket.text = emailRecibido
        txtTelefonoTicket.text = telefonoRecibido
        txtEstadoTicket.text = estadoRecibido
        txtUbicacionTicket.text = ubicacionRecibida
        txtIDticket.text = idRecibido


        icRegresar.setOnClickListener{
            val pantallaTickets = Intent(this, activity_tickets::class.java)
            startActivity(pantallaTickets)
        }

    }
}