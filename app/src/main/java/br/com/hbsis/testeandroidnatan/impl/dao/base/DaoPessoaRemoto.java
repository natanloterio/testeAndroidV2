package br.com.hbsis.testeandroidnatan.impl.dao.base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.hbsis.testeandroidnatan.base.IDao;
import br.com.hbsis.testeandroidnatan.util.Connector;

/**
 * Created by natan on 07/02/17.
 */

public class DaoPessoaRemoto implements IDao<Pessoa> {
    private String urlJSON="https://testetecnico.herokuapp.com/pessoas";

    @Override
    public List<Pessoa> getAll() throws Exception {
        String jsonData = getJSON();
        if(jsonData.startsWith("Error"))
        {
            String error=jsonData;
            throw new Exception("Erro baixando os dados do servidor");
        }else
        {
            try {
                return parseJSON(jsonData);
            }catch (Exception e){
                e.printStackTrace();
                throw new Exception("Erro convertendo os dados do servidor");
            }


        }

    }

    private List<Pessoa> parseJSON(String jsonData) throws JSONException {
        List<Pessoa> lista = new ArrayList<>();
        JSONArray array = new JSONArray(jsonData);
        for(int i =0;i<array.length();i++){
            JSONObject obj = array.getJSONObject(i);
            String nome = obj.getString("nome");
            String sobrenome = obj.getString("sobrenome");
            int dat= obj.getInt("dataNascimento");
            Date dataNascimento = new Date(dat);

            Pessoa p = new Pessoa();
            p.setNome(nome);
            p.setSobrenome(sobrenome);
            p.setDataNascimento(dataNascimento);
            lista.add(p);
        }
        return lista;
    }

    private String getJSON(){
        Object connection= Connector.connect(urlJSON);
        if(connection.toString().startsWith("Error"))
        {
            return connection.toString();
        }

        try
        {
            HttpURLConnection con= (HttpURLConnection) connection;
            if(con.getResponseCode()==con.HTTP_OK)
            {
                //GET INPUT FROM STREAM
                InputStream is=new BufferedInputStream(con.getInputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(is));

                String line;
                StringBuffer jsonData=new StringBuffer();

                //READ
                while ((line=br.readLine()) != null)
                {
                    jsonData.append(line+"n");
                }

                //CLOSE RESOURCES
                br.close();
                is.close();

                //RETURN JSON
                return jsonData.toString();

            }else
            {
                return "Error "+con.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error "+e.getMessage();

        }
    }
}
