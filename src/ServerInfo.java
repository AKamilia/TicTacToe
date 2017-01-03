
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.ArrayList;
import java.net.InetAddress;

public class ServerInfo {
	Hashtable<String, Connexion> Liste_des_connexions;
	Hashtable<String, Connexion> Liste_des_disponibles;
	ServerSocket server_socket;

	public ServerInfo(int PORT) {

		this.Liste_des_connexions = new Hashtable<String, Connexion>();
		this.Liste_des_disponibles = new Hashtable<String, Connexion>();

		try {
			this.server_socket = new ServerSocket(PORT);
			String Adresse_IP = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Informations Perso -----------------------------");
			System.out.println("Adresse IP : " + Adresse_IP);
			System.out.println("Informations Perso -----------------------------");

		} catch (Exception e) {
		}
	}

	// GET ********************************************************
	ServerSocket getServer_socket() {
		return server_socket;
	}

	synchronized Hashtable<String, Connexion> getListe_des_connexions() {
		return Liste_des_connexions;
	}

	synchronized Hashtable<String, Connexion> getListe_des_disponibles() {
		return Liste_des_disponibles;
	}

	synchronized int getNbConnected() {
		return this.getListe_des_connexions().size();

	}

	// MODIF ********************************************************
	synchronized void addConnexion(String key, Connexion c) {
		this.getListe_des_disponibles().put(key, c);
		this.getListe_des_connexions().put(key, c);
	}

	synchronized void removeConnexion(String key) {
		this.getListe_des_disponibles().remove(key);
		this.getListe_des_connexions().remove(key);
	}

	synchronized void addDisponible(String key) {
		this.getListe_des_disponibles().put(key, this.getListe_des_connexions().get(key));
	}

	synchronized void removeDisponible(String key) {
		this.getListe_des_disponibles().remove(key);
	}

	synchronized void removeBothDisponible(String key, String key2) {
		this.getListe_des_disponibles().remove(key);
		this.getListe_des_disponibles().remove(key2);
	}

}
