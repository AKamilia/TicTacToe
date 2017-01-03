import java.io.IOException;
import java.net.SocketException;

public class WorkerClient_From_Client implements Runnable{
	
	
	ClientInfo clientInfo;

	public WorkerClient_From_Client(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	@Override
	public void run() {
		while (true) {
		
				try {
					clientInfo.socket_to_client = clientInfo.serverSocket.accept();
				} catch (Exception e ) {
				
				}
			
				System.out.println("Vous avez une demande de connexion");
				clientInfo.une_demande_en_attente = true;
			
		}
	}

}
