package com.example.room.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.Model.Tarea;
import com.example.room.databinding.ItemTareaBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Adaptador para gestionar la lista de tareas en el RecyclerView.
public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareasViewHolder> {

    private List<Tarea> listaTareas = new ArrayList<>(); // Lista de tareas.
    private final TareasViewModel tareasViewModel; // ViewModel para interactuar con la base de datos.

    // Constructor para inicializar el adaptador con el ViewModel.
    public TareasAdapter(TareasViewModel tareasViewModel) {
        this.tareasViewModel = tareasViewModel;
    }

    @NonNull
    @Override
    public TareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout de la tarjeta (item_tarea.xml).
        ItemTareaBinding binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TareasViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasViewHolder holder, int position) {
        // Obtenemos la tarea en la posición actual.
        Tarea tareaActual = listaTareas.get(position);

        // Configuramos los datos en las vistas.
        holder.binding.textoNombre.setText(tareaActual.getNombre());
        holder.binding.textoDescripcion.setText(tareaActual.getDescripcion());

        // Convertimos la fecha de Date a String antes de asignarla al TextView.
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.binding.textoFecha.setText(formatoFecha.format(tareaActual.getFecha()));

        // Asigna un listener de clic al elemento completo del RecyclerView.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene una instancia del NavController para gestionar la navegación.
                NavController navController = Navigation.findNavController((Activity) holder.itemView.getContext(), R.id.nav_host_fragment);

                // Crea un Bundle para pasar datos al siguiente fragmento.
                Bundle bundle = new Bundle();
                bundle.putSerializable("tarea", tareaActual); // Inserta el objeto 'tareaActual' en el Bundle.

                // Navega al fragmento de detalle utilizando el ID del destino y el Bundle con los datos.
                navController.navigate(R.id.fragmentDetalleTarea, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devuelve el número de elementos en la lista.
        return listaTareas.size();
    }

    // Método para actualizar la lista de tareas.
    public void establecerTareas(List<Tarea> tareas) {
        this.listaTareas = tareas;
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado.
    }

    // Clase interna ViewHolder para vincular las vistas de cada tarjeta.
    static class TareasViewHolder extends RecyclerView.ViewHolder {

        private final ItemTareaBinding binding;

        public TareasViewHolder(@NonNull ItemTareaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Método para obtener la tarea en una posición específica.
    public Tarea obtenerTareaEn(int posicion) {
        return listaTareas.get(posicion);
    }

    // Método para eliminar una tarea de la lista.
    public void eliminarTareaEn(int posicion) {
        listaTareas.remove(posicion); // Elimina el elemento de la lista.
        notifyItemRemoved(posicion); // Notifica al RecyclerView sobre la eliminación.
    }
}
