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
            String projetoId,
            String tituloProjeto,
            String clienteId,
            String clienteNome,
            String pilotoId,
            String pilotoNome,
            String ofertaInicial,
            String ofertaFinal,
            String detalhesProposta,
            String status,
            String droneId
    ){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("projetoId", projetoId);
        valores.put("tituloProjeto", tituloProjeto);
        valores.put("clienteId", clienteId);
        valores.put("clienteNome", clienteNome);
        valores.put("pilotoId", pilotoId);
        valores.put("pilotoNome", pilotoNome);
        valores.put("ofertaInicial", ofertaInicial);
        valores.put("ofertaFinal", ofertaFinal);
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
        String[] campos = {"tituloProjeto", "clienteId", "clienteNome", "pilotoId", "pilotoNome", "ofertaInicial", "detalhesProposta", "status", "droneId"};
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
