package control;

import exceptions.BuscaCepException;
import model.BuscaCepBase;
import model.Cep;
import org.json.JSONArray;
import org.json.JSONObject;
import interfaces.BuscaCepEvents;

/**
 * Classe java para obter um Cep no BuscaCep
 *
 */
public class BuscaCep extends BuscaCepBase {

    // constantes
    public static final double VIACEP_VERSAO = 0.33;

    /**
     * Constrói uma nova classe
     */
    public BuscaCep() {
        super();
    }

    /**
     * Constrói uma nova classe
     *
     * @param events eventos para a classe
     */
    public BuscaCep(BuscaCepEvents events) {
        super();
        this.Events = events;
    }

    /**
     * Constrói uma nova classe e busca um Cep no ViaCEP
     *
     * @param events eventos para a classe
     * @param cep
     * @throws buscacep.ViaCEPException caso ocorra algum erro
     */
    public BuscaCep(String cep, BuscaCepEvents events) throws BuscaCepException {
        super();
        this.Events = events;
        this.buscar(cep);
    }

    /**
     * Constrói uma nova classe e busca um Cep no ViaCEP
     *
     * @param cep
     * @throws buscacep.ViaCEPException caso ocorra algum erro
     */
    public BuscaCep(String cep) throws BuscaCepException {
        super();
        this.buscar(cep);
    }

    /**
     * Busca um Cep no BuscaCep
     *
     * @param cep
     * @throws buscacep.ViaCEPException caso ocorra algum erro
     */
    @Override
    public final void buscar(String cep) throws BuscaCepException {
        // define o cep atual
        currentCEP = cep;

        // define a url
        String url = "http://viacep.com.br/ws/" + cep + "/json/";

        // define os dados
        JSONObject obj = new JSONObject(getHttpGET(url));

        if (!obj.has("erro")) {
            Cep novoCEP = new Cep(obj.getString("cep"),
                    obj.getString("logradouro"),
                    obj.getString("complemento"),
                    obj.getString("bairro"),
                    obj.getString("localidade"),
                    obj.getString("uf"),
                    obj.getString("ibge"),
                    obj.getString("gia"));

            // insere o novo Cep
            CEPs.add(novoCEP);

            // atualiza o index
            index = CEPs.size() - 1;

            // verifica os Eventos
            if (Events instanceof BuscaCepEvents) {
                Events.sucessoAoEncontrar(this);
            }
        } else {
            // verifica os Eventos
            if (Events instanceof BuscaCepEvents) {
                Events.erroAoEncontrar(currentCEP);
            }

            throw new BuscaCepException("Não foi possível encontrar o CEP", cep, BuscaCepException.class.getName());
        }
    }

    /**
     * Busca um Cep usando um endereço
     *
     * @param cep classe Cep com uf, localidade, logradouro
     * @throws BuscaCepException
     */
    @Override
    public void buscarCEP(Cep cep) throws BuscaCepException {
        buscarCEP(cep.Uf, cep.Localidade, cep.Logradouro);
    }

    /**
     * Busca um Cep usando um endereço
     *
     * @param Uf Estado
     * @param Localidade Municipio
     * @param Logradouro Rua, Avenidade, Viela...
     * @throws BuscaCepException
     */
    @Override
    public void buscarCEP(String Uf, String Localidade, String Logradouro) throws BuscaCepException {
        // define o cep atual
        currentCEP = "?????-???";

        // define a url
        String url = "http://viacep.com.br/ws/" + Uf.toUpperCase() + "/" + Localidade + "/" + Logradouro + "/json/";

        // obtem a lista de Cep's
        JSONArray ceps = new JSONArray(getHttpGET(url));

        if (ceps.length() > 0) {
            for (int i = 0; i < ceps.length(); i++) {
                JSONObject obj = ceps.getJSONObject(i);

                if (!obj.has("erro")) {
                    Cep novoCEP = new Cep(obj.getString("cep"),
                            obj.getString("logradouro"),
                            obj.getString("complemento"),
                            obj.getString("bairro"),
                            obj.getString("localidade"),
                            obj.getString("uf"),
                            obj.getString("ibge"),
                            obj.getString("gia"));

                    // insere o novo Cep
                    CEPs.add(novoCEP);

                    // atualiza o index
                    index = CEPs.size() - 1;

                    // verifica os Eventos
                    if (Events instanceof BuscaCepEvents) {
                        Events.sucessoAoEncontrar(this);
                    }
                } else {
                    // verifica os Eventos
                    if (Events instanceof BuscaCepEvents) {
                        Events.erroAoEncontrar(currentCEP);
                    }

                    throw new BuscaCepException("Não foi possível validar o CEP", currentCEP, BuscaCepException.class.getName());
                }
            }
        } else {
            throw new BuscaCepException("Nenhum CEP encontrado", currentCEP, getClass().getName());
        }
    }
}
