package control.actions;

import control.JanelaSwingControl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import view.JanelaSwing;

/**
 *
 * @author Will
 */
public class JanelaSwingControlActions {
    
    JanelaSwing janelaPrincipal;
    JanelaSwingControl janelaPrincipalControl;

    public JanelaSwingControlActions() {
        janelaPrincipal = new JanelaSwing();
        janelaPrincipal.getBtBuscar().addActionListener(acaoBotaoBuscar());
        
    }

    private ActionListener acaoBotaoBuscar(){
         return new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 System.out.println("Chegou Aqui!");
                 janelaPrincipalControl.buscarCepAction();
               
             }
             
         };
        
    }
    

}
