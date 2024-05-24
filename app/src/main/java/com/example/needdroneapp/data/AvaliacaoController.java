package com.example.needdroneapp.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.needdroneapp.models.Avaliacao;

import java.util.ArrayList;
import java.util.List;

public class AvaliacaoController {
    private SQLiteDatabase db;
    private CriarDb banco;

    public AvaliacaoController(Context context){
        banco = new CriarDb(context);
    }

    public String insereDados(
            Integer avaliadorId,
            Integer avaliadoId,
            String comentario,
            String dataAvaliacao,
            Float avaliacao
    ){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("avaliadorId", avaliadorId);
        valores.put("avaliadoId", avaliadoId);
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


    @SuppressLint("Range")
    public List<Avaliacao> pegarAvaliacoes() {
        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM avaliacoes", null);
        cursor.moveToFirst();

        List<Avaliacao> avaliacoes = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++){
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setPilotoId(cursor.getInt(cursor.getColumnIndex("pilotoId")));
            avaliacao.setClienteId(cursor.getInt(cursor.getColumnIndex("clienteId")));
            avaliacao.setComentario(cursor.getString(cursor.getColumnIndex("comentario")));
            avaliacao.setAvaliacao(cursor.getFloat(cursor.getColumnIndex("avaliacao")));
            avaliacoes.add(avaliacao);
            cursor.moveToNext();
        }

        return avaliacoes;
    }

    @SuppressLint("Range")
    public Float pegarAvaliacaoPiloto(int pilotoId){
        float valorAvaliacao = 0;
        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM avaliacao WHERE pilotoId = " + pilotoId, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++){
            valorAvaliacao += (cursor.getFloat(cursor.getColumnIndex("avaliacao")));
        }

        return valorAvaliacao/cursor.getCount();
    }
}
