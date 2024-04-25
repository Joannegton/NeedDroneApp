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

        String drone = "CREATE TABLE drone ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT,"
                + "tipoDrone TEXT,"
                + "cobertArea TEXT CHECK(cobertArea IN ('100', '200', '400', '500+')),"
                + "foto TEXT,"
                + "imgQualidade TEXT CHECK(imgQualidade IN ('SD', 'HD', 'Full HD', 'Quad HD', '4K')),"
                + "imgSobreposicao INTEGER DEFAULT 0 CHECK(imgSobreposicao IN (0, 1))," // Ajuste aqui
                + "autonomia TEXT CHECK(autonomia IN ('60', '90', '120', '121+')),"
                + "status TEXT DEFAULT 'Ativo' CHECK(status IN ('Ativo', 'Manutencao', 'Inativo')),"
                + "pilotoId INTEGER,"
                + "FOREIGN KEY(pilotoId) REFERENCES piloto(id));";

        String projeto = "CREATE TABLE projeto ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT,"
                + "descricao TEXT,"
                + "tipoDrone TEXT,"
                + "imgQualidade TEXT CHECK(imgQualidade IN ('SD', 'HD', 'Full HD', 'Quad HD', '4K')),"
                + "imgSobreposicao INTEGER DEFAULT 0 CHECK(imgSobreposicao IN (0, 1)),"
                + "cobertArea TEXT CHECK(cobertArea IN ('100', '200', '400', '500+')),"
                + "dataEvento DATE,"
                + "localizacao TEXT,"
                + "valor REAL,"
                + "status TEXT DEFAULT 'Ativo' CHECK(status IN ('Ativo', 'Manutencao', 'Inativo')),"
                + "clienteId INTEGER,"
                + "clientNome TEXT,"
                + "clienteAvaliacao REAL,"
                + "pilotoId INTEGER,"
                + "pilotoNome TEXT,"
                + "FOREIGN KEY(clienteId) REFERENCES cliente(id),"
                + "FOREIGN KEY(clientNome) REFERENCES cliente(nome),"
                + "FOREIGN KEY(clienteAvaliacao) REFERENCES cliente(avaliacao),"
                + "FOREIGN KEY(pilotoId) REFERENCES piloto(id),"
                + "FOREIGN KEY(pilotoNome) REFERENCES piloto(nome));";

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


        db.execSQL(sqlClientes);
        db.execSQL(sqlPiloto);
        db.execSQL(sqlAvaliacoes);
        db.execSQL(drone);
        db.execSQL(projeto);
        db.execSQL(proposta);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL("DROP TABLE IF EXISTS piloto");
        db.execSQL("DROP TABLE IF EXISTS avaliacoes");
        onCreate(db);
    }
}
