package buscacep;

import control.BuscaCep;
import exceptions.BuscaCepException;
import interfaces.BuscaCepEvents;
import interfaces.BuscaCepEventsImpl;

/**
 *
 * @author Will
 */
public class Principal {

    public static void main(String[] args) {
        
        BuscaCepEvents buscaCepEvents = new BuscaCepEventsImpl();
        
        /**
         * Buscador de CEP - Main Teste.
         */
        BuscaCep buscaCEP = new BuscaCep();
        try {

            buscaCEP.buscar("88133810");
            System.out.println("Bairro: "  + buscaCEP.getBairro());
            System.out.println("Logradouro: " +buscaCEP.getLogradouro());
            buscaCepEvents.sucessoAoEncontrar(buscaCEP);
        } catch (BuscaCepException viaCEPException) {
            System.out.println("Deu erro");
        }
    }
}
