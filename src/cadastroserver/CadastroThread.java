package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;
import model.Usuario;

public class CadastroThread implements Runnable {
    private final Socket s1;
    private final ProdutoJpaController ctrl;
    private final UsuarioJpaController ctrlUsu;

    public CadastroThread(Socket s1, ProdutoJpaController ctrl,
            UsuarioJpaController ctrlUsu) {
        this.s1 = s1;
        this.ctrl = ctrl;
        this.ctrlUsu = ctrlUsu;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s1.getInputStream()));

            String login = in.readLine();
            String senha = in.readLine();
            Usuario usuario = this.ctrlUsu.getUsuario(login, senha);

            if (usuario == null) {
                s1.close();
                return;
            }

            while (true) {
                String comando = in.readLine().toUpperCase();
                if (comando == null)
                    continue;

                if (comando.equals("L")) {
                    List<Produto> produtos = ctrl.findAll();
                    out.writeObject(produtos);
                }
            }

        } catch (IOException ex) {
            System.out.println("Erro de conexão: " + ex.getMessage());
        }
    }
}
