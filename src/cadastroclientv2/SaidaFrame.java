package cadastroclientv2;

import javax.swing.JDialog;
import javax.swing.JTextArea;

public class SaidaFrame extends JDialog{
    public JTextArea texto;
    
    public SaidaFrame(){
        this.setBounds(100, 100, 400, 400);
        this.setModal(false);
        texto = new JTextArea();
        texto.setText("Usuario conectado!");
        texto.setEditable(false);
        this.add(texto);
        this.setVisible(true);
    }
}



