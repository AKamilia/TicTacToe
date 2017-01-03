
public class WorkerClient_From_Server implements Runnable {

	ClientInfo clientInfo;

	public WorkerClient_From_Server(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	synchronized void addConnexion(String key) {
		this.clientInfo.Liste_des_connexions.add(key);
	}

	synchronized void removeConnexion(String s) {
		this.clientInfo.Liste_des_connexions.remove(s);
	}

	@Override
	public void run() {

		String s;

		while (true) {
			s = Communications.receiveMessage(this.clientInfo.connexion_to_server.in);
			if (s != null) {
				String[] temp = s.split(" ");

				// NOUVELLE CONNEXION
				// -------------------------------------------------
				if (temp[0].equals(Constantes.MESSAGE_DISPONIBILITE)) {
					// Il ne sajoute pas lui meme

					System.out.println(s);

					
						if(!this.clientInfo.Liste_des_connexions.contains(temp[1] + " " + temp[2])){
							if (!(temp[1].equals(clientInfo.adresse_IP_du_client_local) && temp[2].equals(Integer.toString(clientInfo.port_de_la_ServerSocket_locale)))) {
								System.out.println("ajout de " + temp[1] + " " + temp[2]);
								addConnexion(temp[1] + " " + temp[2]);
								clientInfo.liste_client_dirty = true;
							}
						}
				}

				// DECCONEXION OU OCCUPE
				// ----------------------------------------------
				if (temp[0].equals(Constantes.MESSAGE_DECONNEXION)) {
					removeConnexion(temp[1] + " " + temp[2]);
					clientInfo.liste_client_dirty = true;
				}

				if (temp[0].equals(Constantes.MESSAGE_INDISPONIBILITE)) {
					removeConnexion(temp[1] + " " + temp[2]);
					removeConnexion(temp[3] + " " + temp[4]);
					clientInfo.liste_client_dirty = true;

				}

			}
		}

	}

} // END -----------------
