package com.example.needdroneapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriarDb extends SQLiteOpenHelper { //estende para obter os metodos de SQLiteOpenHelper
    private static final String NOME_DB = "needDroneDB.db";
    private static final int VERSAO = 9;

    public CriarDb(Context context){
        super(context, NOME_DB, null, VERSAO);
    }

    @Override //escreve um novo metodo
    public void onCreate(SQLiteDatabase db) {
        String drones = "CREATE TABLE drones ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT,"
                + "tipoDrone TEXT, "
                + "imgQualidade TEXT CHECK(imgQualidade IN ('SD', 'HD', 'Full HD', 'Quad HD', '4K')),"
                + "autonomia TEXT CHECK(autonomia IN ('até 60 min', 'entre 61 e 90 min', 'entre 91 e 120 min', '120+')),"
                + "areaCobertura TEXT CHECK(areaCobertura IN ('até 100m²', 'até 200m²', 'até 400m²', '500m²+')),"
                + "status TEXT DEFAULT 'Ativo' CHECK(status IN ('Ativo', 'Manutenção', 'Inativo')),"
                + "imgSobreposicao TEXT," // Ajuste aqui INTEGER DEFAULT 0 CHECK(imgSobreposicao IN (0, 1))
                + "foto TEXT,"
                + "pilotoId Text,"
                + "FOREIGN KEY(pilotoId) REFERENCES piloto(id));";

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
                + "cpf text NOT NULL,"
                + "dataNasc text,"
                + "tel text,"
                + "whatsapp boolean,"
                + "rua text,"
                + "cidadeEstado text,"
                + "cep text,"
                + "foto text,"
                + "biografia text,"
                + "avaliacaoPiloto integer,"
                + "experiencia text,"
                + "especializacao text,"
                + "licencaPilotagem text);";

        String sqlAvaliacoes = "CREATE TABLE avaliacoes ("
                + "id integer primary key autoincrement,"
                + "nome text,"
                + "pilotoId integer,"
                + "clienteId integer,"
                + "foto text,"
                + "comentario text,"
                + "dataAvaliacao text,"
                + "avaliacao integer,"
                + "FOREIGN KEY(clienteId) REFERENCES clientes(id),"
                + "FOREIGN KEY(pilotoId) REFERENCES piloto(id));";


        String projeto = "CREATE TABLE projeto ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT,"
                + "descricao TEXT,"
                + "dataEvento TEXT,"
                + "rua TEXT,"
                + "cidadeEstado TEXT,"
                + "status TEXT DEFAULT 'Pendente' CHECK(status IN ('Andamento', 'Cancelado', 'Finalizado', 'Pendente')),"
                + "clienteId INTEGER,"
                + "pilotoId INTEGER,"
                + "FOREIGN KEY(clienteId) REFERENCES cliente(id),"
                + "FOREIGN KEY(pilotoId) REFERENCES piloto(id));";

        String proposta = "CREATE TABLE proposta ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "projetoId TEXT NOT NULL,"
                + "tituloProjeto TEXT,"
                + "clienteId TEXT,"
                + "clienteNome TEXT,"
                + "pilotoId TEXT,"
                + "pilotoNome TEXT,"
                + "ofertaInicial TEXT NOT NULL,"
                + "ofertaFinal TEXT NOT NULL,"
                + "detalhesProposta TEXT NOT NULL,"
                + "status TEXT DEFAULT 'Ativo' CHECK(status IN ('Ativo', 'Manutencao', 'Inativo')),"
                + "droneId TEXT,"
                + "FOREIGN KEY(projetoId) REFERENCES projeto(id),"
                + "FOREIGN KEY(clienteId) REFERENCES cliente(id),"
                + "FOREIGN KEY(pilotoId) REFERENCES piloto(id),"
                + "FOREIGN KEY(droneId) REFERENCES drone(_id),"
                + "FOREIGN KEY(clienteId, clienteNome) REFERENCES cliente(id, nome),"
                + "FOREIGN KEY(pilotoId, pilotoNome) REFERENCES piloto(id, nome)"
                + ");";



        db.execSQL(drones);
        db.execSQL(sqlClientes);
        db.execSQL(sqlPiloto);
        db.execSQL(sqlAvaliacoes);
        db.execSQL(projeto);
        db.execSQL(proposta);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL("DROP TABLE IF EXISTS piloto");
        db.execSQL("DROP TABLE IF EXISTS avaliacoes");
        db.execSQL("DROP TABLE IF EXISTS drones");
        db.execSQL("DROP TABLE IF EXISTS projeto");
        db.execSQL("DROP TABLE IF EXISTS proposta");
        onCreate(db);
    }
}
