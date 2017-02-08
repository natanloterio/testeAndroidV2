package br.com.hbsis.testeandroidnatan.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import br.com.hbsis.testeandroidnatan.util.DialogUtils;

/**
 * Created by natan on 06/02/17.
 */

public abstract class BaseActivity<PresenterClass> extends AppCompatActivity implements IMainView, IViewNotifier{

    // Presenter da activity
    private PresenterClass presenter;

    private boolean deverReinicializarPresenter = true;
    private ProgressDialog progressDialog;
    private ViewGroup containerView;
    private View contentView;
    private View splash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenterNewsInstance();
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (deveReinicializarPresenter()) {
                presenter = getPresenterNewsInstance();
            }

            ((BasePresenter)presenter).onResume();

            deverReinicializarPresenter = false;
        }catch (Exception e){
            e.printStackTrace();
            DialogUtils.mostrarAlerta(BaseActivity.this,"Alerta","Aconteceu um problema durante ao abrir inicializar a tela. Contate o suporte",new  DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            } );
        }
    }

    @Override
    public void mostrarAlerta(String s) {
        DialogUtils.mostrarAlerta(BaseActivity.this,"Alerta",s,new  DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        } );
    }

    @Override
    public void mostrarAlertaFatal(String s) {
        DialogUtils.mostrarAlerta(BaseActivity.this,"Erro grave",s,new  DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        } );
    }

    @Override
    public void mostrarProgressDialog() {
        mostrarAlerta("Carregando...");
    }


    @Override
    public void mostrarProgressDialog(String message) {
        esconderProgressDialog();
        progressDialog = new ProgressDialog(BaseActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void esconderProgressDialog() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    /**
     * Retorna uma instancia do @{@link BasePresenter<PresenterClass>}
     */
    protected PresenterClass getPresenter(){
        return this.presenter;
    }
    /**
     * Retorna boolean indicando se o presenter deve ser reinicializado
     * @return @boolean
     */
    private boolean deveReinicializarPresenter() {
        return deverReinicializarPresenter;
    }

    /**
     * Retorna uma instânca de @{@link BasePresenter}
     * @return @{@link BasePresenter}
     */
    protected abstract PresenterClass getPresenterNewsInstance();

}
