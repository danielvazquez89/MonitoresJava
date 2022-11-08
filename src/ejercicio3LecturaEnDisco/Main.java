package ejercicio3LecturaEnDisco;

/**
 * 
 * @author Daniel Vázquez
 *
 */
public class Main {
	public static void main(String[] args) {
		HD disco = new HD();
		Needle lector = new Needle(disco);
		lector.start();
		Proceso[] procesos = new Proceso[10];
		for (int i = 0; i < procesos.length; i++) {
			procesos[i] = new Proceso(i+1, disco);
			procesos[i].start();
		}
		for (int i = 0; i < procesos.length; i++) {
			try {
				procesos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		try {
//			lector.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("Fin del programa");
		System.exit(0);
	}
}
