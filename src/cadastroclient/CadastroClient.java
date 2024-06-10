package cadastroclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import model.Produto;

public class CadastroClient {
    public static void main(String[] args) {
        try {
            Scanner entrada = new Scanner(System.in);
            Socket socket = new Socket("localhost", 4321);
            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            System.out.println("Digite o login: ");
            out.println(entrada.nextLine());
            System.out.println("Digite a senha: ");
            out.println(entrada.nextLine());
            System.out.println("Faz o L: ");
            out.println(entrada.nextLine());
            
            ObjectInputStream in = 
                    new ObjectInputStream(socket.getInputStream());
            List<Produto> produtos = (List<Produto>)in.readObject();
            
            for(Produto p : produtos){
                System.out.println(p.getNome());
            }
            
            in.close();
            out.close();
            socket.close();
            
        } catch (IOException ex) {
            System.out.println("Erro de conexão com o servidor: "
                    + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
