package br.com.hbsis.testeandroidnatan.impl.dao.base;

import br.com.hbsis.testeandroidnatan.base.IDao;

/**
 * Created by natan on 07/02/17.
 */

public abstract class BaseDaoFactory<Clazz> {
    public enum DaoFactoryType{REMOTE,LOCAL};
    private IDao daoLocal;
    private IDao daoRemoto;

    public IDao<Clazz> getInstance(DaoFactoryType t){
        switch (t){
            case REMOTE: return getRemoteInstance();
            case LOCAL: return getLocalInstance();
            default:return null;
        }
    }

    protected IDao<Clazz> getLocalInstance() {
        if(daoLocal == null){
            daoLocal = getNewLocalInstance();
        }
        return daoLocal;
    }

    protected IDao<Clazz> getRemoteInstance(){
        if(daoRemoto == null){
            daoRemoto = getNewRemotolInstance();
        }
        return daoRemoto;
    }

    protected abstract IDao<Clazz> getNewRemotolInstance();

    protected abstract IDao<Clazz> getNewLocalInstance();


}
