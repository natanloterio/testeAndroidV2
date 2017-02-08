package br.com.hbsis.testeandroidnatan.impl.view.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import br.com.hbsis.testeandroidnatan.R;
import br.com.hbsis.testeandroidnatan.impl.dao.base.Pessoa;

/**
 * Created by natan on 07/02/17.
 */

public class PessoaAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private final List<Pessoa> pessoas;
    private final Context context;
    private IPessoaAdapter listener;

    public PessoaAdapter(Context ctx, List<Pessoa> pessoas, IPessoaAdapter listener) {
        this.context = ctx;
        this.pessoas = pessoas;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return pessoas.size();
    }

    @Override
    public Object getItem(int position) {
        return pessoas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        }
        java.text.DateFormat df = DateFormat.getDateFormat(context);
        Pessoa p = pessoas.get(position);
        CheckBox chkAtivo = ((CheckBox) convertView.findViewById(R.id.list_item__chk_ativo));
        chkAtivo.setTag(p.getId());
        chkAtivo.setOnCheckedChangeListener(this);

        chkAtivo.setChecked(p.isAtivo());
        ((TextView)convertView.findViewById(R.id.list_item__nome)).setText(p.getNome());
        ((TextView)convertView.findViewById(R.id.list_item__sobrenome)).setText(p.getSobrenome());
        ((TextView)convertView.findViewById(R.id.list_item__data_nascimento)).setText(df.format(p.getDataNascimento()));
        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(listener!=null){
            listener.onClickCheckAtivo((Long) buttonView.getTag(),isChecked);
        }
    }

    public interface IPessoaAdapter{

        void onClickCheckAtivo(Long id, boolean isChecked);
    }
}
