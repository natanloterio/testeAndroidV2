package br.com.hbsis.testeandroidnatan.base;

import java.util.List;

/**
 * Created by natan on 07/02/17.
 */

public interface IDao<Clazz> {
    public List<Clazz> getAll() throws Exception;
}
