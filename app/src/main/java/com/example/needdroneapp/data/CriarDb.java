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
        String sqlClientes = "CREATE TABLE clientes ("
                + "id integer primary key autoincrement,"
                + "nome text NOT NULL,"
                + "email text NOT NULL,"
                + "password text NOT NULL,"
                + "cpf text NOT NULL,"
                + "dataNasc text,"
                + "tel text,"
                + "whatsapp boolean,"
                + "rua text,"
                + "cidadeEstado text,"
                + "cep text,"
                + "foto text,"
                + "biografia text,"
                + "avaliacaoCliente integer);";

        String sqlPiloto = "CREATE TABLE piloto ("
                + "id integer primary key autoincrement,"
                + "nome text NOT NULL,"
                + "email text NOT NULL,"
                + "password text NOT NULL,"
                + "dataNasc text,"
                + "tel text,"
                + "whatsapp boolean,"
                + "rua text,"
                + "cidade text,"
                + "estado text,"
                + "cep text,"
                + "foto text,"
                + "biografia text,"
                + "avaliacaoPiloto text,"
                + "licencaPilotagem text);";

        String sqlAvaliacoes = "CREATE TABLE avaliacoes ("
                + "id integer primary key autoincrement,"
                + "nome text,"
                + "pilotoId integer,"
                + "clienteId integer,"
                + "foto text,"
                + "comentario text,"
                + "dataAvaliacao date,"
                + "avaliacao int,"
                + "FOREIGN KEY(clienteId) REFERENCES clientes(id),"
                + "FOREIGN KEY(pilotoId) REFERENCES piloto(id));";




        db.execSQL(sqlClientes);
        db.execSQL(sqlPiloto);
        db.execSQL(sqlAvaliacoes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL("DROP TABLE IF EXISTS piloto");
        db.execSQL("DROP TABLE IF EXISTS avaliacoes");
        onCreate(db);
    }
}
