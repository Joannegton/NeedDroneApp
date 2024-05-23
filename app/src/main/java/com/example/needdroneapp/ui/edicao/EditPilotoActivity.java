package com.example.needdroneapp.ui.edicao;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.databinding.ActivityEditPilotoBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditPilotoActivity extends AppCompatActivity {

    private ActivityEditPilotoBinding binding;

    SharedPreferences preferences;
    int userId;
    PilotoController pilotoController;
    Cursor cursor;

    private static final int SELECT_IMAGE_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPilotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userId = preferences.getInt("userId", 0);

        pilotoController = new PilotoController(getBaseContext());
        cursor = pilotoController.carregaDadosPorId(userId);

        binding.updtImgBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);

        });

        carregaDados();
        binding.btnAtualizar.setOnClickListener(v -> atualizarDados());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                // Aqui você pode definir a imagem selecionada para o seu ImageButton
                binding.userImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public Bitmap getBitmapFromView(View view) {
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }

    public static Bitmap loadImageFromStorage(String path)
    {
        try {
            File f=new File(path, "profile.jpg");
            return BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void atualizarDados() {
        String nome = binding.username.getText().toString();
        String biografia = binding.biografia.getText().toString();
        //String dataNasc = cursor.getString(cursor.getColumnIndexOrThrow("dataNasc"));
        String tel = binding.telefone.getText().toString();
        Boolean whatsapp = binding.whatsapp.isChecked();
        String rua = binding.rua.getText().toString();
        String cidadeEstado = binding.cidadeEstado.getText().toString();
        String cep = binding.cep.getText().toString();
        String foto = saveToInternalStorage(getBitmapFromView(binding.userImg));
        String experiencia = binding.experiencia.getText().toString();
        String especializacao = binding.especializacao.getText().toString();

        String dataNasc = cursor.getString(cursor.getColumnIndexOrThrow("dataNasc"));

        String resultado = pilotoController.atualizaDados(
                userId,
                nome,
                dataNasc,
                tel,
                whatsapp,
                rua,
                cidadeEstado,
                cep,
                foto,
                biografia,
                experiencia,
                especializacao
        );

        if (resultado.equals("Registro atualizado com sucesso!")){
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        cursor = pilotoController.carregaDadosPorId(userId);

        finish();
    }

    public void carregaDados(){
        binding.username.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        binding.email.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        binding.biografia.setText(cursor.getString(cursor.getColumnIndexOrThrow("biografia")));
        //binding.editDataNasc.setText(cursor.getString(cursor.getColumnIndexOrThrow("dataNasc")));
        binding.telefone.setText(cursor.getString(cursor.getColumnIndexOrThrow("tel")));
        binding.whatsapp.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("whatsapp")) > 0);
        binding.rua.setText(cursor.getString(cursor.getColumnIndexOrThrow("rua")));
        binding.cidadeEstado.setText(cursor.getString(cursor.getColumnIndexOrThrow("cidadeEstado")));
        binding.cep.setText(cursor.getString(cursor.getColumnIndexOrThrow("cep")));
        String fotoPath = cursor.getString(cursor.getColumnIndexOrThrow("foto"));
        Bitmap fotoBitmap = loadImageFromStorage(fotoPath);
        if (fotoBitmap != null) {
            binding.userImg.setImageBitmap(fotoBitmap);
        } else {
            binding.userImg.setImageResource(android.R.drawable.ic_menu_camera);
        }

        binding.licenca.setText(cursor.getString(cursor.getColumnIndexOrThrow("licencaPilotagem")));
        binding.experiencia.setText(cursor.getString(cursor.getColumnIndexOrThrow("experiencia")));
        binding.especializacao.setText(cursor.getString(cursor.getColumnIndexOrThrow("especializacao")));
    }


}