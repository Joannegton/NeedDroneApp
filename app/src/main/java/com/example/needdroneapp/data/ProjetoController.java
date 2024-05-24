package com.example.needdroneapp.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.needdroneapp.models.Projeto;

import java.util.ArrayList;
import java.util.List;

public class ProjetoController {
    private SQLiteDatabase db;
    private final CriarDb banco;

    public ProjetoController(Context context){
        banco = new CriarDb(context);
    }

    public String insereDados(
            String titulo,
            String descricao,
            String dataEvento,
            String rua,
            String cidadeEstado,
            Integer clienteId,
            Integer pilotoId
    ){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("titulo", titulo);
        valores.put("descricao", descricao);
        valores.put("dataEvento", dataEvento);
        valores.put("rua", rua);
        valores.put("cidadeEstado", cidadeEstado);
        valores.put("clienteId", clienteId);
        valores.put("pilotoId", pilotoId);

        resultado = db.insert("projeto", null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro!";
        } else {
            return "Registro inserido com sucesso!";
        }
    }

    //ATUALIZAÇÕES
    public String atualizarProjeto(
            Integer idProjeto,
            String titulo,
            String descricao,
            String dataEvento,
            String rua,
            String cidadeEstado
    ) {

        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("titulo", titulo);
        valores.put("descricao", descricao);
        valores.put("dataEvento", dataEvento);
        valores.put("rua", rua);
        valores.put("cidadeEstado", cidadeEstado);

        String where = "id = " + idProjeto;
        String msg = "";
        int linha = db.update("projeto", valores, where, null);
        if (linha == -1) {
            msg = "Erro ao alterar os dados!";
        } else {
            msg = "Dados alterados com sucesso!";
        }

        db.close();
        return msg;
    }

    public boolean projetoExiste(Integer idProjeto) {
        db = banco.getReadableDatabase();
        Cursor cursor = db.query("projeto", new String[]{"id"}, "id = ?", new String[]{String.valueOf(idProjeto)}, null, null, null);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existe;
    }
    public String atualizarStatusProjeto(Integer idProjeto, String status) {
        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("status", status);

        String where = "id = " + idProjeto;
        String msg = "";
        int linha = db.update("projeto", valores, where, null);
        if (linha == -1) {
            msg = "Erro ao alterar os dados!";
        } else {
            msg = "Dados alterados com sucesso!";
        }

        db.close();
        return msg;
    }

    //LISTAGENS
    public List<Projeto> listarTodosProjetos(){
        List<Projeto> projetoList = new ArrayList<>();

        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM projeto", null);

        if (cursor.moveToFirst()){
            do {
                Projeto projeto = new Projeto();
                projeto.setProjetoId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                projeto.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
                projeto.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
                projeto.setDataEvento(cursor.getString(cursor.getColumnIndexOrThrow("dataEvento")));
                projeto.setRua(cursor.getString(cursor.getColumnIndexOrThrow("rua")));
                projeto.setCidadeEstado(cursor.getString(cursor.getColumnIndexOrThrow("cidadeEstado")));
                projeto.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                projeto.setClienteId(cursor.getInt(cursor.getColumnIndexOrThrow("clienteId")));

                projetoList.add(projeto);
            } while (cursor.moveToNext());

        }
        db.close();
        return projetoList;
    }

    public List<Projeto> listarProjetosPorIdCliente(Integer idCliente){
        List<Projeto> projetoList = new ArrayList<>();

        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM projeto WHERE clienteId = " + idCliente, null);

        if (cursor.moveToFirst()){
            do {
                Projeto projeto = new Projeto();
                projeto.setProjetoId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                projeto.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
                projeto.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
                projeto.setDataEvento(cursor.getString(cursor.getColumnIndexOrThrow("dataEvento")));
                projeto.setRua(cursor.getString(cursor.getColumnIndexOrThrow("rua")));
                projeto.setCidadeEstado(cursor.getString(cursor.getColumnIndexOrThrow("cidadeEstado")));
                projeto.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                projeto.setClienteId(cursor.getInt(cursor.getColumnIndexOrThrow("clienteId")));

                projetoList.add(projeto);
            } while (cursor.moveToNext());

        }
        db.close();
        return projetoList;
    }

    public Cursor buscarProjeto(Integer projetoId) {
        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM projeto WHERE id = " + projetoId, null);

        cursor.moveToFirst();
        db.close();
        return cursor;

    }

    public Integer buscarClientePorProjeto(Integer projetoId) {
        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT clienteId FROM projeto WHERE id = " + projetoId, null);
        cursor.moveToFirst();
        @SuppressLint("Range") Integer clienteId = cursor.getInt(cursor.getColumnIndex("clienteId"));
        db.close();
        return clienteId;

    }
    public Integer buscarPilotoPorProjeto(Integer projetoId) {
        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pilotoId FROM projeto WHERE id = " + projetoId, null);
        cursor.moveToFirst();
        @SuppressLint("Range") Integer pilotoId = cursor.getInt(cursor.getColumnIndex("pilotoId"));
        db.close();
        return pilotoId;

    }
}
