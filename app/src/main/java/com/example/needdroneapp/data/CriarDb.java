package com.example.needdroneapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriarDb extends SQLiteOpenHelper { //estende para obter os metodos de SQLiteOpenHelper
    private static final String NOME_DB = "needDroneDB.db";
    private static final int VERSAO = 1;

    public CriarDb(Context context){
        super(context, NOME_DB, null, VERSAO);
    }

    @Override //escreve um novo metodo
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE clientes ("
                + "id integer primary key autoincrement,"
                + "nome text,"
                + "email text,"
                + "password text,"
                + "dataNasc text,"
                + "tel text,"
                + "whatsapp boolean,"
                + "rua text,"
                + "cidade text,"
                + "estado text,"
                + "cep text,"
                + "foto text,"
                + "biografia text,"
                + "avaliacao text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");
        onCreate(db);
    }
}
