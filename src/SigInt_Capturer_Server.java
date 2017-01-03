import java.io.IOException;

public class SigInt_Capturer_Server implements Runnable {

	
	
	ServerInfo serverInfo;

	public SigInt_Capturer_Server(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	
	@Override
	public void run() {		
		try {
			this.serverInfo.server_socket.close();
		} catch (Exception e) {
	 
		}
	}

}
