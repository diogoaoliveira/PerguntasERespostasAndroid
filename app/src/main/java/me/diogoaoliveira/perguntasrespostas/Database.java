package me.diogoaoliveira.perguntasrespostas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by diogoaoliveira on 24/11/16.
 */

public class Database extends SQLiteOpenHelper {
    private static final String NOME_BD = "questoeserepostas.db";

    private static final String TABLE_USUARIO = "usuarios";
    private static final String TABLE_PERGUNTAS = "perguntas";
    private static final String TABLE_RESPOSTAS = "respostas";

    private static final String FIELD_ID_USUARIO = "idUsuario";
    private static final String FIELD_NOME_USUARIO = "nmUsuario";

    private static final String FIELD_ID_PERGUNTA = "idPergunta";
    private static final String FIELD_DESCRICAO_PERGUNTA = "dsPergunta";

    private static final String FIELD_ID_RESPOSTA = "idResposta";
    private static final String FIELD_DESCRICAO_RESPOSTA = "dsResposta";
    private static final String FIELD_CORRETA_RESPOSTA = "flCorreta";
    private static final String FIELD_FK_PERGUNTA_RESPOSTA = "idPergunta";


    private static final int DATABASE_VERSION = 1;

    Database(Context context) {
        super(context, NOME_BD, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIO +
                "("+ FIELD_ID_USUARIO +" integer PRIMARY KEY autoincrement," +
                FIELD_NOME_USUARIO + " TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PERGUNTAS +
                "("+ FIELD_ID_PERGUNTA +" integer PRIMARY KEY autoincrement," +
                FIELD_DESCRICAO_PERGUNTA + " TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_RESPOSTAS +
                "("+ FIELD_ID_RESPOSTA +" integer PRIMARY KEY autoincrement," +
                FIELD_DESCRICAO_RESPOSTA + " TEXT NOT NULL, " +
                FIELD_CORRETA_RESPOSTA + " integer NOT NULL, " +
                FIELD_FK_PERGUNTA_RESPOSTA + " integer,"
                + " FOREIGN KEY ("+FIELD_FK_PERGUNTA_RESPOSTA+") REFERENCES "+TABLE_PERGUNTAS+"("+FIELD_ID_PERGUNTA+"));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long salvarPergunta(String descricao) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_DESCRICAO_PERGUNTA, descricao);
        return db.insert(TABLE_PERGUNTAS, null, values);
    }

    public long salvarResposta(String descricao, int correta, int idPergunta) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_DESCRICAO_PERGUNTA, descricao);
        values.put(FIELD_CORRETA_RESPOSTA, correta);
        values.put(FIELD_FK_PERGUNTA_RESPOSTA, idPergunta);
        return db.insert(TABLE_RESPOSTAS, null, values);
    }

}
