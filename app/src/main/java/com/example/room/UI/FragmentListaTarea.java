package com.example.room.UI;

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

    private TareasViewModel tareasViewModel; // Referencia al ViewModel.
    private TareasAdapter tareasAdapter; // Adaptador para el RecyclerView.
    private FragmentListaTareaBinding binding; // Objeto para ViewBinding

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el diseño del fragmento.
        binding = FragmentListaTareaBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recycler_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Diseño en lista vertical.

        // Obtenemos una instancia del ViewModel.
        tareasViewModel = new ViewModelProvider(this).get(TareasViewModel.class);

        // Configuramos el adaptador del RecyclerView.
        tareasAdapter = new TareasAdapter(tareasViewModel);
        recyclerView.setAdapter(tareasAdapter);

        binding.botomAgregarTarea.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.fragmentAgregarTarea);
        });

        // Observamos los datos de la lista de películas.
        tareasViewModel.obtenerTodasLasTareas().observe(getViewLifecycleOwner(), tareas -> {
            // Actualizamos los datos del adaptador cuando cambien en el ViewModel.
            tareasAdapter.establecerTareas(tareas);
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No permitimos mover los elementos.
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Obtiene la posición del elemento desplazado.
                int posicion = viewHolder.getAdapterPosition();
                Tarea tareaAEliminar = tareasAdapter.obtenerTareaEn(posicion);

                // Elimina la película de la base de datos.
                tareasViewModel.eliminar(tareaAEliminar);

                // Elimina la película de la lista visualmente.
                tareasAdapter.eliminarTareaEn(posicion);
            }
        }).attachToRecyclerView(binding.recyclerTareas);

    }



}
