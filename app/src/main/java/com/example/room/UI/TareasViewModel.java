package com.example.room.UI;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.room.Model.Tarea;
import com.example.room.Model.TareasRepositorio;

import java.util.List;

// Clase que gestiona los datos observables para la vista.
public class TareasViewModel extends AndroidViewModel {

    private final TareasRepositorio repositorio; // Referencia al repositorio.
    private final LiveData<List<Tarea>> todasLasTareas; // Lista de tareas observables.

    // Constructor del ViewModel.
    public TareasViewModel(Application aplicacion) {
        super(aplicacion);
        repositorio = new TareasRepositorio(aplicacion); // Inicializa el repositorio.
        todasLasTareas = repositorio.obtenerTodasLasTareas(); // Obtiene todas las tareas.
    }

    // Método para insertar una nueva tarea.
    public void insertar(Tarea tarea) {
        repositorio.insertar(tarea);
    }

    // Método para actualizar una tarea existente.
    public void actualizar(Tarea tarea) {
        repositorio.actualizar(tarea);
    }

    // Método para eliminar una tarea.
    public void eliminar(Tarea tarea) {
        repositorio.eliminar(tarea);
    }

    // Método para eliminar todas las tareas.
    public void eliminarTodasLasTareas() {
        repositorio.eliminarTodasLasTareas();
    }

    // Método para obtener todas las tareas como LiveData.
    public LiveData<List<Tarea>> obtenerTodasLasTareas() {
        return todasLasTareas;
    }
}
