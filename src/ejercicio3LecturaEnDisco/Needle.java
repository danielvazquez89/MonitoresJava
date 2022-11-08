package ejercicio3LecturaEnDisco;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author Daniel Vázquez
 *
 */
public class Needle extends Thread {
	private HD disco;

	public Needle(HD disco) {
		this.disco = disco;
	}

	public void run() {
		while (!disco.getTodosTerminados()) {
			disco.comprobarLectura();
				int tiempoEspera = ThreadLocalRandom.current().nextInt(1000, 2000 + 1);
				try {
					sleep(tiempoEspera);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Read " + disco.getPistaLeyendo());
				disco.terminarLectura();
		}
		System.err.println("Needle terminado");
	}
}
