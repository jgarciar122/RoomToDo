package com.example.room.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.room.databinding.FragmentDetalleTareaBinding;
import com.example.room.Model.Tarea;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FragmentDetalleTarea extends Fragment {

    private static final int REQUEST_IMAGE_PICK = 1;
    private FragmentDetalleTareaBinding binding;
    private byte[] imagenSeleccionada;
    private TareasViewModel tareasViewModel;
    private Tarea tarea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleTareaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tareasViewModel = new ViewModelProvider(this).get(TareasViewModel.class);

        if (getArguments() != null) {
            tarea = (Tarea) getArguments().getSerializable("tarea");

            binding.textoNombreDetalle.setText(tarea.getNombre());
            binding.textoDescripcionDetalle.setText(tarea.getDescripcion());
            binding.textoFechaDetalle.setText(tarea.getFecha().toString());

            // Cargar la imagen si ya existe
            if (tarea.getImagen() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(tarea.getImagen(), 0, tarea.getImagen().length);
                binding.imagenDetalle.setImageBitmap(bitmap);
            }

            // Acción para abrir la galería y actualizar la imagen
            binding.btnSeleccionarImagen2.setOnClickListener(v -> abrirGaleria());
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == getActivity().RESULT_OK && data != null) {
            Uri uriImagen = data.getData();
            try {
               // InputStream inputStream = getActivity().getContentResolver().openInputStream(uriImagen);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriImagen);


                // Convertir la imagen a byte array para actualizar la tarea
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                imagenSeleccionada = outputStream.toByteArray();

                // Actualizar la tarea con la nueva imagen
                tarea.setImagen(imagenSeleccionada); // Actualizar la tarea con la nueva imagen

                // Llamar al método de actualización en el ViewModel
                tareasViewModel.actualizar(tarea); // Llamar al método de actualización

                binding.imagenDetalle.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
