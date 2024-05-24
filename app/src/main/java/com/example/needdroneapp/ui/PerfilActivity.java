package com.example.needdroneapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.data.PortfolioController;
import com.example.needdroneapp.databinding.ActivityPerfilBinding;
import com.example.needdroneapp.ui.edicao.EditClienteActivity;
import com.example.needdroneapp.ui.edicao.EditPilotoActivity;
import com.example.needdroneapp.ui.piloto.ImagemTelaCheiaActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    private ActivityPerfilBinding binding;
    private static final int SELECT_IMAGE_REQUEST_CODE = 100;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userType = prefs.getString("userType", "");
        int userId = prefs.getInt("userId", 0);

        if (userType.equals("cliente")) {
            LinearLayout experiencia = findViewById(R.id.containerExperiencia);
            experiencia.setVisibility(View.GONE);//deixa a experiencia invisivel
            //conexao com o banco
            ClienteController clienteController = new ClienteController(this);
            Cursor dados = clienteController.carregaDadosPorId(userId);
            if (dados.getCount() > 0){
                dados.moveToFirst();
                String fotoPath = dados.getString(dados.getColumnIndex("foto"));
                Bitmap fotoBitmap = EditClienteActivity.loadImageFromStorage(fotoPath);
                if (fotoPath != null) {
                    binding.fotoPerfil.setImageBitmap(fotoBitmap);
                }else {
                    binding.fotoPerfil.setImageResource(android.R.drawable.ic_menu_camera);
                }
                binding.nome.setText(dados.getString(dados.getColumnIndex("nome")));
                binding.biografia.setText(dados.getString(dados.getColumnIndex("biografia")));
                binding.emailPiloto.setText("E-mail :" + dados.getString(dados.getColumnIndex("email")));
                binding.telefonePiloto.setText("Tel :" + dados.getString(dados.getColumnIndex("tel")));
            }

        } else if (userType.equals("piloto")) {
            LinearLayout experiencia = findViewById(R.id.containerExperiencia);
            experiencia.setVisibility(View.VISIBLE);//deixa a experiencia invisivel

            binding.portfolio1.setOnClickListener(v ->{
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            });

            PilotoController pilotoController = new PilotoController(this);
            Cursor dados = pilotoController.carregaDadosPorId(userId);
            if (dados.getCount() > 0) {
                dados.moveToFirst();
                String fotoPath = dados.getString(dados.getColumnIndex("foto"));
                Bitmap fotoBitmap = EditPilotoActivity.loadImageFromStorage(fotoPath);
                if (fotoBitmap != null) {
                    binding.fotoPerfil.setImageBitmap(fotoBitmap);
                }else {
                    binding.fotoPerfil.setImageResource(android.R.drawable.ic_menu_camera);
                }

                binding.nome.setText(dados.getString(dados.getColumnIndex("nome")));
                binding.biografia.setText(dados.getString(dados.getColumnIndex("biografia")));
                binding.licenca.setText("Licença de pilotagem: " + dados.getString(dados.getColumnIndex("licencaPilotagem")));
                binding.emailPiloto.setText("E-mail: " + dados.getString(dados.getColumnIndex("email")));
                binding.telefonePiloto.setText("Tel: " + dados.getString(dados.getColumnIndex("tel")));
            }

            //Carregar imagens do portfolio
            PortfolioController portfolioController = new PortfolioController(this);
            List<String> portfolio = portfolioController.carregaImagemPorIdPiloto(userId);
            if (!portfolio.isEmpty()) {
                for (int i = 0; i < portfolio.size(); i++) {
                    String fotoPath = portfolio.get(i);
                    Bitmap fotoBitmap = loadImageFromStorage(fotoPath);
                    if (fotoBitmap != null) {
                        // Define os parâmetros do LinearLayout
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, // Largura
                                LinearLayout.LayoutParams.WRAP_CONTENT, // Altura
                                1 // Peso
                        );

                        ImageView imageView = new ImageView(this);
                        imageView.setImageBitmap(fotoBitmap);
                        imageView.setLayoutParams(layoutParams);

                        binding.galeriaPortfolio.addView(imageView);

                        imageView.setOnClickListener(v -> {
                            mostrarPopup(v, fotoPath);

                        });

                    }
                }


                if (binding.galeriaPortfolio.getChildCount() == 4){
                    binding.portfolio1.setVisibility(View.GONE);
                    binding.portfolio1.setImageResource(android.R.drawable.ic_menu_camera);
                }
            }
        }
    }

    @Override
    //metodo para pegar a imagem selecionada e colocar no ImageView
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", 0);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                binding.portfolio1.setImageBitmap(bitmap);

                //salvar no banco de dados
                EditPilotoActivity editPilotoActivity = new EditPilotoActivity();
                if (binding.portfolio1.isActivated()){
                    String stringFoto = saveToInternalStorage(editPilotoActivity.getBitmapFromView(binding.portfolio1));
                    PortfolioController portfolioController = new PortfolioController(this);
                    portfolioController.insereDados(stringFoto, userId);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile_" + System.currentTimeMillis() + ".jpg");

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
        return mypath.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String directoryPath)
    {
        try {
            File f = new File(directoryPath);
            return BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void imagemTelaCheia(View view, String pathUrl){
        Intent intent = new Intent(this, ImagemTelaCheiaActivity.class);
        intent.putExtra("pathUrl", pathUrl);
        startActivity(intent);
    }

    public void mostrarPopup(View view, String pathUrl){
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.itemEditarImagem) {
                    abrirGaleria();
                    return true;
                } else if (itemId == R.id.itemVerImagem) {
                    imagemTelaCheia(view, pathUrl);
                    return true;
                } else {
                    return false;
                }
            }

            private void abrirGaleria() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
        popup.show();

    }

}