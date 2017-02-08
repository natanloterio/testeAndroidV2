package br.com.hbsis.testeandroidnatan.base;

/**
 * Created by natan on 07/02/17.
 */

public interface IMainView {

    void mostrarAlerta(String s);

    void mostrarAlertaFatal(String s);

    void registerViews();

    void initializeViews();

    void mostrarProgressDialog();

    void mostrarProgressDialog(String message);

    void esconderProgressDialog();

}
