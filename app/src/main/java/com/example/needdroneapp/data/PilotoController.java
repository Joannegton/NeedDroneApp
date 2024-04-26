package com.example.needdroneapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PilotoController {
    private SQLiteDatabase db;
    private final CriarDb banco;

    public  PilotoController(Context context){
        banco = new CriarDb(context);
    }

    public String insereDados(
            String nome,
            String email,
            String password,
            String cpf,
            String dataNasc,
            String tel,
            Boolean whatsapp,
            String rua,
            String cidade,
            String cep,
            String foto,
            String biografia,
            String avaliacao,
            String licenca
    ){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("email", email);
        valores.put("password", password);
        valores.put("cpf", cpf);
        valores.put("dataNasc", dataNasc);
        valores.put("tel", tel);
        valores.put("whatsapp", whatsapp);
        valores.put("rua", rua);
        valores.put("cidadeEstado", cidade);
        valores.put("cep", cep);
        valores.put("foto", foto);
        valores.put("biografia", biografia);
        valores.put("avaliacaoPiloto", avaliacao);
        valores.put("licencaPilotagem", licenca);

        resultado = db.insert("piloto", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro!";
        } else {
            return "Registro inserido com sucesso!";
        }
    }

    public Cursor carregaDadosPorId(int id){
        Cursor cursor;
        String[] campos = {"id", "nome", "email", "password", "dataNasc", "tel", "whatsapp", "rua", "cidade", "estado", "cep", "foto", "biografia", "avaliacaoPiloto"};
        String where = "id = " + id;
        db = banco.getReadableDatabase();
        cursor = db.query("piloto", campos, where, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDados() {
        Cursor cursor = null;
        String[] campos = {"id", "nome", "email", "password", "dataNasc", "tel", "whatsapp", "rua", "cidade", "estado", "cep", "foto", "biografia", "avaliacaoPiloto"};
        db = banco.getReadableDatabase();
        return cursor;
    }

    /*public String atualizaDados(){
        valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("email", email);
        valores.put("password", password);
        valores.put("cpf", password);
        valores.put("dataNasc", dataNasc);
        valores.put("tel", tel);
        valores.put("whatsapp", whatsapp);
        valores.put("rua", rua);
        valores.put("cidadeEstado", cidade);
        valores.put("cep", cep);
        valores.put("foto", foto);
        valores.put("biografia", biografia);
        valores.put("avaliacaoPiloto", avaliacao);
        valores.put("licencaPilotagem", estado);
    } */
}
