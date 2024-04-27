package com.example.needdroneapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ClienteController {
    private SQLiteDatabase db;
    private final CriarDb banco;

    public  ClienteController(Context context){
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
            String cidadeEstado,
            String cep,
            String foto,
            String biografia,
            Integer avaliacao
    ){
        ContentValues valores;
        long resultado;
        db = banco.getReadableDatabase();

        valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("email", email);
        valores.put("password", password);
        valores.put("cpf", cpf);
        valores.put("dataNasc", dataNasc);
        valores.put("tel", tel);
        valores.put("whatsapp", whatsapp);
        valores.put("rua", rua);
        valores.put("cidadeEstado", cidadeEstado);
        valores.put("cep", cep);
        valores.put("foto", foto);
        valores.put("biografia", biografia);
        valores.put("avaliacaoCliente", avaliacao);

        resultado = db.insert("clientes", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro!";
        } else {
            return "Registro inserido com sucesso!";
        }
    }

    public Cursor carregaDadosPorId(int id){
        Cursor cursor;
            String[] campos = { "id", "nome", "email", "password", "cpf", "dataNasc", "tel", "whatsapp", "rua", "cidadeEstado", "cep", "foto", "biografia", "avaliacaoCliente"};

        String where = "id=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query("clientes", campos, where, null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    public Cursor carregaDadosLogin(String email, String password) {
        Cursor cursor;
        String[] campos = { "id", "email", "password"};

        // Corrigindo a cláusula where para adicionar o critério de seleção do email e senha
        String where = "email=? AND password=?";
        String[] whereArgs = { email, password };

        db = banco.getReadableDatabase();
        cursor = db.query("clientes", campos, where, whereArgs, null, null, null);

        // Não é necessário verificar se o cursor é nulo antes de chamar moveToFirst
        if (cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }



    public String alteraDados(
            int id,
            String nome,
            String dataNasc,
            String tel,
            Boolean whatsapp,
            String rua,
            String cidadeEstado,
            String cep,
            String foto,
            String biografia
    ){
        String msg = "Dados alterados com sucesso!!!";

        db = banco.getReadableDatabase();

        ContentValues valores;
        valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("dataNasc", dataNasc);
        valores.put("tel", tel);
        valores.put("whatsapp", whatsapp);
        valores.put("rua", rua);
        valores.put("cidadeEstado", cidadeEstado);
        valores.put("cep", cep);
        valores.put("foto", foto);
        valores.put("biografia", biografia);

        String where = "id = " + id;

        int linha;
        linha = db.update("clientes", valores, where, null);

        if(linha < 1){
            msg = "Erro ao alterar os dados!";
        } else {
            msg = "Dados alterados com sucesso!";
        }

        db.close();
        return  msg;
    }

    public String excluirDados(int id){
        String msg = "Registro Excluído" ;
        db = banco.getReadableDatabase();

        String where = "codigo = " + id ;

        int linhas ;
        linhas = db.delete("contatos", where, null) ;

        if ( linhas < 1) {
            msg = "Erro ao Excluir" ;
        }

        db.close();
        return msg;
    }


}
