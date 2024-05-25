package com.example.needdroneapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.needdroneapp.ui.login.LoginFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_dashboard, R.id.nav_projetos, R.id.nav_login)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Verifica se a Intent que iniciou a atividade possui um extra chamado "OpenFragment"
        if (getIntent().hasExtra("OpenFragment")) {
            // Recupera o valor do extra "OpenFragment"
            String fragmentToOpen = getIntent().getStringExtra("OpenFragment");
            // Verifica se o fragmento a ser aberto é o LoginFragment
            if ("LoginFragment".equals(fragmentToOpen)) {
                // Chama o método para exibir o LoginFragment
                displayLoginFragment();
            }
        }
    }

    // Método para exibir o LoginFragment
    private void displayLoginFragment() {
        // Cria uma instância do LoginFragment
        LoginFragment fragment = new LoginFragment();
        // Inicia uma transação de fragmento para substituir o conteúdo do container pelo LoginFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, fragment) // Substitui o fragmento atual pelo LoginFragment
                .commit(); // Aplica as mudanças na interface do usuário
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla o menu; isso adiciona itens à barra de ação se ela estiver presente.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            configuracoes();
            return true;
        } else if (id == R.id.action_logout) {
            sair();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void configuracoes() {
        // Este é apenas um exemplo. Substitua por sua própria implementação.
        Toast.makeText(this, "Configurações selecionadas", Toast.LENGTH_SHORT).show();
    }

    private void sair() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        //garante que todas as atividades na pilha de atividades sejam limpas quando a nova intent for iniciada.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}