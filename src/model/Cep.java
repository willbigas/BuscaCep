package model;

public class Cep {

    public String cep;
    public String logradouro;
    public String complemento;
    public String bairro;
    public String cidade;
    public String uf;
    public String ibge;
    public String gia;

    /**
     * Cria um novo cep vazio
     */
    public Cep() {
        this.logradouro = null;
        this.complemento = null;
        this.bairro = null;
        this.cidade = null;
        this.uf = null;
        this.ibge = null;
        this.gia = null;
    }

    /**
     * Cria um novo cep completo
     *
     * @param cep
     * @param logradouro
     * @param complemento
     * @param bairro
     * @param localidade
     * @param uf
     * @param ibge
     * @param gia
     */
    public Cep(String cep, String logradouro, String complemento, String bairro, String localidade, String uf, String ibge, String gia) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = localidade;
        this.uf = uf;
        this.ibge = ibge;
        this.gia = gia;
    }

    /**
     * Cria um novo cep parcial
     *
     * @param logradouro
     * @param localidade
     * @param uf
     */
    public Cep(String logradouro, String localidade, String uf) {
        this.logradouro = logradouro;
        this.cidade = localidade;
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Cep : " + this.cep;
    }

}
