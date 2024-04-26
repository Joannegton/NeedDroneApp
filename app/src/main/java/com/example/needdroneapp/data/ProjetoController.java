package com.example.needdroneapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ProjetoController {
    private SQLiteDatabase db;
    private final CriarDb banco;

    public ProjetoController(Context context){
        banco = new CriarDb(context);
    }

    public String insereDados(
            String titulo,
            String descricao,
            String tipoDrone,
            String imgQualidade,
            String imgSobreposicao,
            String cobertArea,
            String dataEvento,
            String localizacao,
            String valor,
            String status,
            String clienteId,
            String clientNome,
            String clienteAvaliacao,
            String pilotoId,
            String pilotoNome
    ){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("titulo", titulo);
        valores.put("descricao", descricao);
        valores.put("tipoDrone", tipoDrone);
        valores.put("imgQualidade", imgQualidade);
        valores.put("imgSobreposicao", imgSobreposicao);
        valores.put("cobertArea", cobertArea);
        valores.put("dataEvento", dataEvento);
        valores.put("localizacao", localizacao);
        valores.put("status", status);
        valores.put("clienteId", clienteId);
        valores.put("clientNome", clientNome);
        valores.put("clienteAvaliacao", clienteAvaliacao);
        valores.put("pilotoId", pilotoId);
        valores.put("pilotoNome", pilotoNome);


        resultado = db.insert("projeto", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro!";
        } else {
            return "Registro inserido com sucesso!";
        }
    }
}
