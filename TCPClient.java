import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

	private static Socket clientSocket;

	public static void main(String[] args) throws IOException {
		String sentence;
		/*
		 * Cliente insere uma string no buffer para envio ao servidor
		 */
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		/*
		 * define a mesma porta do servidor
		 */
		int port = 4444;
		String servidor = "localhost";
		System.out.println("[*]Conectando ao Servidor "+servidor+":"+port);
		try {
			/*
			 * inicia a conexão com o servidor
			 */
			clientSocket = new Socket(servidor, port);
		}catch (Exception e) {
			System.out.println("Servidor com problemas de Conexão");
			return;
		}
		System.out.println("Digite uma mensagem e pressione Enter: ");
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		/*
		 * aguarda resposta do servidor (string com quebra de linha no final
		 */
		sentence = inFromUser.readLine();
		try {
			outToServer.writeBytes(sentence+'\n');
		} catch (Exception e) {
			System.out.println("[*] Servidor com Problemas");
			return;
		}
		/*
		 * Imprime mensagem recebida do servidor
		 */
		System.out.println(inFromServer.readLine());
		/*
		 * Fecha conexão
		 */
		clientSocket.close();
	}

}