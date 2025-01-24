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
        binding = FragmentDetalleTareaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {

            Tarea tarea = (Tarea) getArguments().getSerializable("tarea");

            binding.textoNombreDetalle.setText(tarea.getNombre());
            binding.textoDescripcionDetalle.setText(tarea.getDescripcion());
            binding.textoFechaDetalle.setText(tarea.getFecha().toString());

            if (tarea.getImagen() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(tarea.getImagen(), 0, tarea.getImagen().length);
                binding.imagenDetalle.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
