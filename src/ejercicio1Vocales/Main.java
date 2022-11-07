package ejercicio1Vocales;

import java.util.Scanner;

/**
 * 
 * @author Daniel Vázquez
 *
 */
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String texto = sc.nextLine();
		sc.close();
		Contador contador = new Contador(0);
		hiloContador[] letras = new hiloContador[5];
		char[] vocales = {'a', 'e', 'i', 'o', 'u'};
		for (int i = 0; i < 5; i++) {
			letras[i] = new hiloContador(vocales[i], texto, contador);
			letras[i].start();
		}
		for (int i = 0; i < letras.length; i++) {
			try {
				letras[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(contador.value());
	}
}

class hiloContador extends Thread {
	private char letraContar;
	private String texto;
	private Contador contador;

	public hiloContador(char letraContar, String texto, Contador contador) {
		this.letraContar = letraContar;
		this.texto = texto;
		this.contador = contador;
	}

	public void run() {
		for (int posicionEnTexto = 0; posicionEnTexto < texto.length(); posicionEnTexto++) {
			if (texto.charAt(posicionEnTexto) == this.letraContar) {
				contador.increment();
			}
		}
	}
}

class Contador {
	private int c = 0;

	public Contador(int num) {
		this.c = num;
	}

	public synchronized void increment() {
		c++;
	}

	public synchronized void decrement() {
		c--;
	}

	public synchronized int value() {
		return c;
	}
}
