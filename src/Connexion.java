import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexion {

	InputStream Flux_entrant;
	OutputStream Flux_sortant;
	PrintWriter out;
	BufferedReader in;

	public Connexion(Socket socket) {

		try {
			this.Flux_entrant = socket.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			this.Flux_sortant = socket.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		in = new BufferedReader(new InputStreamReader(this.getFlux_entrant()));
		out = new PrintWriter(this.Flux_sortant, true);

	}

	public InputStream getFlux_entrant() {
		return Flux_entrant;
	}

	public void setFlux_entrant(InputStream flux_entrant) {
		Flux_entrant = flux_entrant;
	}

	public OutputStream getFlux_sortant() {
		return Flux_sortant;
	}

	public void setFlux_sortant(OutputStream flux_sortant) {
		Flux_sortant = flux_sortant;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

}
