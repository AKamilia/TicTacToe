import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;


public class PatronClient {

	static ClientInfo clientInfo;
	static Scanner sc;

	public static void fonc_decision() {
		int decision = 0;
		System.out.println("\n\n\n[0] Rejouer \n[1] Quitter");
		while (sc.hasNext()) {
			if (sc.hasNextInt()) {
				decision = sc.nextInt();
				break;
			} else {
				sc.next();
			}
		}
		if (decision == 0) {
			clientInfo.Liste_des_connexions.clear();
			Communications.MESSAGE_LOGISTIQUE_TO_SERVER(Constantes.MESSAGE_DISPONIBILITE, clientInfo);
			clientInfo.veut_rejouer = true;
		} else {
			Communications.MESSAGE_LOGISTIQUE_TO_SERVER(Constantes.MESSAGE_DECONNEXION, clientInfo);
			clientInfo.veut_rejouer = false;
			System.exit(0);
		}

	}

	public static void main(String[] args) {

		try {
			clientInfo = new ClientInfo(args[0]);
			sc = new Scanner(System.in);
		} catch (Exception e) {
		}
		
		// AFFICHAGE -------------------------------------------------------
		System.out.println(Constantes.CLIENT_HANDSOME);

		// DECLARATIONS ----------------------------------------------------
		System.out.println("Informations Perso -----------------------------");
		System.out.println("	 > Adresse IP : " + clientInfo.adresse_IP_du_client_local);
		System.out.println("	 > Port Client/Client : " + clientInfo.port_de_la_ServerSocket_locale);
		System.out.println("------------------------------------------------");

		// ON LANCE LE THREAD QUI REAGIT LORS D'UN CTRL-C
		//Runtime.getRuntime().addShutdownHook(new Thread(new SigInt_Capturer(clientInfo)));

		// MESSAGE ETAPE 2
		// ----------------------------------------------------------------------
		Communications.MESSAGE_LOGISTIQUE_TO_SERVER(Constantes.FIRST_MESSAGE_CONNEXION_ET_DISPONIBLE, clientInfo);
		// --------------------------------------------------------------------------------------
		Thread t = null;
		Thread t1 = new Thread(new WorkerClient_From_Server(clientInfo));
		t1.start();
		Thread t2 = new Thread(new WorkerClient_From_Client(clientInfo));
		t2.start();
		clientInfo.liste_client_dirty = false;
		while (true) {
			if (clientInfo.est_occupe != true) {
	
				System.out.println("[0] Contacter un joueur \n" + "[1] Se mettre en attente\n" + "[2] Quitter");
				int choix = Utilitaire.attendre_int_entre(0, 2);

				
				
				
				
				
				
				
				
				
				
				
				if (choix == 0) {
					if(clientInfo.une_demande_en_attente != true){
						choix = -1;
						if (clientInfo.Liste_des_connexions.size() == 0) {
							System.out.println(Constantes.USER_0_JOUEURS_DISPONIBLE);
							Communications.MESSAGE_LOGISTIQUE_TO_SERVER(Constantes.MESSAGE_DECONNEXION, clientInfo);
							t1.interrupt();
							t2.interrupt();
							System.exit(0);
						}
	
						clientInfo.est_occupe = true;
						clientInfo.est_initiateur = true;
	
						if(clientInfo.liste_client_dirty != true){
							
						System.out.println(Constantes.USER_SELECTIONNEZ_ADVERSAIRE);
						for (int i = 0; i < clientInfo.Liste_des_connexions.size(); i++) {
							System.out.println("[" + i + "] " + clientInfo.Liste_des_connexions.get(i));
						}
	
						choix = Utilitaire.attendre_int_entre(0, clientInfo.Liste_des_connexions.size() - 1);
						String info_client_distant = null;
						
						
	
								info_client_distant = clientInfo.Liste_des_connexions.get(choix);
						
		
							try {
								clientInfo.socket_to_client = new Socket(info_client_distant.split(" ")[0],Integer.parseInt(info_client_distant.split(" ")[1]));
								clientInfo.connexion_to_client = new Connexion(clientInfo.socket_to_client);
								clientInfo.adresse_IP_du_client_distant = info_client_distant.split(" ")[0];
								clientInfo.port_de_la_ServerSocket_distant = Integer
										.parseInt(info_client_distant.split(" ")[1]);
							} catch (Exception e) {
								System.out.println("Impossible d'etablir une connexion avec le client distant");
								clientInfo.est_occupe = false;
								clientInfo.est_initiateur = false;
								System.exit(-1);
							}
		
							if (clientInfo.est_initiateur) {
		
								Communications.MESSAGE_DE_JEU_TO_CLIENT(Constantes.MESSAGE_GAME, clientInfo);
		
								System.out.println(Constantes.USER_ATTENTE_REPONSE);
								String s = Communications.receiveMessage(clientInfo);
		
								if (s != null && s.equals(Constantes.MESSAGE_OK)) {
									System.out.println(Constantes.USER_DEMANDE_ACCEPTEE);
									Communications.MESSAGE_BOTH_TO_SERVER(Constantes.MESSAGE_INDISPONIBILITE, clientInfo);
									t = new Thread(new Game(clientInfo));
									t.start();
									try {
										t.join();
										clientInfo.est_occupe = false;
										clientInfo.est_initiateur = false;
										fonc_decision();
		
									} catch (Exception e) {
										System.exit(-1);						
									}
		
								} else if (s == null || s.equals(Constantes.MESSAGE_NO)) {
									System.out.println(Constantes.USER_DEMANDE_REFUSEE);
									clientInfo.est_occupe = false;
									clientInfo.est_initiateur = false;
								
								} else {
									System.out.println("Message indetermine");
									System.exit(-1);
								}
							}
							
						}else{
							System.out.println("La liste a ete mise a jour entre temps, recommencez ... ");
							clientInfo.est_occupe = false;
							clientInfo.est_initiateur = false;
							clientInfo.liste_client_dirty = false;
						}
					}
					else{
						System.out.println("Impossible, vous avez une demande en attente");
						clientInfo.est_occupe = false;
						clientInfo.est_initiateur = false;
					}
				} 
				
				
				
				
				
				
				
				
				
				else if (choix == 1) {
					choix = -1;
					clientInfo.est_occupe = false;
					clientInfo.une_demande_en_attente = false;
					
					if(clientInfo.socket_to_client != null){
						clientInfo.connexion_to_client = new Connexion(clientInfo.socket_to_client);
						clientInfo.est_occupe = true;
						clientInfo.est_initiateur = false;
						clientInfo.adresse_IP_du_client_distant = clientInfo.socket_to_client.getRemoteSocketAddress().toString();

						System.out.println(Constantes.USER_ATTENTE_REPONSE);
						String s = Communications.receiveMessage(clientInfo);
						
							if (s != null && s.equals(Constantes.MESSAGE_GAME)) {
								System.out.println("Un client a demander a jouer avec vous. Accepter ?\n[0] Oui \n[1] Non");
	
								choix = Utilitaire.attendre_int_entre(0, 1);
	
								if (choix == 0) {
	
									t = new Thread(new Game(clientInfo));
									t.start();
	
									Communications.MESSAGE_DE_JEU_TO_CLIENT(Constantes.MESSAGE_OK, clientInfo);
	
									try {
										t.join();
										clientInfo.est_occupe = false;
										clientInfo.est_initiateur = false;
										fonc_decision();
									} catch (Exception e) {
										return;
									}
	
								} else {
									Communications.MESSAGE_DE_JEU_TO_CLIENT(Constantes.MESSAGE_NO, clientInfo);
									clientInfo.adresse_IP_du_client_distant = null ;
									clientInfo.socket_to_client = null ;
									clientInfo.port_de_la_ServerSocket_distant = 0;
									clientInfo.est_occupe = false;
									clientInfo.est_initiateur = false;
								}
							}
						}else{
							
							clientInfo.est_occupe = false;
							clientInfo.est_initiateur = false;
						}
					
						
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				else{
					
					
					try{
						
						Communications.MESSAGE_LOGISTIQUE_TO_SERVER(Constantes.MESSAGE_DECONNEXION, clientInfo);
						t.interrupt();
						t1.interrupt();
						t2.interrupt();
					}catch (Exception e) {
						System.exit(-1);
					}
					
					System.out.println("EXIT");
					System.exit(-1);
				}
				
			} // if occuped
		} // while true
	}

}
