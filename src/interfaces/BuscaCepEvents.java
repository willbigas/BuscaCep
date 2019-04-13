package interfaces;

import control.BuscaCep;

public interface BuscaCepEvents {
    /**
     * Quando o CEP for encontrado com sucesso
     * @param cep retorna o objeto BuscaCep
     */
    public void sucessoAoEncontrar(BuscaCep cep);
    
    /**
     * Quando ocorrer qualquer erro ao encontrar o CEP
     * @param cep retorna o CEP que foi requisitado
     */
    public void erroAoEncontrar(String cep);
}
