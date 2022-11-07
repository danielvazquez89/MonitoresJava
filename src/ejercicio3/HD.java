package ejercicio3;

/**
 * 
 * @author Daniel Vázquez
 *
 */
public class HD {
	private boolean libre;
	private int pistaLeyendo;
	private boolean todosTerminados = false;
	private Needle lector;


	public HD() {
		libre = false;
		this.pistaLeyendo = 0;
	}

	public synchronized void leerFichero(int pista) {
		libre = false;
		this.pistaLeyendo = pista;
		synchronized (lector) {
			lector.notify();
		}
		System.err.println("Reading " + pista);
		while (!libre) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		this.pistaLeyendo = 0;
	}

	public synchronized int getPistaLeyendo() {
		return pistaLeyendo;
	}
	
	public synchronized void setPistaLeyendo(int pistaLeyendo) {
		this.pistaLeyendo = pistaLeyendo;
	}

	public synchronized void setTerminada(boolean terminada) {
		this.libre = terminada;
	}

	public synchronized boolean getLibre() {
		return libre;
	}

	public synchronized void setLibre(boolean libre) {
		this.libre = libre;
	}

	public synchronized boolean getTodosTerminados() {
		return todosTerminados;
	}
	
	public synchronized void setTodosTerminados(boolean todosTerminados) {
		this.todosTerminados = todosTerminados;
	}

	public synchronized void setLector(Needle lector) {
		this.lector = lector;
	}

}
