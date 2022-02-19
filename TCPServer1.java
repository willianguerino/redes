import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer1 {
	
	

	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception{
		ServerSocket welcomeSocket;
		String clientSentence ="";
		String capitalizedSentence;
		
		/*
		 * definição da porta
		 */
		
		int port = 4444;
		
		/*
		 * atribuindo a porta ao socket
		 */
		welcomeSocket = new ServerSocket(port);
		

		System.out.println("[*]Esperando conexões na porta "+port);
		
		/*
		 * laço infinito para o Socket aguardar a conexão
		 */
		
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("[*]Conexão Aceita...");
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			try {
				/*
				 * Servidor fica aquardando o caracter quebra linha '\n'
				 * */
				clientSentence = inFromClient.readLine();
				/*
				 * conversão da string para maiuscula
				 */
				capitalizedSentence = clientSentence.toUpperCase();
				/*
				 * retorna a string maiuscula concatenada com a quebra de linha para identificar o final da mensagen
				 */
				outToClient.writeBytes(capitalizedSentence+'\n');
			}catch (Exception e) {
				System.out.println("[*] PERDA DA CONEXÃO");
			}
			/*
			 * fecha a conexão aberta
			 */
			connectionSocket.close();
			System.out.println("Conexão Finalizada");
		}
	}

}