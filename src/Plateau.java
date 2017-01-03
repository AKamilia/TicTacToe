
public class Plateau {

	int tour_numero;
	int[][] plateau;

	public Plateau() {
		plateau = new int[3][3];
		tour_numero = 0;
	}

	public void print() {
		int cpt = 0;

		System.out.println("----------------------------");
		for (int i = 0; i < plateau.length; i++) {
			System.out.print("    |");
			for (int j = 0; j < plateau[i].length; j++) {

				if (plateau[i][j] == 1) {
					System.out.print("  X" + "  |");
				} else if (plateau[i][j] == 2) {
					System.out.print("  O" + "  |");
				} else {
					System.out.print("  " + cpt + "  |");
				}
				cpt++;
				if (j == plateau.length - 1) {
					System.out.println();
				}
			}
			System.out.println("----------------------------");
		}

	}

	public int set(int pion_de_qui, int ligne, int colonne) {
		if (ligne < 0 || ligne > 2 || colonne < 0 || colonne > 2) {
			return -1;
		}

		if (plateau[ligne][colonne] == 1 || plateau[ligne][colonne] == 2) {
			return -1;
		}

		plateau[ligne][colonne] = pion_de_qui;
		tour_numero++;

		return 0;

	}

	public int test_win() {
		int res = 0;

		// 1 ere ligne ---------------------------------
		if (plateau[0][0] == plateau[0][1] && plateau[0][1] == plateau[0][2]) {
			if (plateau[0][0] == 1) {
				return 1;
			}
			if (plateau[0][0] == 2) {
				return 2;
			}

		}

		// 2 eme ligne ---------------------------------
		if (plateau[1][0] == plateau[1][1] && plateau[1][1] == plateau[1][2]) {
			if (plateau[1][0] == 1) {
				return 1;
			}
			if (plateau[1][0] == 2) {
				return 2;
			}

		}

		// 3 eme ligne ---------------------------------
		if (plateau[2][0] == plateau[2][1] && plateau[2][1] == plateau[2][2]) {
			if (plateau[2][0] == 1) {
				return 1;
			}
			if (plateau[2][0] == 2) {
				return 2;
			}

		}

		// 1 ere colonne ---------------------------------
		if (plateau[0][0] == plateau[1][0] && plateau[1][0] == plateau[2][0]) {
			if (plateau[0][0] == 1) {
				return 1;
			}
			if (plateau[0][0] == 2) {
				return 2;
			}

		}

		// 2 eme colonne ---------------------------------
		if (plateau[0][1] == plateau[1][1] && plateau[1][1] == plateau[2][1]) {
			if (plateau[0][1] == 1) {
				return 1;
			}
			if (plateau[0][1] == 2) {
				return 2;
			}

		}

		// 3 eme colonne ---------------------------------
		if (plateau[0][2] == plateau[1][2] && plateau[1][2] == plateau[2][2]) {
			if (plateau[0][2] == 1) {
				return 1;
			}
			if (plateau[0][2] == 2) {
				return 2;
			}

		}

		// Deux DIAGONALES
		if (plateau[0][0] == plateau[1][1] && plateau[1][1] == plateau[2][2]) {
			if (plateau[0][0] == 1) {
				return 1;
			}
			if (plateau[0][0] == 2) {
				return 2;
			}

		}

		if (plateau[0][2] == plateau[1][1] && plateau[1][1] == plateau[2][0]) {
			if (plateau[0][2] == 1) {
				return 1;
			}
			if (plateau[0][2] == 2) {
				return 2;
			}

		}

		return res;
	}
}
