package control;

import control.actions.JanelaSwingControlActions;
import view.JanelaSwing;
import exceptions.BuscaCepException;
import interfaces.BuscaCepEventos;
import interfaces.BuscaCepEventosImpl;

/**
 *
 * @author Will
 */
public class JanelaSwingControl {

    private BuscaCepEventos buscaCepEventos;
    private BuscaCep buscaCep;
    private JanelaSwing janelaPrincipal;
    private JanelaSwingControlActions janelaPrincipalActions;

    public JanelaSwingControl() {
        buscaCep = new BuscaCep();
        buscaCepEventos = new BuscaCepEventosImpl();
        janelaPrincipal = new JanelaSwing();
        janelaPrincipal.setVisible(true);
        janelaPrincipalActions = new JanelaSwingControlActions();
    }

    public void buscarCepAction() {
        buscaCep();
        System.out.println("Rua encontrada: " + buscaCep.getLogradouro());
        popularCampos();
    }

    private boolean buscaCep() {
        try {
            buscaCep.buscar(janelaPrincipal.getTfCep().getText());
            return true;
        } catch (BuscaCepException buscaCepException) {
            return false;
        }
    }

    private void popularCampos() {
        if (!buscaCep()) {
            buscaCepEventos.erroAoEncontrar(janelaPrincipal.getTfCep().getText());
            return;
        }
        buscaCepEventos.sucessoAoEncontrar(buscaCep);
        janelaPrincipal.getTfEstado().setText(buscaCep.getUf());
        janelaPrincipal.getTfCidade().setText(buscaCep.getCidade());
        janelaPrincipal.getTfBairro().setText(buscaCep.getBairro());
        janelaPrincipal.getTfRua().setText(buscaCep.getLogradouro());
        janelaPrincipal.getTfComplemento().setText(buscaCep.getComplemento());
    }

    public void limparCamposAction() {
        janelaPrincipal.getTfEstado().setText(null);
        janelaPrincipal.getTfCidade().setText(null);
        janelaPrincipal.getTfBairro().setText(null);
        janelaPrincipal.getTfRua().setText(null);
        janelaPrincipal.getTfComplemento().setText(null);
    }

}
