package br.com.hbsis.testeandroidnatan.impl.dao.base;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.hbsis.testeandroidnatan.base.IDao;

/**
 * Created by natan on 07/02/17.
 */

public class DaoFactoryPessoa extends BaseDaoFactory<Pessoa> {
    private final Context context;

    public DaoFactoryPessoa(Context c){
        context = c;
    }
    @Override
    protected IDao<Pessoa> getNewRemotolInstance() {
        return new DaoPessoaRemoto();
    }

    @Override
    protected IDao<Pessoa> getNewLocalInstance() {
        return new DaoPessoaLocal(context);
    }

    public void updateData() throws Exception {
        List<Pessoa> pessoasRemoto = getRemoteInstance().getAll();
        for(Pessoa p:pessoasRemoto){
            ((DaoPessoaLocal)getLocalInstance()).insert(p);
        }
        Log.w("DaoFactoryPessoa","after updateData()");
    }
}
