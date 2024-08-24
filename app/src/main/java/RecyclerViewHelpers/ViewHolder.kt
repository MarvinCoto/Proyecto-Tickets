package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import marvinjavier.gutierrezcoto.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val txtTituloCard = view.findViewById<TextView>(R.id.txtTituloCard)
    val txtEstadoCard = view.findViewById<TextView>(R.id.txtEstadoCard)
    val imgEditar = view.findViewById<ImageView>(R.id.imgEditar)
    val imgEliminar = view.findViewById<ImageView>(R.id.imgBorrar)
}