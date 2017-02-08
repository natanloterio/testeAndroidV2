package br.com.hbsis.testeandroidnatan.impl.presenter;

import android.content.Context;
import android.content.Intent;

import br.com.hbsis.testeandroidnatan.base.BasePresenter;
import br.com.hbsis.testeandroidnatan.impl.dao.base.BaseDaoFactory;
import br.com.hbsis.testeandroidnatan.impl.view.activity.MainActivity;
import br.com.hbsis.testeandroidnatan.util.NetworkUtil;

/**
 * Created by natan on 08/02/17.
 */

public class SplashPresenter extends BasePresenter {
    public SplashPresenter(Context activityContext) {
        super(activityContext);
        setMostrarProgressDialogAoCarregaDados(false);
    }

    @Override
    protected void carrearDados() throws Exception {

        if(deveBaixarNovaCargaDeDados()){
            getDaoFactory().updateData();
        }

    }

    @Override
    protected void depoisDeCarregarDados() {
        openMainActivity();
    }

    private void openMainActivity() {
        Intent intent = new Intent(getActivityContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivityContext().startActivity(intent);

    }


    private boolean deveBaixarNovaCargaDeDados() throws Exception {
        return bancoVazio() && isNetworkAvailable();
    }

    private boolean isNetworkAvailable() {
        return NetworkUtil.isNetworkAvailable(getActivityContext());
    }

    private boolean bancoVazio() throws Exception {
        int count = getDaoFactory().getInstance(BaseDaoFactory.DaoFactoryType.LOCAL).getAll().size();
        return count<=0;
    }


}
