package br.com.hbsis.testeandroidnatan.base;

import android.os.AsyncTask;

/**
 * Created by natan on 06/02/17.
 */

public class CarregarDados extends AsyncTask<Object, Object, Boolean> {

    private final IAntesCarregarDados listenerAntesCarregarDados;
    private final IDepoisCarregarDados listenerDepoisCarregarDados;
    private final ICarregarDados listenerCarregarDados;
    private final IErrorListener listenerOnError;

    public CarregarDados(IAntesCarregarDados listenerAntesCarregarDados, ICarregarDados listenerCarregarDados, IDepoisCarregarDados listenerDepoisCarregarDados, IErrorListener listenerOnError) throws Exception {
        if(listenerAntesCarregarDados == null ){
            throw new Exception("Você precisa informar um listener para antes de carregar os dados");
        }

        if(listenerCarregarDados == null){
            throw new Exception("Você precisa informar um listener para carregar os dados");
        }

        if(listenerDepoisCarregarDados == null) {
            throw new Exception("Você precisa informar um listener para depois de carregar os dados");
        }
        this.listenerAntesCarregarDados = listenerAntesCarregarDados;
        this.listenerCarregarDados = listenerCarregarDados;
        this.listenerDepoisCarregarDados = listenerDepoisCarregarDados;
        this.listenerOnError = listenerOnError;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listenerAntesCarregarDados.antesCarregarDadosAsyncTask();
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        try {
            listenerCarregarDados.carregarDadosAsyncTask();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if(success) {
            listenerDepoisCarregarDados.depoisCarregarDadosAsyncTask();
        }else{
            listenerOnError.onError();
        }
    }

    public interface IAntesCarregarDados {
        void antesCarregarDadosAsyncTask();
    }

    public interface ICarregarDados{
        void carregarDadosAsyncTask() throws Exception;

    }

    public interface IDepoisCarregarDados {
        void depoisCarregarDadosAsyncTask();
    }

    public interface IErrorListener{
        void onError();
    }
}
