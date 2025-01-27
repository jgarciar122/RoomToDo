package com.example.room.UI;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.Model.Tarea;
import com.example.room.R;
import com.example.room.databinding.FragmentListaTareaBinding;

public class FragmentListaTarea extends Fragment {

    private TareasViewModel tareasViewModel;
    private TareasAdapter tareasAdapter;
    private FragmentListaTareaBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListaTareaBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tareasViewModel = new ViewModelProvider(this).get(TareasViewModel.class);

        tareasAdapter = new TareasAdapter(tareasViewModel);
        recyclerView.setAdapter(tareasAdapter);

        binding.botomAgregarTarea.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.fragmentAgregarTarea);
        });

        tareasViewModel.obtenerTodasLasTareas().observe(getViewLifecycleOwner(), tareas -> {
            tareasAdapter.establecerTareas(tareas);
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Tarea tarea = tareasAdapter.obtenerTareaEn(posicion);

                if (direction == ItemTouchHelper.RIGHT) {
                    // Alerta para marcar tarea como completada cuando se desliza a la derecha
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Marcar tarea como completada")
                            .setMessage("¿Estás seguro de que quieres marcar esta tarea como completada?")
                            .setPositiveButton("Sí", (dialog, which) -> {
                                tarea.setCompletada(true);
                                tareasViewModel.actualizar(tarea);
                                tareasAdapter.notifyItemChanged(posicion);
                            })
                            .setNegativeButton("Cancelar", (dialog, which) -> {
                                tareasAdapter.notifyItemChanged(posicion); // Restaurar tarea si se cancela
                            })
                            .show();
                } else if (direction == ItemTouchHelper.LEFT) {
                    // Alerta para eliminar tarea cuando se desliza a la izquierda
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Eliminar tarea")
                            .setMessage("¿Estás seguro de que quieres eliminar esta tarea?")
                            .setPositiveButton("Eliminar", (dialog, which) -> {
                                tareasViewModel.eliminar(tarea);
                                tareasAdapter.eliminarTareaEn(posicion);
                            })
                            .setNegativeButton("Cancelar", (dialog, which) -> {
                                tareasAdapter.notifyItemChanged(posicion); // Restaurar tarea si se cancela
                            })
                            .show();
                }
            }

        }).attachToRecyclerView(binding.recyclerTareas);
    }
}