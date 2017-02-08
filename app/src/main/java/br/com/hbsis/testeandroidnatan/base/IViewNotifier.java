package br.com.hbsis.testeandroidnatan.base;

/**
 * Interface que representa uma view. Pode ser uma activity, fragment ou outra view
 * Created by natan on 06/02/17.
 */

public interface IViewNotifier {

    /**
     * Evento disparado quando há a encessidade de Atualizar as views
     */
    void atualizarView();

    void inicializarAdapter();
}
