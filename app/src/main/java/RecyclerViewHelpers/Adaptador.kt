package RecyclerViewHelpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import marvinjavier.gutierrezcoto.R
import marvinjavier.gutierrezcoto.activity_tickets_info
import modelo.ClaseConexion
import modelo.tbTickets

class Adaptador(var Datos: List<tbTickets>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Uno el recyclerView con mi card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_tickets, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Controlar mi card
        val item = Datos[position]
        holder.txtTituloCard.text = item.Titulo
        holder.txtEstadoCard.text = item.Estado

        //Configurar el icono para eliminar datos
        holder.imgEliminar.setOnClickListener {

            //Crear alerta de confirmación para borrar
            val context = holder.txtTituloCard.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Estás seguro de que deseas eliminar el ticket?")

            //Botones de mi alerta
            builder.setPositiveButton("Si") {
                    dialog, wich ->
                eliminarTicket(item.Titulo, position)
            }

            builder.setNegativeButton("No") {
                    dialog, wich ->
                //Si doy click en "no" se cerrara la alerta
                dialog.dismiss()
            }

            //Para que se muestre mi alerta
            val dialog = builder.create()
            dialog.show()

        }

        holder.imgEditar.setOnClickListener {
            val context = holder.itemView.context
            val builder = android.app.AlertDialog.Builder(context)

            builder.setTitle("Editar Estado")

            val txtNuevoEstado = EditText(context).apply {
                setText(item.Estado)
            }


            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(txtNuevoEstado)
            }

            builder.setView(layout)

            builder.setPositiveButton("Guardar Cambios") {
                    dialog, wich -> editarTicket(txtNuevoEstado.text.toString(), item.UUID_Ticket)
            }

            builder.setNegativeButton("Cancelar"){
                    dialog, wich -> dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        //Ver todos los datos dandole click a la card
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context

            //Cambiar de pantalla a la pantalla de detalle
            val pantallaDetalle = Intent(context, activity_tickets_info::class.java)
            //Enviar a la otra pantalla todos mis datos
            pantallaDetalle.putExtra("UUID_Ticket", item.UUID_Ticket)
            pantallaDetalle.putExtra("Titulo", item.Titulo)
            pantallaDetalle.putExtra("Descripcion", item.Descripcion)
            pantallaDetalle.putExtra("Responsable", item.Responsable)
            pantallaDetalle.putExtra("Email_Autor", item.EmailAutor)
            pantallaDetalle.putExtra("Telefono_Autor", item.TelefonAutor)
            pantallaDetalle.putExtra("Ubicacion", item.Ubicacion)
            pantallaDetalle.putExtra("Estado", item.Estado)
            context.startActivity(pantallaDetalle)
        }

    }

    //Función para actualizar recyclerView
    fun actualizarRecyclerView(nuevaLista: List<tbTickets>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    //1- Crear la función de eliminar
    fun eliminarTicket(titulo: String, posicion: Int) {
        //Se notifica al adaptador
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        //Quitar de la base de datos
        GlobalScope.launch(Dispatchers.IO) {
            //Dos pasos para eliminar de la base

            //1- Crear un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Creamos una variable que contenga un PrepareStatement
            val deleteProducto = objConexion?.prepareStatement("DELETE tbtTickets WHERE Titulo = ?")!!
            deleteProducto.setString(1, titulo)
            deleteProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }

        //Notificamos los cambios para refrescar la lista
        Datos = listaDatos.toList()
        //Quitamos los datos de la lista
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }


    //Actualizar pantalla al editar
    fun actualizarPantalla (uuid: String, nuevoEstado: String) {

        val index = Datos.indexOfFirst { it.UUID_Ticket == uuid }

        Datos[index].Estado = nuevoEstado
        notifyDataSetChanged()

    }

    //Funcion para editar datos
    fun editarTicket (estado: String, uuidTicket: String,) {
        //Creomos una corrutina
        GlobalScope.launch(Dispatchers.IO) {

            //Creamos el objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //Creamos la variable con el prepare Statement
            val updatePaciente = objConexion?.prepareStatement("UPDATE tbtTickets SET Estado = ? WHERE UUID_Ticket = ?")!!
            updatePaciente.setString(1, estado)
            updatePaciente.setString(2, uuidTicket)
            updatePaciente.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualizarPantalla(uuidTicket, estado)
            }
        }


    }

}