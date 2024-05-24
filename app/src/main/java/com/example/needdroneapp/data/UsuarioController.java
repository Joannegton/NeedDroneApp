package com.example.needdroneapp.data;

import android.database.Cursor;

public interface UsuarioController {
    Cursor carregaDadosLogin(String email, String senha);

    String pegarNomePorId(Integer userId);
}
