package cadastroclientv2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import model.Produto;

public class ThreadClient implements Runnable {
    private ObjectInputStream entrada;
    //private JTextArea textArea;
    private SaidaFrame janela;
    private Socket socket;

    public ThreadClient(ObjectInputStream entrada, SaidaFrame janela, Socket socket) {
        this.entrada = entrada;
        this.janela = janela;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String texto = "\n";
                Object resposta = entrada.readObject();

                if (resposta instanceof Collection) {
                    List<Produto> produtos = (List<Produto>) resposta;
                    texto += "\nLista de Produtos";
                    for (Produto p : produtos) {
                        texto += "\nProduto: " + p.getNome() + "; Quantidade: " + p.getQuantidade();
                    }
                } else {
                    texto += "\n" + resposta.toString();
                }

                final String msg = texto;
                SwingUtilities.invokeLater(() -> {
                    janela.texto.append(msg);
                });
            } catch (IOException ex) {
                Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
