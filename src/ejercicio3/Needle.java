package ejercicio3;

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

	public synchronized void run() {
		while (!disco.getTodosTerminados()) {
			while (disco.getPistaLeyendo() == 0) {
				try {
//					System.out.println("A dormir");
					wait();
//					System.out.println("Despertado");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			int tiempoEspera = ThreadLocalRandom.current().nextInt(1000, 2000 + 1);
			try {
				sleep(tiempoEspera);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Read " + disco.getPistaLeyendo());
			disco.setLibre(true);
			disco.setPistaLeyendo(0);
			synchronized (disco) {
				disco.notify();
			}
		}
		System.out.println("Needle terminado");
	}
}
