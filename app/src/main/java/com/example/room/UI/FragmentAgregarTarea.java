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

import com.example.room.databinding.FragmentAgregarTareaBinding;
import com.example.room.Model.Tarea;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FragmentAgregarTarea extends Fragment {

    private static final int REQUEST_IMAGE_PICK = 1; // Código único para identificar la selección de imagen.

    private FragmentAgregarTareaBinding binding; // Objeto para acceder a las vistas con ViewBinding.
    private byte[] imagenSeleccionada; // Almacena la imagen seleccionada como un array de bytes.
    private TareasViewModel tareasViewModel; // Referencia al ViewModel para interactuar con los datos.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inicializa ViewBinding para vincular el diseño XML con el fragmento.
        binding = FragmentAgregarTareaBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Devuelve la vista raíz del diseño.
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa el ViewModel para gestionar las operaciones relacionadas con las tareas.
        tareasViewModel = new ViewModelProvider(this).get(TareasViewModel.class);

        // Configura el botón para abrir la galería y seleccionar una imagen.
        binding.btnSeleccionarImagen.setOnClickListener(v -> abrirGaleria());

        // Configura el botón para guardar la tarea y sus datos en la base de datos.
        binding.btnGuardar.setOnClickListener(v -> guardarTarea());
    }

    private void abrirGaleria() {
        // Crea un intent para seleccionar una imagen desde la galería.
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK); // Inicia la actividad para la selección de imagen.
    }

    // Maneja la respuesta de la actividad que abre la galería.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica si el resultado corresponde a la selección de imagen y si fue exitoso.
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == getActivity().RESULT_OK && data != null) {
            Uri uriImagen = data.getData(); // Obtiene la URI de la imagen seleccionada.
            try {
                // Abre un flujo de entrada para leer los datos de la imagen.
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uriImagen);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream); // Convierte los datos en un Bitmap.

                // Muestra la imagen seleccionada en el ImageView.
                binding.imagenTarea.setImageBitmap(bitmap);

                // Convierte el Bitmap a un array de bytes para guardarlo en la base de datos.
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                imagenSeleccionada = outputStream.toByteArray(); // Almacena la imagen como bytes.
            } catch (Exception e) {
                e.printStackTrace(); // Maneja posibles errores al procesar la imagen.
            }
        }
    }

    private void guardarTarea() {
        // Obtiene los valores ingresados por el usuario.
        String nombre = binding.editNombre.getText().toString(); // Nombre de la tarea.
        String descripcion = binding.editDescripcion.getText().toString(); // Descripción de la tarea.
        String fecha = binding.editFecha.getText().toString(); // Fecha de la tarea (utiliza un DatePicker en la interfaz).

        // Crea un nuevo objeto Tarea con los datos proporcionados.
        Tarea tarea = new Tarea(nombre, descripcion, fecha, imagenSeleccionada);

        // Inserta la tarea en la base de datos a través del ViewModel.
        tareasViewModel.insertar(tarea);

        // Navega al fragmento anterior después de guardar la tarea.
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Libera el objeto ViewBinding para evitar fugas de memoria.
    }
}
