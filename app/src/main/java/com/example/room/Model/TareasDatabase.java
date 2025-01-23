package com.example.room.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Clase que representa la base de datos Room para la aplicación.
@Database(entities = {Tarea.class}, version = 1, exportSchema = false)
public abstract class TareasDatabase extends RoomDatabase {

    // Define el DAO que interactuará con la base de datos.
    public abstract TareaDAO tareaDAO();

    // Instancia única de la base de datos para evitar múltiples inicializaciones.
    private static volatile TareasDatabase instancia;

    // Método para obtener la instancia de la base de datos.
    public static TareasDatabase obtenerInstancia(final Context context) {
        if (instancia == null) { // Verifica si ya existe una instancia.
            synchronized (TareasDatabase.class) {
                if (instancia == null) { // Doble verificación para mayor seguridad.
                    instancia = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TareasDatabase.class,
                                    "base_datos_tareas" // Nombre de la base de datos para tareas
                            )
                            .fallbackToDestructiveMigration() // Permite recrear la base en caso de cambios en el esquema.
                            .build();
                }
            }
        }
        return instancia;
    }
}
