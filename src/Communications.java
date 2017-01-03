
import java.io.BufferedReader;
import java.util.Enumeration;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;

/*
 * Differentes fonctions utilises pour les communications 
 */
public class Communications {

	// FONCTION D'ENVOI DE MESSAGE UNIQUE
	// -----------------------------------------------------
	static void sendMessageUnique(String s, PrintWriter pw) {
		try {pw.println(s);
		}catch (Exception e) {
				System.exit(-1);
		}
	}

	// FONCTION D'ENVOI DE MESSAGE GROUPE
	// -----------------------------------------------------
	static void sendMessageGroupe(String s, Enumeration<Connexion> ensemble_de_Connexion) {
		while (ensemble_de_Connexion.hasMoreElements()) {
			Communications.sendMessageUnique(s, ensemble_de_Connexion.nextElement().out);
		}
	}

	static void sendMultipleMessageToOne(String message, Enumeration<String> ensemble_de_String, Connexion c) {
		while (ensemble_de_String.hasMoreElements()) {
			Communications.sendMessageUnique(message + Constantes.SPACE + ensemble_de_String.nextElement(), c.out);
		}
	}

	// FONCTION DE RECEPTION DE MESSAGE UNIQUE
	// -----------------------------------------------------
	static String receiveMessage(ClientInfo c) {
		try {
			return c.connexion_to_client.in.readLine();
		} catch (Exception e) {
			System.exit(-1);
		}
		return null;
	}

	static String receiveMessage(BufferedReader br) {
		try {
			return br.readLine();
		} catch (Exception e) {
			System.exit(-1);
		}
		return null;
	}

	// MESSAGES VERS CLIENT -> SERVEUR
	// ----------------------------------------------------------------------------
	static void MESSAGE_LOGISTIQUE_TO_SERVER(String type_de_message, ClientInfo c) {
		String message = type_de_message + Constantes.SPACE + c.adresse_IP_du_client_local + Constantes.SPACE
				+ Integer.toString(c.port_de_la_ServerSocket_locale);

		Communications.sendMessageUnique(message, c.connexion_to_server.out);
	}

	static void MESSAGE_BOTH_TO_SERVER(String type_de_message, ClientInfo c) {

		String message = type_de_message + Constantes.SPACE + c.adresse_IP_du_client_local + Constantes.SPACE
				+ Integer.toString(c.port_de_la_ServerSocket_locale) + Constantes.SPACE + c.adresse_IP_du_client_distant
				+ Constantes.SPACE + Integer.toString(c.port_de_la_ServerSocket_distant);

		Communications.sendMessageUnique(message, c.connexion_to_server.out);
	}

	// MESSAGE SERVEUR -> CLIENTS
	// -----------------------------------------------------------------------------

	static void MESSAGE_LOGISTIQUE_TO_CLIENTS(String type_de_message, String ip, String port, ServerInfo s) {
		Communications.sendMessageGroupe(type_de_message + Constantes.SPACE + ip + Constantes.SPACE + port,
				s.getListe_des_connexions().elements());
	}

	static void MESSAGE_LOGISTIQUE_TO_CLIENTS(String message, ServerInfo s) {
		Communications.sendMessageGroupe(message, s.getListe_des_connexions().elements());
	}

	// MESSAGE CLIENT -> CLIENTS
	// ------------------------------------------------------------------------------

	static void MESSAGE_DE_JEU_TO_CLIENT(String type_de_message, ClientInfo c) {
		Communications.sendMessageUnique(type_de_message, c.connexion_to_client.out);
	}

	static void MESSAGE_COORDONNEE_TO_CLIENT(String type_de_message, int ligne, int colonne, ClientInfo c) {
		String to_send = type_de_message + Constantes.SPACE + Integer.toString(ligne) + Constantes.SPACE
				+ Integer.toString(colonne);
		Communications.sendMessageUnique(to_send, c.connexion_to_client.out);
	}

}