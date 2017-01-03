import java.util.Scanner;

public class Utilitaire {

	public static int attendre_int_entre(int borne_min, int borne_max) {
		int input = -1;
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while (input < borne_min || input > borne_max) {
			while (!scan.hasNextInt()) {
				scan.next();
			}
			input = scan.nextInt();
			if (input < borne_min || input > borne_max) {
				System.out.println("Veuillez entrer un entier correct entre " + borne_min + " et " + borne_max);
			}
		}
		return input;

	}

}
