package br.com.hbsis.testeandroidnatan.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by natan on 06/02/17.
 */

public class DialogUtils {

    public static void mostrarAlerta(Context context, String msg){
        String appTitle = "Alerta";
        mostrarAlerta(context,appTitle,msg);
    }
    public static void mostrarAlerta(Context context, String title, String msg){
        mostrarAlerta(context,title,msg,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });
    }

    public static void mostrarAlerta(Context context, String title, String msg, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setNeutralButton(android.R.string.ok, listener)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
