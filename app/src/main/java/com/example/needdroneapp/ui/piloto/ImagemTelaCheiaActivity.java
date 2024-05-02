package com.example.needdroneapp.ui.piloto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.ActivityImagemtelacheiaBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImagemTelaCheiaActivity extends AppCompatActivity {

    ActivityImagemtelacheiaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImagemtelacheiaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView fullScreenImageView = findViewById(R.id.fullScreenImageView);
        fullScreenImageView.setScaleType(ImageView.ScaleType.CENTER_CROP); // imagem fica do tamanho todo da tela

        Intent callingActivityIntent = getIntent();
        if(callingActivityIntent != null) {
            String imagePath = callingActivityIntent.getStringExtra("pathUrl");
            Glide.with(this)
                    .load(new File(imagePath)) // Carrega a imagem a partir do arquivo
                    .into(fullScreenImageView); // Define a imagem no ImageView
        }
    }

    public static Bitmap loadImageFromStorage(String directoryPath) {
        try {
            File f = new File(directoryPath);
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
