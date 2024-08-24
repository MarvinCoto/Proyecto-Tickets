package modelo

data class tbTickets(
    var UUID_Ticket: String,
    var Titulo: String,
    var Descripcion: String,
    var Responsable: String,
    var EmailAutor: String,
    var TelefonAutor: String,
    var Ubicacion: String,
    var Estado: String,
)
