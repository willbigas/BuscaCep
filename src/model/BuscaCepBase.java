package model;

import exceptions.BuscaCepException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import interfaces.BuscaCepEventos;

public abstract class BuscaCepBase {
    protected List<Cep> listCeps;
    protected int index;
    protected String cepAtual;
    
    // váriaveis internas
    protected BuscaCepEventos eventos;
    
    public BuscaCepBase () {
        listCeps = new ArrayList<>();
        index = -1;
        cepAtual = "00000-000";
        this.eventos = null;
    }
    
    // métodos abstratos
    public abstract void buscar(String cep) throws BuscaCepException;
    public abstract void buscarCEP(Cep cep) throws BuscaCepException;
    
    /**
     * Busca um Cep usando um endereço
     * @param uf estado
     * @param localidade cidade
     * @param logradouro nome ou parte do nome da rua, av, viela...
     * @throws exceptions.BuscaCepException
     */
    public void buscarCEP(String uf, String localidade, String logradouro) throws BuscaCepException {
        buscarCEP(new Cep(logradouro, localidade, uf));
    }
    
    /**
     * Retona o index atual;
     * @return 
     */
    public int pegaIndex() {
        return index;
    }
    
    /**
     * Retorna o total de Cep's
     * @return 
     */
    public int getSize() {
        return listCeps.size();
    }
    
    /**
     * Retonar o Cep
     *
     * @return
     */
    public String getCep() {
        return listCeps.get(index).cep;
    }

    /**
     * Retorna o nome da rua, avenida, travessa, ...
     *
     * @return
     */
    public String getLogradouro() {
        return listCeps.get(index).logradouro;
    }

    /**
     * Retorna se tem algum complemento Ex: lado impar
     *
     * @return
     */
    public String getComplemento() {
        return listCeps.get(index).complemento;
    }

    /**
     * Retorna o bairro
     *
     * @return
     */
    public String getBairro() {
        return listCeps.get(index).bairro;
    }

    /**
     * Retorna a cidade
     *
     * @return
     */
    public String getCidade() {
        return listCeps.get(index).cidade;
    }

    /**
     * Retorna o UF
     *
     * @return
     */
    public String getUf() {
        return listCeps.get(index).uf;
    }

    /**
     * Retorna o ibge
     *
     * @return
     */
    public String getIbge() {
        return listCeps.get(index).ibge;
    }

    /**
     * Retorna a gia
     *
     * @return
     */
    public String getGia() {
        return listCeps.get(index).gia;
    }
    
    /**
     * Procedimento para obter dados via GET
     *
     * @param urlToRead endereço
     * @return conteúdo remoto
     * @throws exceptions.BuscaCepException caso ocorra algum erro
     */
    public final String getHttpGET(String urlToRead) throws BuscaCepException {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            
        } catch (MalformedURLException | ProtocolException ex) {
            // verifica os Eventos
            if (eventos instanceof BuscaCepEventos) {
                eventos.erroAoEncontrar(cepAtual);
            }
            
            throw new BuscaCepException(ex.getMessage(), ex.getClass().getName());
        } catch (IOException ex) {
            // verifica os Eventos
            if (eventos instanceof BuscaCepEventos) {
                eventos.erroAoEncontrar(cepAtual);
            }
            
            throw new BuscaCepException(ex.getMessage(), ex.getClass().getName());
        }
        
        return result.toString();
    }
    
    /**
     * Move para um registro específico
     * @param index
     * @return 
     */
    public boolean move(int index) {
        if (listCeps.size() > 0 && index >= 0 && index < listCeps.size()) {
            this.index = index;
            return true;
        }
        
        this.index = -1;
        return false;
    }
    
    /**
     * Move para o primeiro registro
     * @return 
     */
    public boolean moveFirst() {
        if (listCeps.size() > 0) {
            index = 0;
            return true;
        }
        
        index = -1;
        return false;
    }
    
    /**
     * Move para o próximo registro
     * @return 
     */
    public boolean moveNext() {
        if (listCeps.size() > 0 && (index + 1) < listCeps.size()) {
            index += 1;
            return true;
        }
        
        index = -1;
        return false;
    }
    
    /**
     * Move para o registro anterior
     * @return 
     */
    public boolean movePrevious() {
        if (listCeps.size() > 0 && (index - 1) >= 0) {
            index -= 1;
            return true;
        }
        
        index = -1;
        return false;
    }
    
    /**
     * Move para o último registro
     * @return 
     */
    public boolean moveLast() {
        if (listCeps.size() > 0) {
            index = listCeps.size() - 1;
            return true;
        }
        
        index = -1;
        return false;
    }
    
    /**
     * Retorna a lista de Cep's
     * @return 
     */
    public List<Cep> getList() {
        return listCeps;
    }
    
    /**
     * Procedimento para formatar uma string para usar em urls
     * @param string texto que vai ser formatado
     * @return texto formatado
     * @throws BuscaCepException em caso de erro
     */
    protected String formatStringToUri(String string) throws BuscaCepException {
        String out = null;
        
        // verifica está válido
        if (string != null && !string.isEmpty()) {
            try {
                out = URLEncoder.encode(string, "utf-8");
                out = out.replace("+", "%20"); // força espaço como %20
            } catch (UnsupportedEncodingException e) {
                throw new BuscaCepException("Não foi possível codificar o valor solicitado!", UnsupportedEncodingException.class.getName());
            }
        } else {
            throw new BuscaCepException("Valor nulo ou vazio informado!", String.class.getName());
        }
        
        return out;
    }
}
