package com.example.needdroneapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbController {
    private SQLiteDatabase db;
    private CriarDb banco;

    public  DbController(Context context){
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
        String[] campos = { "id", "nome", "email", "password", "dataNasc", "tel", "whatsapp", "rua", "cidade", "estado", "cep", "foto", "biografia", "avaliacao"};

        String where = "id=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query("usuarios", campos, where, null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    public String alteraDados(
            int id,
            String nome,
            String email,
            String password,
            String dataNasc,
            String tel,
            Boolean whatsapp,
            String rua,
            String cidade,
            String estado,
            String cep,
            String foto,
            String biografia,
            String avaliacao
    ){
        String msg = "Dados alterados com sucesso!!!";

        db = banco.getReadableDatabase();

        ContentValues valores = new ContentValues();
        valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("email", email);
        valores.put("password", password);
        valores.put("dataNasc", dataNasc);
        valores.put("tel", tel);
        valores.put("whatsapp", whatsapp);
        valores.put("rua", rua);
        valores.put("cidade", cidade);
        valores.put("estado", estado);
        valores.put("cep", cep);
        valores.put("foto", foto);
        valores.put("biografia", biografia);
        valores.put("avaliacao", avaliacao);

        String where = "codigo = " + id;

        int linha;
        linha = db.update("usuarios", valores, where, null);

        if(linha < 1){
            msg = "Erro ao alterar os dados!";
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
