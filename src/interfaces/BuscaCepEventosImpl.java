package interfaces;

import control.BuscaCep;
import javax.swing.JOptionPane;

/**
 *
 * @author Will
 */
public class BuscaCepEventosImpl implements BuscaCepEventos {

    @Override
    public void sucessoAoEncontrar(BuscaCep buscaCep) {
        JOptionPane.showMessageDialog(null, "Cep " + buscaCep.getCep() + " Encontrado");
    }

    @Override
    public void erroAoEncontrar(String cep) {
        JOptionPane.showMessageDialog(null, "Cep " + cep + "Nao Encontrado!");

    }

}
