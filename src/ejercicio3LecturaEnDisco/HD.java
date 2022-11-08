package ejercicio3LecturaEnDisco;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author Daniel Vázquez
 *
 */
public class HD {
	private boolean libre;
	private int pistaLeyendo;
	private boolean todosTerminados;
	private int procesosTerminados;

	public HD() {
		procesosTerminados = 0;
		todosTerminados = false;
		libre = false;
		this.pistaLeyendo = 0;
	}

	public synchronized void leerFichero(int pista) {
		System.out.println("Reading " + pista);
		libre = false;
		this.pistaLeyendo = pista;
		notifyAll();
		while (!libre) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notify();
//		this.pistaLeyendo = 0;
	}

	public synchronized void solicitarLectura(int pista) {
		while (pistaLeyendo != 0 && pistaLeyendo != pista) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (pistaLeyendo != pista) {
			int tiempoEspera = ThreadLocalRandom.current().nextInt(1000, 2000 + 1);
			try {
				Thread.sleep(tiempoEspera);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			leerFichero(pista);
		} else {
			System.out.println("Sharing " + pista);
			try {
				// Hago el wait para simular que está leyendo hasta que el notifyAll le deje
				// continuar
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void comprobarLectura() {
		while (pistaLeyendo == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void terminarLectura() {
		libre = true;
		pistaLeyendo = 0;
		notify();
	}

	public synchronized void aumentoProcesosTerminados() {
		procesosTerminados++;
		if (procesosTerminados == 10) {
			todosTerminados = true;
		}
	}

	public synchronized int getPistaLeyendo() {
		return pistaLeyendo;
	}

	public synchronized boolean getTodosTerminados() {
		return todosTerminados;
	}

}
