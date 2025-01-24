package com.example.room.UI;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
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

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareasViewHolder> {

    private List<Tarea> listaTareas = new ArrayList<>();
    private final TareasViewModel tareasViewModel;

    public TareasAdapter(TareasViewModel tareasViewModel) {
        this.tareasViewModel = tareasViewModel;
    }

    @NonNull
    @Override
    public TareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTareaBinding binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TareasViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasViewHolder holder, int position) {
        Tarea tareaActual = listaTareas.get(position);

        holder.binding.textoNombre.setText(tareaActual.getNombre());
        holder.binding.textoDescripcion.setText(tareaActual.getDescripcion());

        if (tareaActual.isCompletada()) {
            // Esto sirve para tacharla si está completada y poner el fondo gris
            holder.binding.textoNombre.setPaintFlags(holder.binding.textoNombre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        } else {
            // Esto la mantiene normal si no está completada
            holder.binding.textoNombre.setPaintFlags(holder.binding.textoNombre.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        // Convertimos la fecha de Date a String antes de asignarla al TextView.
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.binding.textoFecha.setText(formatoFecha.format(tareaActual.getFecha()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((Activity) holder.itemView.getContext(), R.id.nav_host_fragment);

                Bundle bundle = new Bundle();
                bundle.putSerializable("tarea", tareaActual);

                navController.navigate(R.id.fragmentDetalleTarea, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    // Método para actualizar la lista de tareas.
    public void establecerTareas(List<Tarea> tareas) {
        this.listaTareas = tareas;
        notifyDataSetChanged();
    }

    static class TareasViewHolder extends RecyclerView.ViewHolder {

        private final ItemTareaBinding binding;

        public TareasViewHolder(@NonNull ItemTareaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public Tarea obtenerTareaEn(int posicion) {
        return listaTareas.get(posicion);
    }

    public void eliminarTareaEn(int posicion) {
        listaTareas.remove(posicion);
        notifyItemRemoved(posicion);
    }
}
