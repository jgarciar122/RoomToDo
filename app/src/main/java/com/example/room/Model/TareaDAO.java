package com.example.room.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Esta interfaz define las operaciones para interactuar con la tabla "tabla_tareas".
@Dao
public interface TareaDAO {

    // Inserta una nueva tarea en la tabla.
    @Insert
    void insertar(Tarea tarea);

    // Actualiza una tarea existente en la tabla.
    @Update
    void actualizar(Tarea tarea);

    // Elimina una tarea de la tabla.
    @Delete
    void eliminar(Tarea tarea);

    // Consulta todas las tareas de la tabla, ordenadas por fecha.
    @Query("SELECT * FROM tabla_tareas ORDER BY fecha ASC")
    LiveData<List<Tarea>> obtenerTodasLasTareas();

    // Consulta una tarea específica por su nombre.
    @Query("SELECT * FROM tabla_tareas WHERE nombre = :nombre")
    Tarea obtenerTareaPorNombre(String nombre);

    // Elimina todas las tareas de la tabla.
    @Query("DELETE FROM tabla_tareas")
    void eliminarTodasLasTareas();
}

