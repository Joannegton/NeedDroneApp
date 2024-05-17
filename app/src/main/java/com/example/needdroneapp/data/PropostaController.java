package com.example.needdroneapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PropostaController {
    private SQLiteDatabase db;

    private final CriarDb banco;

    public PropostaController(Context context){
        banco = new CriarDb(context);
    }

    public String insereDados(
            Integer projetoId,
            Integer clienteId,
            Integer pilotoId,
            Float ofertaInicial,
            String detalhesProposta,
            String status,
            Integer droneId
    ){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("projetoId", projetoId);
        valores.put("clienteId", clienteId);
        valores.put("pilotoId", pilotoId);
        valores.put("ofertaInicial", ofertaInicial);
        valores.put("detalhesProposta", detalhesProposta);
        valores.put("status", status);
        valores.put("droneId", droneId);


        resultado = db.insert("proposta", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro!";
        } else {
            return "Registro inserido com sucesso!";
        }
    }

    public Cursor recuperarInfosIdProjeto(int idProjeto){
        Cursor dados;
        String[] campos = {"tituloProjeto", "clienteId", "pilotoId", "ofertaInicial", "detalhesProposta", "status", "droneId"};
        String where = "projetoId= " + idProjeto;
        db = banco.getReadableDatabase();
        dados = db.query("proposta", campos, where, null, null, null, null);

        if(dados != null){
            dados.moveToFirst();
        }
        db.close();
        return dados;
    }
}
