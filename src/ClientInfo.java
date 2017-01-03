import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientInfo {

	// Sockets -------------------------------
	ServerSocket serverSocket;
	Socket socket_to_server;
	Socket socket_to_client;

	boolean est_initiateur = false;
	boolean est_occupe = false;
	boolean veut_rejouer = false;
	boolean une_demande_en_attente = false;
	boolean liste_client_dirty = false ;
	
	// ----------------------------------------

	// Information Serveur --------------------
	String adresse_IP_du_serveur;

	// Information Locale ---------------------
	int port_de_la_ServerSocket_locale;
	String adresse_IP_du_client_local;

	// Information sur l'adversaire -----------
	int port_de_la_ServerSocket_distant;
	String adresse_IP_du_client_distant;

	// Donnees --------------------------------
	ArrayList<String> Liste_des_connexions;

	// Connexions -----------------------------
	Connexion connexion_to_server;
	Connexion connexion_to_client;
	


	public ClientInfo(String Adresse_IP_du_serveur) throws Exception {
		// Sockets ---------------------------------
		
		this.socket_to_server = new Socket(Adresse_IP_du_serveur, Constantes.PORT);

		try {
			this.serverSocket = new ServerSocket(0, 1);
		} catch (Exception e) {
			System.out.println("[ClientInfo] ServerSocket non cree");
		}

		this.socket_to_client = null;

		// Information Serveur ---------------------
		this.adresse_IP_du_serveur = Adresse_IP_du_serveur;
		this.port_de_la_ServerSocket_locale = serverSocket.getLocalPort();

		// Information Locale ----------------------
		this.adresse_IP_du_client_local = InetAddress.getLocalHost().getHostAddress();

		// Information sur l'adversaire ------------
		this.port_de_la_ServerSocket_distant = 0;
		this.adresse_IP_du_client_distant = null;

		// Donnees --------------------------------
		this.Liste_des_connexions = new ArrayList<String>();

		// Connexions -----------------------------
		this.connexion_to_server = new Connexion(socket_to_server);
		this.connexion_to_client = null;
		 
	}

}
