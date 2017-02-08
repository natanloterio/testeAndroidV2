package br.com.hbsis.testeandroidnatan.impl.dao.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.hbsis.testeandroidnatan.base.IDao;

/**
 * Created by natan on 07/02/17.
 */

public class DaoPessoaLocal implements IDao<Pessoa> {
    private static final String DATABASE = "DATABAASE";
    private Context context;

    private SQLiteOpenHelper dbHelper;
    private String SELECT_SQL="Select * from pessoa";

    private final String COLUNA_ID = "id";
    private final String COLUNA_NOME = "nome";
    private final String COLUNA_SOBRENOME = "sobrenome";
    private final String COLUNA_DATANASCIMENTO = "dataNascimento";
    private final String TABLE_NAME = "pessoa";

    public DaoPessoaLocal(Context c){
        this.context = c;
        this.dbHelper = getNewDBHelper();
    }

    public long insert(Pessoa p){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select max("+COLUNA_ID+") from " + TABLE_NAME, null);
        cursor.moveToFirst();
        int last = cursor.getInt(0)+1;

        ContentValues values = new ContentValues();
        values.put(COLUNA_ID, last);
        values.put(COLUNA_NOME, p.getNome());
        values.put(COLUNA_SOBRENOME, p.getSobrenome());
        values.put(COLUNA_DATANASCIMENTO, p.getDataNascimento().getTime());


        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    @Override
    public List<Pessoa> getAll() {

        Cursor c = dbHelper.getReadableDatabase().rawQuery(SELECT_SQL, null);


        List<Pessoa> pessoas = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                String id = c.getString(0);
                String nome = c.getString(c.getColumnIndex(COLUNA_NOME));
                String sobrenome = c.getString(c.getColumnIndex(COLUNA_SOBRENOME));
                long longdataNascimenot = c.getLong(c.getColumnIndex(COLUNA_DATANASCIMENTO));
                Date dataNascimento = new Date(longdataNascimenot);
                Pessoa p = new Pessoa();
                p.setNome(nome);
                p.setSobrenome(sobrenome);
                p.setDataNascimento(dataNascimento);
                pessoas.add(p);
                c.moveToNext();
            } while (!c.isLast());
        }
        return pessoas;
    }


    public SQLiteOpenHelper getNewDBHelper() {
        return new PessoaDBHelper(context);
    }
}
