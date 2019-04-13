package interfaces;

import control.BuscaCep;
import javax.swing.JOptionPane;

/**
 *
 * @author Will
 */
public class BuscaCepEventsImpl implements BuscaCepEvents {

    @Override
    public void sucessoAoEncontrar(BuscaCep cep) {
        JOptionPane.showMessageDialog(null, "Cep " + cep + "Nao Encontrado!");
    }

    @Override
    public void erroAoEncontrar(String cep) {
        JOptionPane.showMessageDialog(null, "Cep " + cep + "Encontrado!");

    }

}
