package ejercicio2ProductorConsumidor;

/**
 * 
 * @author Daniel Vázquez
 *
 */
public class Main {
	/**
	 * Método main que se ejecuta al iniciar el programa, este crea el objeto de
	 * depósito y se lo pasa al productor y consumidor para que compartan el mismo
	 * objeto. Posteriormente inicia la ejecución de los 2 hilos, productor y
	 * consumidor y espera a que terminen su ejecución.
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Deposito deposito = new Deposito();
		Productor productor = new Productor(deposito);
		Consumidor consumidor = new Consumidor(deposito);
		productor.start();
		consumidor.start();
		productor.join();
		consumidor.join();
	}
}

/**
 * 
 * Clase de la que creamos el hilo productor en el main, contiene el deposito
 * que es el objeto compartido, y el método run que es el que se ejecutará
 * cuando hacemos el start de este hilo. El método run contiene un for que
 * intentara guardar en el depósito con un for y una llamada al método guardar,
 * hará esto 20 veces como podemos ver en el for.
 *
 */
class Productor extends Thread {
	private Deposito deposito;

	public Productor(Deposito d) {
		deposito = d;
	}

	public void run() {
		for (int i = 1; i < 20; i++)
			deposito.guardar();
	}
}

/**
 * 
 * Clase de la que creamos el hilo consumidor en el main, contiene el deposito
 * que es el objeto compartido, y el método run que es el que se ejecutará
 * cuando hacemos el start de este hilo. El método run contiene un for que
 * intentara sacar un objeto del depósito con un for y una llamada al método
 * sacar, hará esto 20 veces como podemos ver en el for.
 *
 */
class Consumidor extends Thread {
	private Deposito deposito;

	public Consumidor(Deposito d) {
		deposito = d;
	}

	public void run() {
		for (int i = 1; i < 20; i++)
			deposito.sacar();
	}
}

/**
 * 
 * Clase que nos sirve para crear nuestro objeto compartido, tiene los elementos
 * como atributo que iniciaran con el valor 0, y 2 métodos synchronizados
 * guardar y sacar, que será usados por los hilos de las clases Consumidor y
 * Productor. Se usa el synchronized en estos para que no puedan ejecutarlos más
 * de un hilo al mismo tiempo y que no surjan problemas, ya que consultamos el
 * mismo atributo elementos y modificamos su valor.
 *
 */
class Deposito {
	private int elementos = 0;

	public synchronized void guardar() {
		// While para que mientras los elementos en nuestro depósito sean mayores que 0
		// el hilo espere para seguir la ejecución, ya que si hay más de 0 elementos
		// todavía no podremos guardar, porque el límite de elementos en nuestro
		// programa es 1.
		while (elementos > 0)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		elementos++;
		System.out.println("Guardando");
		// Notify avisará al otro hilo que tenemos, consumidor, de que puede continuar
		// su ejecución, sacandoló así de su while con el wait.
		notify();
	}

	public synchronized void sacar() {
		// While para que mientras los elementos en nuestro depósito sean 0 el hilo
		// espere para seguir la ejecución, ya que si no hay elementos no queremos que
		// saque.
		while (elementos == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		elementos--;
		System.out.println("Sacando");
		// Notify avisará al otro hilo que tenemos, productor, de que puede continuar su
		// ejecución, sacandoló así de su while con el wait.
		notify();
	}
}