package ejercicio3;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author Daniel Vázquez
 *
 */
public class Proceso extends Thread {
	private int id;
	private int[] tracklist;
	private HD disco;
	private Object monitor;

	public Proceso(int id, HD disco, Object monitor) {
		this.id = id;
		this.disco = disco;
		this.monitor = monitor;
		tracklist = new int[10];
		for (int i = 0; i < tracklist.length; i++) {
			tracklist[i] = 1 + (int) Math.floor(Math.random() * 20);
		}
	}

	public void run() {
		int tiempoEspera;
		for (int i = 0; i < tracklist.length; i++) {
			while (disco.getPistaLeyendo() != 0 && disco.getPistaLeyendo() != tracklist[i]) {
				try {
//					System.out.println("P" + id + " a dormir");
					synchronized (monitor) {						
						monitor.wait();
					}
//					System.out.println("P" + id + " a despertar");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (disco.getPistaLeyendo() != tracklist[i]) {
				synchronized (disco) {
					tiempoEspera = ThreadLocalRandom.current().nextInt(1000, 2000 + 1);
					try {
						sleep(tiempoEspera);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					System.out.println("P" + id + " pasa a leer " + tracklist[i] + " en posición del array " + i);
//				if (disco.getPistaLeyendo() == 0 ) {
					disco.leerFichero(tracklist[i]);
					synchronized (monitor) {
						monitor.notify();
					}
				}
			} else {
				System.out.println("Sharing " + tracklist[i]);
			}
			tiempoEspera = ThreadLocalRandom.current().nextInt(1000, 2000 + 1);
			try {
				sleep(tiempoEspera);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Terminado P" + id);
	}
}
