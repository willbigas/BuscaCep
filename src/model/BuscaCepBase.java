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
import interfaces.BuscaCepEvents;

public abstract class BuscaCepBase {
    protected List<Cep> CEPs;
    protected int index;
    protected String currentCEP;
    
    // váriaveis internas
    protected BuscaCepEvents Events;
    
    public BuscaCepBase () {
        CEPs = new ArrayList<>();
        index = -1;
        currentCEP = "00000-000";
        this.Events = null;
    }
    
    // métodos abstratos
    public abstract void buscar(String cep) throws BuscaCepException;
    public abstract void buscarCEP(Cep cep) throws BuscaCepException;
    
    /**
     * Busca um Cep usando um endereço
     * @param Uf estado
     * @param Localidade cidade
     * @param Logradouro nome ou parte do nome da rua, av, viela...
     * @throws exceptions.BuscaCepException
     */
    public void buscarCEP(String Uf, String Localidade, String Logradouro) throws BuscaCepException {
        buscarCEP(new Cep(Logradouro, Localidade, Uf));
    }
    
    /**
     * Retona o index atual;
     * @return 
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * Retorna o total de Cep's
     * @return 
     */
    public int getSize() {
        return CEPs.size();
    }
    
    /**
     * Retonar o Cep
     *
     * @return
     */
    public String getCep() {
        return CEPs.get(index).CEP;
    }

    /**
     * Retorna o nome da rua, avenida, travessa, ...
     *
     * @return
     */
    public String getLogradouro() {
        return CEPs.get(index).Logradouro;
    }

    /**
     * Retorna se tem algum complemento Ex: lado impar
     *
     * @return
     */
    public String getComplemento() {
        return CEPs.get(index).Complemento;
    }

    /**
     * Retorna o Bairro
     *
     * @return
     */
    public String getBairro() {
        return CEPs.get(index).Bairro;
    }

    /**
     * Retorna a Cidade
     *
     * @return
     */
    public String getLocalidade() {
        return CEPs.get(index).Localidade;
    }

    /**
     * Retorna o UF
     *
     * @return
     */
    public String getUf() {
        return CEPs.get(index).Uf;
    }

    /**
     * Retorna o Ibge
     *
     * @return
     */
    public String getIbge() {
        return CEPs.get(index).Ibge;
    }

    /**
     * Retorna a Gia
     *
     * @return
     */
    public String getGia() {
        return CEPs.get(index).Gia;
    }
    
    /**
     * Procedimento para obtem dados via GET
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
            if (Events instanceof BuscaCepEvents) {
                Events.erroAoEncontrar(currentCEP);
            }
            
            throw new BuscaCepException(ex.getMessage(), ex.getClass().getName());
        } catch (IOException ex) {
            // verifica os Eventos
            if (Events instanceof BuscaCepEvents) {
                Events.erroAoEncontrar(currentCEP);
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
        if (CEPs.size() > 0 && index >= 0 && index < CEPs.size()) {
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
        if (CEPs.size() > 0) {
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
        if (CEPs.size() > 0 && (index + 1) < CEPs.size()) {
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
        if (CEPs.size() > 0 && (index - 1) >= 0) {
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
        if (CEPs.size() > 0) {
            index = CEPs.size() - 1;
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
        return CEPs;
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
