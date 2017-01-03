
import java.net.Socket;

public class PatronServer {

	public static void main(String[] args) {

		System.out.println(Constantes.SERVER_HANDSOME);

		final ServerInfo serverInfo = new ServerInfo(Constantes.PORT);
		
		Runtime.getRuntime().addShutdownHook(new Thread(new SigInt_Capturer_Server(serverInfo)));
		
		while (true) {
			try {
				Socket socket = serverInfo.getServer_socket().accept();
				new Thread(new ServerWorker_From_Client(serverInfo, socket)).start();
			} catch (Exception e) {
				System.out.println("Erreur Serveur // Socket non obtenue ");
				return;
			}

		}

	}

}
