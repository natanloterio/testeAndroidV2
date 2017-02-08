package br.com.hbsis.testeandroidnatan.impl.view.activity;

import android.os.Bundle;

import br.com.hbsis.testeandroidnatan.R;
import br.com.hbsis.testeandroidnatan.base.BaseActivity;
import br.com.hbsis.testeandroidnatan.impl.presenter.SplashPresenter;

/**
 * Created by natan on 08/02/17.
 */

public class SplashActivity extends BaseActivity<SplashPresenter> {
    @Override
    protected SplashPresenter getPresenterNewsInstance() {
        return new SplashPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    }

    @Override
    public void registerViews() {

    }

    @Override
    public void initializeViews() {

    }

    @Override
    public void atualizarView() {

    }

    @Override
    public void inicializarAdapter() {

    }
}
