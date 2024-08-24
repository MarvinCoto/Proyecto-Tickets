--Base de datos del proyecto--

create table tbtUsuarios (
UUID_Usuario VARCHAR2(100) primary key, 
Nombre VARCHAR2(50) not null,
Correo VARCHAR2(50) not null,
Contrasena VARCHAR2(150) not null
);

create table tbtTickets (
UUID_Ticket VARCHAR2(100) primary key,
Titulo VARCHAR2(50) not null,
Descripcion VARCHAR2(100) not null,
Responsable VARCHAR2(50) not null,
Email_Autor VARCHAR2(50) not null,
Telefono_Autor VARCHAR2(50) not null,
Ubicacion VARCHAR2(50) not null,
Estado VARCHAR2(50) not null
);
