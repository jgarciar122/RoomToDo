package com.example.room.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

// Esta clase representa una tarea en la base de datos.
@Entity(tableName = "tabla_tareas") // Define el nombre de la tabla en Room.
public class Tarea implements java.io.Serializable {

    @PrimaryKey(autoGenerate = true) // Clave primaria que se genera automáticamente.
    private int id;

    private String nombre; // Nombre de la tarea.
    private String descripcion; // Descripción de la tarea.
    private Date fecha; // Fecha límite de la tarea.
    private boolean completada; // Estado de la tarea (completada o no).
    private byte[] imagen; // Imagen asociada a la tarea.

    // Constructor de la clase.
    public Tarea(String nombre, String descripcion, Date fecha, byte[] imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = (fecha != null) ? fecha : new Date(); // Si la fecha es null, usa la fecha actual
        this.completada = false; // Por defecto, la tarea no está completada.
        this.imagen = imagen;
    }

    // Métodos para obtener y modificar el ID.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Métodos para obtener y modificar el nombre.
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos para obtener y modificar la descripción.
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Métodos para obtener y modificar la fecha.
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    // Métodos para obtener y modificar el estado de la tarea.
    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    // Métodos para obtener y modificar la imagen.
    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
