package com.example.needdroneapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PilotoController implements UsuarioController{
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
            String cidadeEstado,
            String cep,
            String foto,
            String biografia,
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
        valores.put("cidadeEstado", cidadeEstado);
        valores.put("cep", cep);
        valores.put("foto", foto);
        valores.put("biografia", biografia);
        valores.put("avaliacaoPiloto", 0);
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
        String[] campos = {"id", "nome", "email", "password", "cpf", "dataNasc", "tel", "whatsapp", "rua", "cidadeEstado", "cep", "foto", "biografia", "avaliacaoPiloto", "licencaPilotagem", "experiencia", "especializacao"};
        String where = "id = " + id;
        db = banco.getReadableDatabase();
        cursor = db.query("piloto", campos, where, null, null, null, null, null);

        if(cursor != null){
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
        cursor = db.query("piloto", campos, where, whereArgs, null, null, null);

        // Não é necessário verificar se o cursor é nulo antes de chamar moveToFirst
        if (cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    @Override
    public String pegarNomePorId(Integer userId) {
        Cursor cursor;
        String nome = null;
        db = banco.getReadableDatabase();
        cursor = db.rawQuery("SELECT nome FROM piloto WHERE id = " + userId, null);
        if (cursor.moveToFirst()){
            nome = cursor.getString(0);
        }
        db.close();
        return nome;
    }

    @Override
    public Float pegarAvaliacaoPorId(Integer pilotoId) {
        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT avaliacaoPiloto FROM piloto WHERE id = " + pilotoId, null);
        cursor.moveToFirst();
        Float avaliacao = cursor.getFloat(0);
        db.close();
        return avaliacao;
    }

    public void atualizarAvaliacao(Integer pilotoId, Float avaliacao){
        db = banco.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("avaliacaoCliente", avaliacao);
        String where = "id = " + pilotoId;
        db.update("clientes", valores, where, null);
        db.close();
    }

    public String atualizaDados(
            int id,
            String nome,
            String dataNasc,
            String tel,
            Boolean whatsapp,
            String rua,
            String cidadeEstado,
            String cep,
            String foto,
            String biografia,
            String experiencia,
            String especializacao
    ) {
        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("dataNasc", dataNasc);
        valores.put("tel", tel);
        valores.put("whatsapp", whatsapp);
        valores.put("rua", rua);
        valores.put("cidadeEstado", cidadeEstado);
        valores.put("cep", cep);
        valores.put("foto", foto);
        valores.put("biografia", biografia);
        valores.put("experiencia", experiencia);
        valores.put("especializacao", especializacao);

        String where = "id = " + id;
        db = banco.getWritableDatabase();
        long resultado = db.update("piloto", valores, where, null);
        db.close();

        String msg;
        if (resultado == -1){
            msg =  "Erro ao atualizar registro!";
        } else {
            msg =  "Registro atualizado com sucesso!";
        }

        return msg;
    }

    public String resetarSenha(String email){
        ContentValues valores = new ContentValues();
        String novaSenha = "1234";
        valores.put("password", novaSenha);

        String where = "email = ?";
        String[] whereArgs = { email };

        db = banco.getWritableDatabase();
        long resultado = db.update("piloto", valores, where, whereArgs);

        String msg;
        if (resultado == -1){
            long result = db.update("clientes", valores, where, whereArgs);
            db.close();
            if (result == -1){
                msg =  "Email não existe!";
            }else {
                msg =  "Senha temporária: " + novaSenha;
            }
        } else {
            msg =  "Senha temporária: " + novaSenha;
        }

        return msg;
    }
}
