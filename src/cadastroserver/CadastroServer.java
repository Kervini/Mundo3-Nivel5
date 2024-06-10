package cadastroserver;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CadastroServer {
    public static void main(String[] args) {
        EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("CadastroServerPU");
        ProdutoJpaController ctrlProd = new ProdutoJpaController(emf);
        UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);
        MovimentoJpaController ctrlMov = new MovimentoJpaController(emf);
        PessoaJpaController ctrlPessoa = new PessoaJpaController(emf);
        
        try {
            ServerSocket serverSocket = new ServerSocket(4321);
            
            while(true){
                Socket clientSocket = serverSocket.accept();
                
                Thread thread = new Thread(new CadastroThreadV2(clientSocket, ctrlProd, ctrlUsu, ctrlMov, ctrlPessoa));
                thread.start();
            }
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
