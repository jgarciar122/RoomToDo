package com.example.room.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.room.databinding.FragmentDetalleTareaBinding;
import com.example.room.Model.Tarea;


public class FragmentDetalleTarea extends Fragment {

    private FragmentDetalleTareaBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Vincula el diseño con ViewBinding.
        binding = FragmentDetalleTareaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtiene los argumentos pasados al fragmento.
        if (getArguments() != null) {

            Tarea tarea = (Tarea) getArguments().getSerializable("tarea");

            // Configura los datos de la reseña en las vistas.
            binding.textoNombreDetalle.setText(tarea.getNombre());
            binding.textoDescripcionDetalle.setText(tarea.getDescripcion());

            // Convierte el array de bytes a Bitmap y lo muestra.
            if (tarea.getImagen() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(tarea.getImagen(), 0, tarea.getImagen().length);
                binding.imagenDetalle.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Libera el binding para evitar fugas de memoria.
    }
}
