package com.example.needdroneapp.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class PortfolioController {
    private SQLiteDatabase db;
    private final CriarDb banco;

    public PortfolioController(Context context){
        banco = new CriarDb(context);
    }

    public String insereDados(
            String imagem,
            Integer pilotoId
    ) {
        db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("imagem", imagem);
        valores.put("pilotoId", pilotoId);

        try {
            long resultado = db.insert("portfolio", null, valores);
            if (resultado == -1) {
                return "Erro ao inserir registro!";
            } else {
                return "Registro inserido com sucesso!";
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Erro ao inserir dados", e);
            return "Erro ao inserir registro: " + e.getMessage();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public Cursor carregaImagemPorId(Integer id) {
        Cursor cursor;
        String[] campos = {"id", "imagem", "pilotoId"};
        String where = "id = " + id;
        db = banco.getReadableDatabase();
        cursor = db.query("portfolio", campos, where, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public List<String> carregaImagemPorIdPiloto(Integer pilotoId) {
        Cursor cursor;
        List<String> listaImagens = new ArrayList<>();
        String[] campos = {"imagem"};
        String where = "pilotoId = " + pilotoId;
        db = banco.getReadableDatabase();
        cursor = db.query("portfolio", campos, where, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                String imagem = cursor.getString(0);
                listaImagens.add(imagem);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return listaImagens;
    }
}
