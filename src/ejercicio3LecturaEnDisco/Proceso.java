package ejercicio3LecturaEnDisco;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author Daniel Vázquez
 *
 */
public class Proceso extends Thread {
	private int id;
	private ArrayList<Integer> tracklist;
//	private int[] tracklist;
	private HD disco;

	public Proceso(int id, HD disco) {
		this.id = id;
		this.disco = disco;
		int numeroAleatorio = ThreadLocalRandom.current().nextInt(1, 20 + 1);
		tracklist = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			do {
				numeroAleatorio = (ThreadLocalRandom.current().nextInt(1, 20 + 1));
			} while (tracklist.contains(numeroAleatorio));
			tracklist.add(numeroAleatorio);
		}
	}

	public void run() {
		int tiempoEspera;
		for (int i = 0; i < tracklist.size(); i++) {
			disco.solicitarLectura(tracklist.get(i));
			tracklist.set(i, 0);
			System.out.println("Progreso " + tracklistToString());
			tiempoEspera = ThreadLocalRandom.current().nextInt(1000, 2000 + 1);
			try {
				sleep(tiempoEspera);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Terminado P" + id);
	}

	private String tracklistToString() {
		String trackListString = "P" + id + ":";
		for (int i = 0; i < tracklist.size(); i++) {
			trackListString += " [ " + tracklist.get(i) + " ]";
		}
		return trackListString;
	}
}
