package br.com.hbsis.testeandroidnatan.base;

import android.content.Context;

import java.util.ArrayList;

import br.com.hbsis.testeandroidnatan.impl.dao.base.DaoFactoryPessoa;

/**
 * Created by natan on 06/02/17.
 */

public abstract class BasePresenter {

    // Flag indicando o estado do Presenter.
    // Depois de ir para background, o Android pode não restaurar o estado dos objetos
    // que não extendem de mainView. Então, devemos retaurar por nossa conta.
    private boolean inicializado = false;

    // Flag que indica se está carregando ou não dados
    private boolean carregandoDados = false;

    // Lista com as views que este presenter controla
    private ArrayList<IViewNotifier> views = new ArrayList<>();

    // Contexto da Activity
    private Context activityContext;
    private boolean viewsLiberadasParaAtualizacao;
    private DaoFactoryPessoa daoFactory;
    private IMainView mainView;
    private boolean deveMostrarProgressDialogAoCarregarDados;

    public BasePresenter(Context activityContext)  {
        this.activityContext = activityContext;
    }

    public void onResume() {

        setMainView((IMainView) activityContext);

        try {
            if (!inicializado) {
                inicializarPresenter();
            }
        } catch (Exception e) {
            e.printStackTrace();
            getMainView().mostrarAlerta("Ocorreu um erro ao atualizar os dados");
        }


    }

    private void inicializarPresenter() throws Exception {
        CarregarDados asyncCarregarDados = new CarregarDados(new CarregarDados.IAntesCarregarDados() {
            @Override
            public void antesCarregarDadosAsyncTask() {
                desabilitarAtualizacaoDeViews();
                antesDeCarregarDados();
            }
        }, new CarregarDados.ICarregarDados() {
            @Override
            public void carregarDadosAsyncTask() throws Exception {
                carrearDados();
            }
        }, new CarregarDados.IDepoisCarregarDados() {
            @Override
            public void depoisCarregarDadosAsyncTask() {
                mainView.initializeViews();
                mainView.registerViews();
                habilitarAtualizacaoDeViews();
                inicializarAdaptersDasViews();
                atualizarViews();
                depoisDeCarregarDados();
            }
        },
                new CarregarDados.IErrorListener() {
                    @Override
                    public void onError() {
                        getMainView().mostrarAlertaFatal("Erro ao carregar os dados principais");
                    }
                });
        asyncCarregarDados.execute();

    }

    protected void inicializarAdaptersDasViews(){
        for(IViewNotifier i:views){
            i.inicializarAdapter();
        }
    }

    public void registerView(IViewNotifier i){
        views.add(i);
    }

    public void unregisterView(IViewNotifier i){
        views.remove(i);
    }

    private void atualizarViews() {
        if(!podeAtualizarViews()){
            return;
        }
        for(IViewNotifier i:views){
            i.atualizarView();
        }
    }

    public Context getActivityContext() {
        return activityContext;
    }

    private boolean podeAtualizarViews() {
        return viewsLiberadasParaAtualizacao;
    }

    private void desabilitarAtualizacaoDeViews(){
        viewsLiberadasParaAtualizacao = false;
    }

    private void habilitarAtualizacaoDeViews(){
        viewsLiberadasParaAtualizacao = true;
    }


    protected DaoFactoryPessoa getDaoFactory(){
        if(daoFactory == null){
            daoFactory = new DaoFactoryPessoa(activityContext);
        }
        return daoFactory;
    }

    /**
     * Evento destinado para buscar dados locais ou remotos. Este método roda em segundo plano.
     * Não executar operações de tela aqui dentro.
     */
    protected abstract void carrearDados() throws Exception;

    /**
     * Evento chamado antes de carregar os dados
     */
    protected void antesDeCarregarDados(){
        if(deveMostrarProgressDialogAoCarregarDados) {
            getMainView().mostrarProgressDialog();
        }
    }

    /**
     * Evento chamado antes de carregar os dados
     */
    protected void depoisDeCarregarDados(){
        getMainView().esconderProgressDialog();
    }

    public IMainView getMainView() {
        return mainView;
    }

    public void setMainView(IMainView mainView) {
        this.mainView = mainView;
    }


    protected void setMostrarProgressDialogAoCarregaDados(boolean b) {
        deveMostrarProgressDialogAoCarregarDados = b;
    }



}
