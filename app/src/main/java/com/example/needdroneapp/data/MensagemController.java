package com.example.needdroneapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.needdroneapp.models.Mensagem;

import java.util.ArrayList;
import java.util.List;

public class MensagemController {
    private SQLiteDatabase db;
    private final CriarDb banco;

    public MensagemController(Context context){
        banco = new CriarDb(context);
    }

    public String insereDados(
            Integer remetenteId,
            Integer destinatarioId,
            String texto,
            String dataHora,
            Boolean enviada
    ){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("remetenteId", remetenteId);
        valores.put("destinatarioId", destinatarioId);
        valores.put("texto", texto);
        valores.put("dataHora", dataHora);
        valores.put("enviada", enviada);

        resultado = db.insert("mensagens", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro!";
        } else {
            return "Registro inserido com sucesso!";
        }
    }

    public List<Mensagem> listarMensagensEntreUsuarios(Integer usuarioId1, Integer usuarioId2){
        List<Mensagem> mensagemList = new ArrayList<>();

        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mensagens WHERE (remetenteId = " + usuarioId1 + " AND destinatarioId = " + usuarioId2 + ") OR (remetenteId = " + usuarioId2 + " AND destinatarioId = " + usuarioId1 + ")", null);

        if (cursor.moveToFirst()){
            do {
                Mensagem mensagem = new Mensagem();
                mensagem.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                mensagem.setRemetenteId(cursor.getInt(cursor.getColumnIndexOrThrow("remetenteId")));
                mensagem.setDestinatarioId(cursor.getInt(cursor.getColumnIndexOrThrow("destinatarioId")));
                mensagem.setTexto(cursor.getString(cursor.getColumnIndexOrThrow("texto")));
                mensagem.setDataHora(cursor.getString(cursor.getColumnIndexOrThrow("dataHora")));

                mensagemList.add(mensagem);
            } while (cursor.moveToNext());

        }
        db.close();
        return mensagemList;
    }
}