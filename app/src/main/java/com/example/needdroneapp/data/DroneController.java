package com.example.needdroneapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.needdroneapp.models.Drone;

import java.util.ArrayList;
import java.util.List;

public class DroneController {
    private SQLiteDatabase db; //variavel para manipular o banco de dados
    private final CriarDb banco; //variavel para criar o banco de dados

    public DroneController(Context context){ //construtor
        banco = new CriarDb(context); //cria o banco de dados
    }

    public String insereDados( //metodo para inserir dados
            String nome, //nome do drone
            String tipo, //tipo do drone
            String qualidadeImagem, //qualidade da imagem
            String autonomia, //autonomia
            String areaCobertura, //area de cobertura
            String status, //status
            Boolean imgSobreposicao,
            String foto,
            Integer pilotoId
    ){
        ContentValues valores;
        long resultado; //variavel para armazenar o resultado da inserção
        db = banco.getWritableDatabase(); //abre o banco de dados

        //cria um objeto para armazenar os valores
        valores = new ContentValues();
        valores.put("nome", nome); //adiciona o nome
        valores.put("tipoDrone", tipo); //adiciona o tipo
        valores.put("imgQualidade", qualidadeImagem); //adiciona a qualidade da imagem
        valores.put("autonomia", autonomia); //adiciona a autonomia
        valores.put("areaCobertura", areaCobertura); //adiciona a area de cobertura
        valores.put("status", status); //adiciona o status
        valores.put("imgSobreposicao", imgSobreposicao); //adiciona a imagem de sobreposição
        valores.put("foto", foto); //adiciona a foto
        valores.put("pilotoId", pilotoId); //adiciona o id do piloto

        try {
            resultado = db.insert("drones", null, valores);
            if (resultado == -1) {
                return "Erro ao inserir registro!";
            } else {
                return "Registro inserido com sucesso!";
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Erro ao inserir dados", e);
            return "Erro ao inserir registro: " + e.getMessage();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }




    public void atualizarDrone(
            int idDrone,
            String nome,
            String tipo,
            String qualidadeImagem,
            String autonomia,
            String areaCobertura,
            String status,
            Boolean imgSobreposicao,
            String foto
    ) {
        db = banco.getWritableDatabase(); //abre o banco de dados
        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("tipoDrone", tipo);
        valores.put("imgQualidade", qualidadeImagem);
        valores.put("autonomia", autonomia);
        valores.put("areaCobertura", areaCobertura);
        valores.put("status", status);
        valores.put("imgSobreposicao", imgSobreposicao);
        valores.put("foto", foto);

        db.update("drones", valores, "id = ?", new String[]{String.valueOf(idDrone)});
        db.close();
    }

    public void atualizarStatus(int idDrone, String status) {
        db = banco.getWritableDatabase(); //abre o banco de dados
        ContentValues valores = new ContentValues();
        valores.put("status", status);

        db.update("drones", valores, "id = ?", new String[]{String.valueOf(idDrone)});
        db.close();
    }

    public void excluirDrone(int idDrone) {
        try {
            db = banco.getWritableDatabase(); //abre o banco de dados
            db.delete("drones", "id = ?", new String[]{String.valueOf(idDrone)});
        } catch (Exception e) {
            Log.e("DB_ERROR", "Erro ao excluir drone", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
    public Cursor carregarDadosDrone(int idDrone) {
        db = banco.getReadableDatabase(); //abre o banco de dados
        Cursor cursor = db.rawQuery("SELECT * FROM drones WHERE id = ?", new String[]{String.valueOf(idDrone)});
        cursor.moveToFirst();
        return cursor;
    }

    public List<Drone> pegarTodosDrones(){
        List<Drone> droneList = new ArrayList<>();

        db = banco.getReadableDatabase(); //abre o banco de dados
        Cursor cursor = db.rawQuery("SELECT * FROM drones", null); //executa a query para pegar todos os drones

        if (cursor.moveToFirst()) {
            do {
                Drone drone = new Drone();
                drone.setDroneId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                drone.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                drone.setTipoDrone(cursor.getString(cursor.getColumnIndexOrThrow("tipoDrone")));
                drone.setImgQualidade(cursor.getString(cursor.getColumnIndexOrThrow("imgQualidade")));
                drone.setAutonomia(cursor.getString(cursor.getColumnIndexOrThrow("autonomia")));
                drone.setAreaCobertura(cursor.getString(cursor.getColumnIndexOrThrow("areaCobertura")));
                drone.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));

                int imgSobreposicaoInt = cursor.getInt(cursor.getColumnIndexOrThrow("imgSobreposicao"));
                boolean imgSobreposicao = imgSobreposicaoInt != 0;
                drone.setImgSobreposicao(imgSobreposicao);
                //drone.setFoto(cursor.getString(cursor.getColumnIndexOrThrow("foto")));

                drone.setPilotoId(cursor.getInt(cursor.getColumnIndexOrThrow("pilotoId")));

                droneList.add(drone);
            } while (cursor.moveToNext());
        }



        cursor.close();
        return droneList;
    }
}
