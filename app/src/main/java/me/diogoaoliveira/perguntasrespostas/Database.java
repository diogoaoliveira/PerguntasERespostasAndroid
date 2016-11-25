package me.diogoaoliveira.perguntasrespostas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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

    private static final String FIELD_ID_PERGUNTA = "_id";
    private static final String FIELD_DESCRICAO_PERGUNTA = "dsPergunta";

    private static final String FIELD_ID_RESPOSTA = "_id";
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
                "( "+ FIELD_ID_PERGUNTA +" integer PRIMARY KEY autoincrement," +
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
        values.put(FIELD_DESCRICAO_RESPOSTA, descricao);
        values.put(FIELD_CORRETA_RESPOSTA, correta);
        values.put(FIELD_FK_PERGUNTA_RESPOSTA, idPergunta);
        return db.insert(TABLE_RESPOSTAS, null, values);
    }

    public Cursor getListaPerguntas() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+ FIELD_ID_PERGUNTA +", " + FIELD_DESCRICAO_PERGUNTA +
                " FROM " + TABLE_PERGUNTAS + " ORDER BY " + FIELD_ID_PERGUNTA +
                " ASC";
        return db.rawQuery(query, null);
    }

    public List<Pergunta> getTodasPerguntas() {
        List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERGUNTAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Pergunta pergunta = new Pergunta();
                pergunta.setIdPergunta(Integer.parseInt(cursor.getString(0)));
                pergunta.setDsPergunta(cursor.getString(1));

                List<Resposta> respostas = getRespostasByPergunta(pergunta.getIdPergunta());
                for (int i = 0; i < respostas.size(); i++) {
                    pergunta.respostas.add(respostas.get(i));
                }

                // Adding contact to list
                listaPerguntas.add(pergunta);
            } while (cursor.moveToNext());
        }

        // return contact list
        return listaPerguntas;
    }

    private List<Resposta> getRespostasByPergunta(int idPergunta) {
        List<Resposta> listaRespostas = new ArrayList<Resposta>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_RESPOSTAS +
                        " WHERE " + FIELD_FK_PERGUNTA_RESPOSTA + " = ? ", new String[]{Integer.toString(idPergunta)});

        if (cursor.moveToFirst()) {
            do {
                Resposta resposta = new Resposta();
                resposta.setIdResposta(Integer.parseInt(cursor.getString(0)));
                resposta.setDsResposta(cursor.getString(1));
                resposta.setFlCorreta(Integer.parseInt(cursor.getString(2)));

                listaRespostas.add(resposta);
            } while (cursor.moveToNext());
        }
        return listaRespostas;
    }


    public Cursor getListaRespostas(String idPergunta) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+ FIELD_ID_RESPOSTA +", " + FIELD_DESCRICAO_RESPOSTA +
                " FROM " + TABLE_RESPOSTAS + " WHERE "+ FIELD_FK_PERGUNTA_RESPOSTA +" = "+ idPergunta +" ORDER BY " + FIELD_ID_RESPOSTA +
                " ASC";
        return db.rawQuery(query, null);
    }

    public boolean verificarResposta(int resposta) {
        long returnVal = -1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT "+ FIELD_CORRETA_RESPOSTA +" FROM " + TABLE_RESPOSTAS +
                        " WHERE " + FIELD_ID_RESPOSTA + " = ?", new String[]{Integer.toString(resposta)});
        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            returnVal = cursor.getInt(0);
        }
        if(returnVal == 1) {
            return true;
        } else {
            return false;
        }
    }
}
