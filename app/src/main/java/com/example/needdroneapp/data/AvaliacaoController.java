package com.example.needdroneapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AvaliacaoController {
    private SQLiteDatabase db;
    private CriarDb banco;

    public AvaliacaoController(Context context){
        banco = new CriarDb(context);
    }

    public String insereDados(
            String nome,
            Integer pilotoId,
            Integer clienteId,
            String foto,
            String comentario,
            String dataAvaliacao,
            Integer avaliacao
    ){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("pilotoId", pilotoId);
        valores.put("clienteId", clienteId);
        valores.put("foto", foto);
        valores.put("comentario", comentario);
        valores.put("dataAvaliacao", dataAvaliacao);
        valores.put("avaliacao", avaliacao);

        resultado = db.insert("avaliacao", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro!";
        } else {
            return "Registro inserido com sucesso!";
        }
    }
}
