package br.com.hbsis.testeandroidnatan.impl.dao.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PessoaDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "hbsis.db";
    private String CREATE_SQL="CREATE TABLE pessoa(\n" +
            "   id INT PRIMARY KEY NOT NULL,\n" +
            "   nome TEXT NOT NULL,\n" +
            "   sobrenome TEXT NOT NULL,\n" +
            "   dataNascimento INTEGER)";

    public PessoaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE pessoa ADD COLUMN ativo integer;");
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}