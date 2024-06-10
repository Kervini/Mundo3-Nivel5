package cadastroserver;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movimento;
import model.Produto;
import model.Usuario;

public class CadastroThreadV2 implements Runnable{
    
    private final Socket s1;
    private final ProdutoJpaController ctrlProd;
    private final UsuarioJpaController ctrlUsu;
    private final MovimentoJpaController ctrlMov;
    private final PessoaJpaController ctrlPessoa;

    public CadastroThreadV2(Socket s1, ProdutoJpaController ctrlProd,
            UsuarioJpaController ctrlUsu, MovimentoJpaController ctrlMov,
            PessoaJpaController ctrlPessoa) {
        this.s1 = s1;
        this.ctrlProd = ctrlProd;
        this.ctrlUsu = ctrlUsu;
        this.ctrlMov = ctrlMov;
        this.ctrlPessoa = ctrlPessoa;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
            Scanner entrada = new Scanner(System.in);

            String login = in.readObject().toString();
            String senha = in.readObject().toString();

            Usuario usuario = this.ctrlUsu.getUsuario(login, senha);

            if (usuario == null) {
                s1.close();
                return;
            }
            
            System.out.println("Acesso permitido!");
            
            while (true) {
                String comando = in.readObject().toString().toUpperCase();
                if (comando == null)
                    continue;

                if (comando.equals("L")) {
                    List<Produto> produtos = ctrlProd.findAll();
                    out.writeObject(produtos);
                }else if(comando.equals("S") || comando.equals("E")){
                    Movimento movimento = new Movimento();
                    movimento.setIdusuario(usuario);
                    movimento.setTipoMovimento(comando.toLowerCase().charAt(0));
                    
                    String idPessoa = in.readObject().toString();
                    movimento.setIdpessoa(ctrlPessoa.findById(Integer.parseInt(idPessoa)));
                    
                    String idProduto = in.readObject().toString();
                    Produto produto = ctrlProd.findById(Integer.parseInt(idProduto));
                    movimento.setIdproduto(produto);
                    
                    movimento.setQuantidade(Integer.parseInt(in.readObject().toString()));
                    
                    if(comando.equals("E"))
                        movimento.setValorUnitario(Float.parseFloat(in.readObject().toString()));
                    else
                        movimento.setValorUnitario(produto.getPrecoVenda());
                    
                    ctrlMov.persist(movimento);
                    
                    produto.atualizaQuantidade(movimento.getQuantidade(), comando.equals("E"));
                    ctrlProd.merge(produto);
                    
                    out.writeObject("Movimento realizado!");
                }
            }

        } catch (IOException ex) {
            System.out.println("Erro de conexão: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CadastroThreadV2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
