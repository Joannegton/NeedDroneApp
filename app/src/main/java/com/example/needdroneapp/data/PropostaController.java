package com.example.needdroneapp.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.needdroneapp.models.Proposta;

import java.util.ArrayList;
import java.util.List;

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

    @SuppressLint("Range")
    public List<Proposta> buscarPropostasProjeto(int projetoId){
        List<Proposta> listaPropostas = new ArrayList<>();

        String[] campos = {"id", "projetoId", "pilotoId", "clienteId", "ofertaInicial", "detalhesProposta", "status", "droneId"};
        String where = "projetoId= " + projetoId;
        db = banco.getReadableDatabase();
        Cursor dados = db.query("proposta", campos, where, null, null, null, null);

        if(dados.moveToFirst()){
            do{
                Proposta proposta = new Proposta();
                proposta.setId(dados.getInt(dados.getColumnIndex("id")));
                proposta.setProjetoId(dados.getInt(dados.getColumnIndex("projetoId")));
                proposta.setPilotoId(dados.getInt(dados.getColumnIndex("pilotoId")));
                proposta.setClienteId(dados.getInt(dados.getColumnIndex("clienteId")));
                proposta.setOfertaInicial(dados.getFloat(dados.getColumnIndex("ofertaInicial")));
                proposta.setDetalhesProposta(dados.getString(dados.getColumnIndex("detalhesProposta")));
                proposta.setStatus(dados.getString(dados.getColumnIndex("status")));
                proposta.setDroneId(dados.getInt(dados.getColumnIndex("droneId")));

                listaPropostas.add(proposta);
            } while (dados.moveToNext());
        }

        db.close();
        return listaPropostas;
    }

    public String atualizarStatusProposta(Integer idProposta, String status){
        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();

        valores.put("status", status);

        String where = "id = " + idProposta;
        String msg = "";
        int linha = db.update("proposta", valores, where, null);
        if (linha == -1) {
            msg = "Erro ao alterar os dados!";
        } else {
            msg = "Dados alterados com sucesso!";
        }

        db.close();
        return msg;
    }

    public Integer buscarPilotoPorProposta(Integer propostaId) {
        int pilotoId = 0;
        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pilotoId FROM proposta WHERE id = " + propostaId, null);
        if (cursor.moveToFirst()) {
            pilotoId = cursor.getInt(cursor.getColumnIndexOrThrow("pilotoId"));
        }
        db.close();
        return pilotoId;
    }

    @SuppressLint("Range")
    public List<Proposta> buscarPropostasProjetoUserId(Integer projetoId, Integer userId) {
        List<Proposta> listaPropostas = new ArrayList<>();

        String[] campos = {"id", "projetoId", "pilotoId", "clienteId", "ofertaInicial", "detalhesProposta", "status", "droneId"};
        String where = "projetoId= " + projetoId + " AND pilotoId = " + userId;
        db = banco.getReadableDatabase();
        Cursor dados = db.query("proposta", campos, where, null, null, null, null);

        if(dados.moveToFirst()){
            do{
                Proposta proposta = new Proposta();
                proposta.setId(dados.getInt(dados.getColumnIndex("id")));
                proposta.setProjetoId(dados.getInt(dados.getColumnIndex("projetoId")));
                proposta.setPilotoId(dados.getInt(dados.getColumnIndex("pilotoId")));
                proposta.setClienteId(dados.getInt(dados.getColumnIndex("clienteId")));
                proposta.setOfertaInicial(dados.getFloat(dados.getColumnIndex("ofertaInicial")));
                proposta.setDetalhesProposta(dados.getString(dados.getColumnIndex("detalhesProposta")));
                proposta.setStatus(dados.getString(dados.getColumnIndex("status")));
                proposta.setDroneId(dados.getInt(dados.getColumnIndex("droneId")));

                listaPropostas.add(proposta);
            } while (dados.moveToNext());
        }

        db.close();
        return listaPropostas;
    }
}
