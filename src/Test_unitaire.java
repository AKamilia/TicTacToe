
public class Test_unitaire {

	static Plateau p;

	public static void main(String[] args) {

		p = new Plateau();

		// Affichage ----------------
		// p.print();

		System.out.println("[TEST] POSE  DE PION ----------------------------------------  ");

		// Test coup normal
		// -----------------------------------------------------
		int result = 0;

		result = p.set(1, 0, 0);
		result = p.set(1, 0, 1);
		result = p.set(1, 0, 2);
		result = p.set(1, 1, 0);
		result = p.set(1, 1, 1);
		result = p.set(1, 1, 2);
		result = p.set(1, 2, 0);
		result = p.set(1, 2, 1);
		result = p.set(1, 2, 2);

		if (result == -1) {
			System.out.println("[ERROR] Pose pion sur case vide");
		} else {
			System.out.println("[GOOD] Pose pion sur case vide");
		}

		// Test COUP INTERDIT
		// ------------------------------------------------------
		result = p.set(1, 0, 0);

		if (result == 0) {
			System.out.println("[ERROR] Pose pion sur case occupee");
		} else {
			System.out.println("[GOOD] Pose pion sur case occupee");
		}

		System.out.println("[TEST] VICTOIRE ----------------------------------------  ");

		// Reset du plateau ------------------------
		p = new Plateau();

		// ligne haut --------------------
		result = p.set(1, 0, 0);
		result = p.set(1, 0, 1);
		result = p.set(1, 0, 2);

		result = p.test_win();

		if (result != 1) {
			System.out.println("[ERROR] Test du gagnant ligne haut");
		} else {
			System.out.println("[GOOD] Test du gagnant ligne haut");
		}

		p = new Plateau();

		// ligne haut --------------------
		result = p.set(1, 1, 0);
		result = p.set(1, 1, 1);
		result = p.set(1, 1, 2);

		result = p.test_win();

		if (result != 1) {
			System.out.println("[ERROR] Test du gagnant ligne milieu");
		} else {
			System.out.println("[GOOD] Test du gagnant ligne milieu");
		}

		p = new Plateau();

		// ligne haut --------------------
		result = p.set(1, 2, 0);
		result = p.set(1, 2, 1);
		result = p.set(1, 2, 2);

		result = p.test_win();

		if (result != 1) {
			System.out.println("[ERROR] Test du gagnant ligne bas");
		} else {
			System.out.println("[GOOD] Test du gagnant ligne bas");
		}

		// COLONNE
		// ---------------------------------------------------------------------------

		// Reset du plateau ------------------------
		p = new Plateau();

		// ligne haut --------------------
		result = p.set(1, 0, 0);
		result = p.set(1, 1, 0);
		result = p.set(1, 2, 0);

		result = p.test_win();

		if (result != 1) {
			System.out.println("[ERROR] Test du gagnant premiere colonne");
		} else {
			System.out.println("[GOOD] Test du gagnant premiere colonne");
		}

		// Reset du plateau ------------------------
		p = new Plateau();

		// ligne haut --------------------
		result = p.set(1, 0, 1);
		result = p.set(1, 1, 1);
		result = p.set(1, 2, 1);

		result = p.test_win();

		if (result != 1) {
			System.out.println("[ERROR] Test du gagnant deuxieme colonne");
		} else {
			System.out.println("[GOOD] Test du gagnant deuxieme colonne");
		}

		// Reset du plateau ------------------------
		p = new Plateau();

		// ligne haut --------------------
		result = p.set(1, 0, 2);
		result = p.set(1, 1, 2);
		result = p.set(1, 2, 2);

		result = p.test_win();

		if (result != 1) {
			System.out.println("[ERROR] Test du gagnant troisieme colonne");
		} else {
			System.out.println("[GOOD] Test du gagnant troisieme colonne");
		}

		// Reset du plateau ------------------------
		p = new Plateau();

		// ligne haut --------------------
		result = p.set(1, 2, 0);
		result = p.set(1, 1, 1);
		result = p.set(1, 0, 2);

		result = p.test_win();

		if (result != 1) {
			System.out.println("[ERROR] Test du gagnant diagonale / ");
		} else {
			System.out.println("[GOOD] Test du gagnant diagonale /");
		}

		// Reset du plateau ------------------------
		p = new Plateau();

		// ligne haut --------------------
		result = p.set(1, 0, 0);
		result = p.set(1, 1, 1);
		result = p.set(1, 2, 2);

		result = p.test_win();

		if (result != 1) {
			System.out.println("[ERROR] Test du gagnant diagonale \\ ");
		} else {
			System.out.println("[GOOD] Test du gagnant diagonale \\");
		}

		System.out.println("[TEST] MATCH NUL ----------------------------------------  ");

		p = new Plateau();

		result = p.set(1, 0, 0);
		result = p.set(1, 0, 1);
		result = p.set(1, 1, 2);
		result = p.set(1, 2, 0);
		result = p.set(1, 2, 2);
		result = p.set(2, 0, 2);
		result = p.set(2, 1, 0);
		result = p.set(2, 1, 1);
		result = p.set(2, 2, 1);

		p.print();
		result = p.test_win();

		if (result != 0) {
			System.out.println("[ERROR] Test du Match nul ");
		} else {
			System.out.println("[GOOD] Test du Match nul");
		}

	}
}
