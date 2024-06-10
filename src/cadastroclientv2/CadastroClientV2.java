package cadastroclientv2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CadastroClientV2 {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader entrada = new BufferedReader(reader);

            System.out.println("Conectado...");

            out.writeObject("op1");
            out.writeObject("op1");

            SaidaFrame janela = new SaidaFrame();

            Thread thread = new Thread(new ThreadClient(in, janela, socket));
            thread.start();
            String opcao;

            do {
                System.out.println("\n----------------------");
                System.out.println("L - Listar | X - Finalizar | E - Entrada | S - Saida");
                opcao = entrada.readLine().toUpperCase();

                switch (opcao) {
                    case "L" -> {
                        out.writeObject(opcao);
                    }
                    case "X" -> {
                        System.out.println("Finalizando...");
                    }
                    case "E" -> {
                        out.writeObject(opcao);
                        System.out.println("Digite o ID da pessoa: ");
                        out.writeObject(entrada.readLine());
                        System.out.println("Digite o ID do produto: ");
                        out.writeObject(entrada.readLine());
                        System.out.println("Digite a quantidade: ");
                        out.writeObject(entrada.readLine());
                        System.out.println("Digite o valor unitario: ");
                        out.writeObject(entrada.readLine());
                    }
                    case "S" -> {
                        out.writeObject(opcao);
                        System.out.println("Digite o ID da pessoa: ");
                        out.writeObject(entrada.readLine());
                        System.out.println("Digite o ID do produto: ");
                        out.writeObject(entrada.readLine());
                        System.out.println("Digite a quantidade: ");
                        out.writeObject(entrada.readLine());
                    }
                    default -> {
                        System.out.println("Opcao invalida!");
                    }
                }
            } while (!opcao.equals("X"));
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
