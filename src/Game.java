import java.util.Scanner;

public class Game implements Runnable {

	// Donnee du client ----------------------
	ClientInfo clientInfo;

	// Donee logistique ----------------------
	Scanner sc;
	String message_recu;

	// Donnee de jeu -------------------------
	Plateau plateau;
	int ligne;
	int colonne;
	boolean est_fini;
	boolean mon_tour;
	static int ID_ADVERSAIRE = 2;
	static int ID_NOUS = 1;
	static int CHOIX_BORNE_MIN = 0;
	static int CHOIX_BORNE_MAX = 8;
	static int TOUR_BORNE_MAX = 9;

	public Game(ClientInfo clientInfo) {
		this.plateau = new Plateau();
		this.clientInfo = clientInfo;
		this.message_recu = null;
		this.ligne = 0;
		this.colonne = 0;
		this.est_fini = false;

		if (clientInfo.est_initiateur == true) {
			this.mon_tour = true;
		} else {
			this.mon_tour = false;
		}

	}

	public void attente() {
		message_recu = Communications.receiveMessage(clientInfo);
		if (message_recu != null) {
			String[] message_temporaire = message_recu.split(Constantes.SPACE);
			if (message_temporaire.length == 3) {
				if (message_temporaire[0].equals(Constantes.MESSAGE_COOR)) {
					if (plateau.set(ID_ADVERSAIRE, Integer.parseInt(message_temporaire[1]),
							Integer.parseInt(message_temporaire[2])) == -1) {
						System.out.println(Constantes.USER_ACTION_INTERDITE);
						return;
					}
					plateau.print();
					System.out.println();
				} else {
					System.out.println("[WAIT] Un message " + Constantes.MESSAGE_COOR + " etait attendu. Exit ...");
					System.out.println("Recu : " + message_recu);
					return;
				}
			} else {
				System.out.println("[WAIT] Le message n'a pas le bon format. Exit ... ");
				System.out.println("Recu : " + message_recu);
				return;
			}
		}
	}

	
	
	
	/*
	 * Demande Utilisateur nombre entre 0 et 8
	 * Met le Pion correspondant sur le plateau
	 * Envoie le message au client
	 */
	
	public void action() {
		int result_action = -1;
		while (result_action == -1) {
			plateau.print();
			System.out.println("\n" + Constantes.USER_SELECTION_CASE);
			to_ligne_et_colonne(Utilitaire.attendre_int_entre(CHOIX_BORNE_MIN, CHOIX_BORNE_MAX));
			result_action = plateau.set(ID_NOUS, ligne, colonne);
			if (result_action == -1) {
				System.out.println(Constantes.USER_ACTION_INTERDITE);
			}
		}
		Communications.MESSAGE_COORDONNEE_TO_CLIENT(Constantes.MESSAGE_COOR, ligne, colonne, clientInfo);
	}

	
	
	
	
	
	
	
	
	/*
	 * Fonction de test si un utilisateur a gagne ou si match nul
	 * Si match nul ou victoire , l'initiateur envoie le message au serveur
	 * 
	 */
	public void test_de_fin_de_partie() {
		if (plateau.test_win() == 0 && plateau.tour_numero >= TOUR_BORNE_MAX) {
			System.out.println(Constantes.USER_MATCH_NUL);
			est_fini = true;
			if (clientInfo.est_initiateur == true) {
				System.out.println("envoie de win");
				Communications.MESSAGE_BOTH_TO_SERVER(Constantes.MESSAGE_MATCH_NUL, clientInfo);
			}
		}
		if (plateau.test_win() == ID_ADVERSAIRE) {
			System.out.println(Constantes.USER_PERDU);
			est_fini = true;
			if (clientInfo.est_initiateur == true) {
				Communications.MESSAGE_BOTH_TO_SERVER(Constantes.MESSAGE_WINNER_JOUEUR_2, clientInfo);
			}
		}
		if (plateau.test_win() == ID_NOUS) {
			System.out.println(Constantes.USER_GAGNE);
			est_fini = true;
			if (clientInfo.est_initiateur == true) {
				Communications.MESSAGE_BOTH_TO_SERVER(Constantes.MESSAGE_WINNER_JOUEUR_1, clientInfo);
			}
		}
	}

	
	
	
	
	
	/*
	 * Fonction de conversion d'un entier correspondant a une case en des coordonee tableau
	 */
	public void to_ligne_et_colonne(int case_choisie) {
		switch (case_choisie) {
		case 0:
			ligne = 0;
			colonne = 0;
			break;
		case 1:
			ligne = 0;
			colonne = 1;
			break;
		case 2:
			ligne = 0;
			colonne = 2;
			break;
		case 3:
			ligne = 1;
			colonne = 0;
			break;
		case 4:
			ligne = 1;
			colonne = 1;
			break;
		case 5:
			ligne = 1;
			colonne = 2;
			break;
		case 6:
			ligne = 2;
			colonne = 0;
			break;
		case 7:
			ligne = 2;
			colonne = 1;
			break;
		case 8:
			ligne = 2;
			colonne = 2;
			break;
		default:
			break;
		}

	}

	
	/*
	 * Deroulement de la partie
	 * L'initiateur commence en premier par jouer
	 * Le non initiateur attend que l'autre ai fini son tour
	 */
	@Override
	public void run() {

		sc = new Scanner(System.in);
		System.out.println(Constantes.USER_PARTIE_DEMARREE);
		plateau = new Plateau();

		while (est_fini == false) {
			if (mon_tour == true) {
				test_de_fin_de_partie();
				action();
				test_de_fin_de_partie();
				mon_tour = false;
			} else {
				System.out.println(Constantes.USER_ATTENTE_REPONSE);
				attente();
				test_de_fin_de_partie();
				mon_tour = true;
			}
			if (est_fini == true) {
				break;
			}
		}

		System.out.println(Constantes.USER_PARTIE_TERMINE);
		clientInfo.est_occupe = false;
		clientInfo.est_initiateur = false;
		return;
	}
}
