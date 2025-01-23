package com.example.room.Model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TareasRepositorio {

    // DAO para interactuar con la base de datos.
    private final TareaDAO tareaDAO;

    // Define un Executor para manejar tareas en segundo plano.
    private final Executor databaseExecutor;

    public TareasRepositorio(Application aplicacion) {
        // Obtiene la instancia de la base de datos y el DAO.
        TareasDatabase baseDeDatos = TareasDatabase.obtenerInstancia(aplicacion);
        tareaDAO = baseDeDatos.tareaDAO();
        // Inicializamos el Executor.
        databaseExecutor = Executors.newSingleThreadExecutor();
    }

    // Método para insertar una tarea en la base de datos.
    public void insertar(Tarea tarea) {
        databaseExecutor.execute(() -> tareaDAO.insertar(tarea));
    }

    // Método para actualizar una tarea en la base de datos.
    public void actualizar(Tarea tarea) {
        databaseExecutor.execute(() -> tareaDAO.actualizar(tarea));
    }

    // Método para eliminar una tarea de la base de datos.
    public void eliminar(Tarea tarea) {
        databaseExecutor.execute(() -> tareaDAO.eliminar(tarea));
    }

    // Método para eliminar todas las tareas.
    public void eliminarTodasLasTareas() {
        databaseExecutor.execute(tareaDAO::eliminarTodasLasTareas);
    }

    // Método para obtener todas las tareas como LiveData.
    public LiveData<List<Tarea>> obtenerTodasLasTareas() {
        return tareaDAO.obtenerTodasLasTareas();
    }
}
