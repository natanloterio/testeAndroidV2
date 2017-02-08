package br.com.hbsis.testeandroidnatan.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.hbsis.testeandroidnatan.impl.dao.base.PessoaDBHelper;

/**
 * Created by natan on 08/02/17.
 */

public class SQLiteBackupManager {

    private String backupName;

    public String fazerBackup(Context context) throws IOException {

        final File dbFile = context.getDatabasePath(PessoaDBHelper.DATABASE_NAME);

        FileInputStream fis = new FileInputStream(dbFile);
        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        String outFileName = folder + getBackupName();

        File backupFile = new File(folder,getBackupName());
        if(!backupFile.exists()){
            Log.w(this.getClass().getSimpleName(),"Creating file:"+backupFile.getAbsolutePath());

            backupFile.createNewFile();
        }

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        // Close the streams
        output.flush();
        output.close();
        fis.close();

        return outFileName;
    }

    public String getBackupName() {
        String prefix = "backup_avaliacao_";
        String sufix = ".bkp";
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time = fmt.format(new Date());
        String backupName = prefix + time + sufix;
        return backupName;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getDownloadsStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), albumName);
        if (!file.mkdirs()) {
            Log.e(this.getClass().getSimpleName(), "Directory not created");
        }
        return file;
    }
}
