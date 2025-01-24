package com.example.room.UI;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.room.Model.Tarea;
import com.example.room.Model.TareasRepositorio;

import java.util.Date;
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

        //inicializarTareasDeMuestra();
    }

    private void inicializarTareasDeMuestra() {
        repositorio.eliminarTodasLasTareas();

        // Inserción de tareas con nombre, descripción, fecha y imagen
        repositorio.insertar(new Tarea("Estudiar programación", "Repasar conceptos de Java y Android",
                new Date(), null)); // new Date() para la fecha actual, null para imagen (puedes añadir una imagen si lo deseas)
        repositorio.insertar(new Tarea("Hacer ejercicio", "Correr 30 minutos por la mañana",
                new Date(), null));
        repositorio.insertar(new Tarea("Reunión con el equipo", "Revisar avances de proyectos",
                new Date(), null));
        repositorio.insertar(new Tarea("Llamar a la familia", "Hablar con mis padres y hermanos",
                new Date(), null));
        repositorio.insertar(new Tarea("Comprar comida", "Ir al supermercado a comprar ingredientes",
                new Date(), null));

        repositorio.insertar(new Tarea("Preparar presentación", "Crear diapositivas para la reunión",
                new Date(), null));
        repositorio.insertar(new Tarea("Revisar correo", "Leer los correos importantes",
                new Date(), null));
        repositorio.insertar(new Tarea("Lavar la ropa", "Poner la lavadora y colgar la ropa",
                new Date(), null));
        repositorio.insertar(new Tarea("Ver una película", "Mirar una película de acción para descansar",
                new Date(), null));
        repositorio.insertar(new Tarea("Leer libro", "Leer 30 páginas del libro actual",
                new Date(), null));
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
