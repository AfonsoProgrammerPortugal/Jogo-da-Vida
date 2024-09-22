package project;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Produz uma animação do Jogo da Vida a partir de uma configuração inicial lida de um ficheiro de texto.
 *
 * O nome do ficheiro de texto é solicitado ao utilizador.
 * Caso o utilizador não indique um nome, é usado o ficheiro 'seis_barras.txt' por omissão.
 * O número de iterações e o intervalo entre frames são também solicitados ao utilizador.
 *
 * @author csl
 */
public class AppAnimacoes {

	public static void main(String[] args) throws IOException {
		int[] regra = {3, 2, 3};   // regra usual do Jogo da Vida

		Scanner leTeclado = new Scanner(System.in);   
		System.out.println("Nome do ficheiro inicial, da pasta mundos");
		System.out.print("ou insira Enter: ");
		String nome = leTeclado.nextLine();

		if (nome.length() > 0)
			nome = "./mundos/" + nome;
		else {
			nome = "./mundos/seis_barras.txt";
			System.out.println("sera usado o ficheiro seis_barras.txt");
		}

		// novo mundo a inicializar a partir do ficheiro cujo nome foi instanciado acima
		Mundo novoMundo = new Mundo(nome);

		// escolha do número de iterações
		System.out.print("Numero de iteracoes: ");
		int n = leTeclado.nextInt();

		// escolha do intervalo entre frames, que determina a velocidade da animação
		System.out.print("Intervalo entre frames (em segundos fraccionarios): ");
		leTeclado.useLocale(Locale.US); // para usar o ponto como separador de decimais
		double atraso = leTeclado.nextDouble();

		leTeclado.close();

		novoMundo.animaMundo(n, regra, atraso);
	}

}
