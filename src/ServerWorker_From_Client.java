
import java.net.Socket;

public class ServerWorker_From_Client implements Runnable {

	private Socket socket;
	private Connexion connexion;
	private ServerInfo serverInfo;
	private String ip;
	private String port;
	ServerWorker_From_Client(ServerInfo serverInfo, Socket s) {
		this.socket = s;
		this.serverInfo = serverInfo;

	}

	@Override
	public void run() {

		// CREATION DE LA CONNEXION
		// ---------------------------------------------------------------------

		this.connexion = new Connexion(socket);

		while (true) {

			// ON REGARDE CONSTAMENT SI ON RECOIT DES
			// MESSAGE-------------------------------------
			String m = Communications.receiveMessage(connexion.in);
			
	
			if (m != null) {

				System.out.println("Message recu : " + m);
				String[] temp = m.split(Constantes.SPACE);

				// EXIT
				// -------------------------------------------------------------------------------------
				if (temp[0].equals(Constantes.MESSAGE_DECONNEXION)) {
					System.out.println("	> Le client " + temp[1] + Constantes.SPACE + temp[2] + " s'est deconnecte");
					// On supprime le client de la liste
					// ----------------------------
					serverInfo.removeConnexion(temp[1] + Constantes.SPACE + temp[2]);
					Communications.MESSAGE_LOGISTIQUE_TO_CLIENTS(Constantes.MESSAGE_DECONNEXION, temp[1], temp[2],
							serverInfo);
					try {
						socket.close();
					} catch (Exception e) {
						System.out.println("Echec lors de la fermeture de la socket");
						return;
					}
					return;
				}

				// CODI
				// ----------------------------------------------------------------------------------
				else if (temp[0].equals(Constantes.FIRST_MESSAGE_CONNEXION_ET_DISPONIBLE)) {
					System.out.println("	> Nouveau Connecte: " + temp[1] + ":" + temp[2]);
					this.ip = temp[1];
					this.port = temp[2];
					// ON INFORME TOUT LE MONDE
					Communications.MESSAGE_LOGISTIQUE_TO_CLIENTS(Constantes.MESSAGE_DISPONIBILITE, temp[1], temp[2],
							serverInfo);

					// ON INFORME LE NOUVEL ARRIVANT
					Communications.sendMultipleMessageToOne(Constantes.MESSAGE_DISPONIBILITE,
							serverInfo.getListe_des_disponibles().keys(), connexion);

					// AJOUT DE LA CONNECTION A LA LISTE
					serverInfo.addConnexion(temp[1] + Constantes.SPACE + temp[2], connexion);

				}

				else if (temp[0].equals(Constantes.MESSAGE_DISPONIBILITE)) {
					// ON INFORME TOUT LE MONDE
					// -----------------------------------------------------------
					m = Constantes.MESSAGE_DISPONIBILITE + Constantes.SPACE + temp[1] + Constantes.SPACE + temp[2];
					Communications.sendMessageGroupe(m, serverInfo.getListe_des_disponibles().elements());
					System.out.println("	> Nouveau Connecte: " + temp[1] + ":" + temp[2]);

					// ON INFORME LE NOUVEL ARRIVANT
					// ------------------------------------------------------
					Communications.sendMultipleMessageToOne(Constantes.MESSAGE_DISPONIBILITE,
							serverInfo.getListe_des_disponibles().keys(), connexion);
					serverInfo.addDisponible(temp[1] + Constantes.SPACE + temp[2]);
				}

				if (temp[0].equals(Constantes.MESSAGE_INDISPONIBILITE)) {
					System.out.println("	> Le client " + temp[1] + ":" + temp[2] + " est occupe");
					System.out.println("	> Le client " + temp[3] + ":" + temp[4] + " est occupe\n");

					// On supprime le client de la
					// liste-----------------------------
					serverInfo.removeDisponible(temp[1] + Constantes.SPACE + temp[2]);
					serverInfo.removeDisponible(temp[3] + Constantes.SPACE + temp[4]);

					Communications.MESSAGE_LOGISTIQUE_TO_CLIENTS(m, serverInfo);

				}

				// WINNER
				// -----------------------------------------------------------------------------
				else if (temp[0].equals(Constantes.MESSAGE_WINNER_JOUEUR_1)) {
					System.out.println("	> Partie terminee : " + temp[1] + ":" + temp[2] + " Gagnant\n");
				} else if (temp[0].equals(Constantes.MESSAGE_WINNER_JOUEUR_2)) {
					System.out.println("	> Partie terminee : " + temp[3] + ":" + temp[4] + " Gagnant\n");
				} else if (temp[0].equals(Constantes.MESSAGE_MATCH_NUL)) {
					System.out.println("	> Partie terminee (Match Nul): " + temp[1] + ":" + temp[2] + " // "
							+ temp[3] + ":" + temp[4] + "\n");
				}
				// ------------------------------------------------------------------------------------

				
				
				
				
			}
			
			else{
				System.out.println("	> Le client " + ip + Constantes.SPACE + port + " is dead");
				serverInfo.removeConnexion(ip + Constantes.SPACE + port);
				Communications.MESSAGE_LOGISTIQUE_TO_CLIENTS(Constantes.MESSAGE_DECONNEXION, ip, port,serverInfo);
				return;
			}

		}

	}

}
