package project;

import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

/**
 * Criação e iteração de autómatos celulares.
 *
 * @author Afonso Santos, fc59808
 *
 * Na versão actual, está implementado o Jogo da Vida, de Conway, com a opção de
 * configurar variantes não-standard do mesmo através da regra de actualização
 * de células que é passada aos métodos de iteração do mundo.
 *
 * @author csl
 */
public class Mundo {
	
	private int[][] mundo;  // int desperdiça muita memória, mas facilita alteração para outros autómatos celulares
	private int numLinhas;  // redundante, mas de acordo com o padrão "explanatory variable"
	private int numColunas; // redundante, mas de acordo com o padrão "explanatory variable"

	/**
	 * Inicializa o mundo a partir de uma matriz de 0s e 1s.
	 * 
	 * @param mundoInicial matriz constituída por 0s e 1s
	 * @requires mundoInicial.length >= 2
	 * @requires mundoInicial[0].length >= 2
	 */
	public Mundo(int[][] mundoInicial) {

		this.numLinhas = mundoInicial.length;
		this.numColunas = mundoInicial[0].length;
		
		this.mundo = new int[this.numLinhas][this.numColunas];
		
		for (int i = 0; i < this.numLinhas; i++) {
			for (int j = 0; j < this.numColunas; j++) {
				this.mundo[i][j] = mundoInicial[i][j];				
			}
		}
	}
	
	/**
	 * Inicializa o mundo a partir de um ficheiro de texto.
	 * 
	 * O tamanho do mundo a inicializar é determinado pelo número de linhas e de "colunas"
	 * do ficheiro.
	 * 
	 * @param ficheiroMundoInicial nome (ou path) do ficheiro de inicialização
	 * @requires os dados têm de estar armazenados no ficheiro no mesmo formato
	 *   usado por escreveMundo()
	 * @requires o ficheiro tem de ter pelo menos 2 linhas com dados válidos,
	 *   permitindo assim criar um mundo com pelo menos 2 linhas
	 * @requires o ficheiro tem de ter pelo menos 2 "colunas" com dados válidos,
	 *   permitindo assim criar um mundo com pelo menos 2 colunas
	 * @requires robustez: o ficheiro pode terminar com uma mudança de linha, ou não;
	 *   isso não deve afectar o mundo lido nem o respectivo tamanho
	 */
	public Mundo(String ficheiroMundoInicial) throws IOException {
		
		Scanner sizeReader = new Scanner(new File(ficheiroMundoInicial));
		Scanner cellReader = new Scanner(new File(ficheiroMundoInicial));
		
		int numLinhas = 0;
		int numColunas = sizeReader.nextLine().split(" ").length;
		numLinhas++;
		
		while (sizeReader.hasNextLine()) {
			sizeReader.nextLine();
			numLinhas++;
		}
		
		String[][] receiver = new String[numLinhas][numColunas];
		int[][] mundo = new int[numLinhas][numColunas];
		
		for (int i = 0; i < numLinhas; i++) {
			receiver[i] = cellReader.nextLine().split(" ");
			for (int j = 0; j < numColunas; j++) {
				if (receiver[i][j].equals(".")) {
					mundo[i][j] = 0;
				}else if (receiver[i][j].equals("X")){
					mundo[i][j] = 1;
				}
			}
		}
		sizeReader.close();
		cellReader.close();
		
		this.mundo = mundo;
		this.numLinhas = numLinhas;
		this.numColunas = numColunas;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mundo other = (Mundo) obj;
		return Arrays.deepEquals(mundo, other.mundo);
	}

	/**
	 * Determina se uma célula vive na geração seguinte.
	 *
	 * submatriz é uma matriz quadrada com 9 elementos, onde o elemento central é
	 * a célula cuja vida é determinada de acordo com a regra escolhida.
	 * 1 denota uma célula viva, 0 denota uma célula morta.
	 * Seja regra = {maxVizinhos, minVizinhos, vizinhosParaNascer}.
	 * Uma célula viva:
	 * -- morre se tiver mais de maxVizinhos vivos;
	 * -- morre se tiver menos de minVizinhos vivos;
	 * -- permanece viva nos restantes casos.
	 * Uma célula morta:
	 * -- renasce se tiver exactamente vizinhosParaNascer vizinhos vivos;
	 * -- permanece morta nos restantes casos.
	 * 
	 * @param submatriz matriz 3x3 de 0s e 1s
	 * @param regra array com 3 elementos
	 * @requires cada elemento de regra está entre 0 e 8 inclusive
	 * @return true se a célula central fica viva com a regra escolhida, false caso contrário
	 */
	// visibilidade poderia ser private ou package, mas fica public por simplicidade
	public static boolean celulaVive(int[][] submatriz, int[] regra) {
		
		boolean result = false;
		int neighbours = 0;
		int size = submatriz.length;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (submatriz[i][j] == 1) {
					neighbours++;					
				}
			}
		}
		if (submatriz[1][1] == 1) {
			result = true;
			neighbours--;
			if (neighbours > regra[0] || neighbours < regra[1]) {
				result = false;
			}
		}
		else if (neighbours == regra[2]) {
			result = true;
		}
		
		return result; 
	}
	
	public int getNumLinhas() {
		return this.numLinhas;
	}
	
	public int getNumColunas() {
		return this.numColunas;
	}
	
	/**
	 * Reinicializa o mundo, tornando todas as suas células mortas.
	 */
	public void zeraMundo() {
		for (int i = 0; i < this.numLinhas; i++) {
			for (int j = 0; j < this.numColunas; j++) {
				this.mundo[i][j] = 0;
			}
		}
	}
	
	/**
	 * Atribui um valor a uma célula.
	 *
	 * Força a atribuição de um valor à célula do mundo identificada pela respectiva posição.
	 * 
	 * @param linha índice da linha da célula que se quer afectar
	 * @param coluna índice da coluna da célula que se quer afectar
	 * @param valor a atribuir à célula
	 * @requires {@literal 0 <= linha < numLinhas}
	 * @requires {@literal 0 <= coluna < numColunas}
	 * @requires valor é 0 ou 1
	 */
	public void atribuiValorCelula(int linha, int coluna, int valor) {
		this.mundo[linha][coluna] = valor;
	}
	
	/**
	 * Devolve o valor de uma célula do mundo.
	 *
	 * @param linha índice da linha da célula cujo valor se quer obter
	 * @param coluna índice da coluna da célula cujo valor se quer obter
	 * @requires {@literal 0 <= linha < numLinhas}
	 * @requires {@literal 0 <= coluna < numColunas}
	 * @return 0 ou 1, conforme o valor da célula
	 */
	public int valorDaCelula(int linha, int coluna) {
		return this.mundo[linha][coluna];
	}
	
	/**
	 * Actualiza o estado de todas as células do mundo, de acordo com a regra.
	 * 
	 * Define o valor de cada célula do mundo para a iteração seguinte com a regra dada,
	 * alterando esse valor onde necessário.
	 * As condições de fronteira a concretizar são periódicas.
	 *  
	 * @param regra array com 3 elementos
	 * @requires cada elemento de regra está entre 0 e 8 inclusive
	 */
	// Sugestões (opcionais) de implementação.
	// Devido a as condições de fronteira a concretizar serem periódicas,
	//  mundo[i][numColunas] deve replicar mundo[i][0]
	//  mundo[i][-1] deve replicar mundo[i][numColunas-1]
	//  mundo[numLinhas][j] deve replicar mundo[0][j]
	//  mundo[-1][j] deve replicar mundo[numLinhas-1][j]
	// Como as componentes de replicação estão FORA do mundo, considera-se que existem
	// numa MATRIZ AUMENTADA, com 1 linha acrescentada antes da primeira linha de mundo,
	// e 1 linha acrescentada após a última linha de mundo; idem para 1 coluna acrescentada
	// antes e outra depois das colunas originais do mundo.
	// No entanto, não é preciso criar explicitamente uma matriz aumentada. O fundamental
	// é que cada célula numa linha ou coluna limítrofe consiga encontrar os seus vizinhos
	// no lado oposto do mundo.
	public void iteraMundo(int[] regra) {
		
		int[][] bigWorld = matrizAumentada();
		
		for (int i = 0; i < this.numLinhas; i++) {
			for (int j = 0; j < this.numColunas; j++) {
				
				int[][] subMatriz = new int[3][3];
				
				subMatriz[0][0] = bigWorld[i][j];
		        subMatriz[0][1] = bigWorld[i][j+1];
		        subMatriz[0][2] = bigWorld[i][j+2];
		        subMatriz[1][0] = bigWorld[i+1][j];
		        subMatriz[1][1] = bigWorld[i+1][j+1];
		        subMatriz[1][2] = bigWorld[i+1][j+2];
		        subMatriz[2][0] = bigWorld[i+2][j];
		        subMatriz[2][1] = bigWorld[i+2][j+1];
		        subMatriz[2][2] = bigWorld[i+2][j+2];

				if (celulaVive(subMatriz,regra)) {
					this.mundo[i][j] = 1;
				}
				else {
					this.mundo[i][j] = 0;
				}
			}
		}
	}

	/**
	 * Itera o mundo n vezes, de acordo com a regra.
	 * 
	 * @param n número de iterações a efectuar
	 * @param regra array com 3 elementos
	 * @requires cada elemento de regra está entre 0 e 8 inclusive
	 */
	public void iteraMundoNgeracoes(int n, int[] regra) {
		for (int i = 0; i < n; i++) {
			iteraMundo(regra);
		}
	}
	
	/**
	 * Mostra o mundo no output standard (ecrã, terminal, consola).
	 *
	 * Imprime a matriz mundo no ecrã, linha a linha, representando
	 *  cada 0 pela sequência ". " (ponto seguido de espaço) e
	 *  cada 1 pela sequência "X " (x maiúsculo seguido de espaço);
	 *  cada linha impressa termina com um espaço, que se segue a '.' ou a 'X'.
	 */
	public void mostraMundo() {
		
		String world = toString();
		
		System.out.println(world);
	}

	/**
	 * Regista o mundo num ficheiro de texto.
	 *
	 * Comporta-se como mostraMundo, mas escrevendo num ficheiro em vez de no ecrã.
	 * Regista a matriz mundo no ficheiro, linha a linha, representando
	 *  cada 0 pela sequência ". " (ponto seguido de espaço) e
	 *  cada 1 pela sequência "X " (x maiúsculo seguido de espaço);
	 *  cada linha registada termina com um espaço, que se segue a '.' ou a 'X'.
	 * Se o ficheiro nomeFicheiro existir, ele é reescrito; caso contrário,
	 * é criado um novo ficheiro de texto com esse path.
	 * Nota de API: poderia ser apenas uma variante de mostraMundo sobrecarregada (overloaded),
	 * mas optou-se por métodos com nomes diferentes para escrever no output standard e em ficheiros.
	 * 
	 * @see mostraMundo() formato de armazenamento dos dados no ficheiro
	 * @param nomeFicheiro é uma string que representa um path válido para um ficheiro
	 */
	public void escreveMundo(String nomeFicheiro)  throws IOException {
		
		PrintWriter writer = new PrintWriter(new File(nomeFicheiro));
		String world = toString();
		
		writer.write(world);
		
		writer.close();
	}
		
	/**
	 * Representação deste mundo como uma string.
	 * 
	 * O formato da string é como descrito no contrato de mostraMundo.
	 * 
	 * @return uma string que termina com um carácter de mudança de linha
	 */
	@Override
	public String toString() {
		
		StringBuilder bob = new StringBuilder();
		
		for (int i = 0; i < this.numLinhas; i++) {
			for (int j = 0; j < this.numColunas; j++) {
				if (this.mundo[i][j] == 0) {
					bob.append(". ");
				}
				else {
					bob.append("X ");
				}
			}
			bob.append(System.lineSeparator());
		}
		
		return bob.toString(); 
	}

	/**
	 * Mostra uma animação do mundo no output standard (ecrã).
	 *
	 * Imprime a condição inicial do mundo, e imprime depois
	 *    n sucessivos fotogramas (frames) de uma animação da evolução do mundo
	 *    a partir dessa condição inicial, usando a regra dada;
	 *  em cada frame, imprime a matriz mundo no ecrã, linha a linha,
	  *   representando
	 *    cada 0 pela string ". " (ponto seguido de espaço) e
	 *    cada 1 pela string "X " (x maiúsculo seguido de espaço);
	 *    cada linha impressa termina com um espaço, que se segue a '.' ou a 'X'.
	 *  antes de ser impresso o fotograma inicial (condição inicial), o ecrã é
	 *    apagado;
	 *  cada fotograma sobrepõe-se ao anterior, que foi entretanto apagado;
	 *  o tempo entre fotogramas é dado pelo valor de atraso em segundos;
	 *  este método altera o estado do mundo.
	 *  
	 * @param n número de iterações a efectuar
	 * @param regra array com 3 elementos
	 * @param atrasoEmSegundos intervalo de tempo entre fotogramas sucessivos
	 * @requires {@literal n > 0}
	 * @requires cada elemento de regra está entre 0 e 8 inclusive
	 * @requires {@literal atrasoEmSegundos > 0}
	 */
	// A implementação actual NÃO RESPEITA O CONTRATO no requisito de apagar o ecrã
	// antes da apresentação de cada frame.
	// Isso tem a ver com limitações da consola do Eclipse, onde é suposto o projecto ser
	// testado. Noutros ambientes / terminais é possível ter um método limpaConsola efectivo.
	// Prém, a implementação actual prejudica pouco a experiência de visualização.
	public void animaMundo(int n, int[] regra, double atrasoEmSegundos) {
		int i; // contador de iterações
		limpaConsola();
		mostraMundo();
		for (i = 0; i < n; i++) {
			atrasa(atrasoEmSegundos);
			limpaConsola();
			iteraMundo(regra);
			mostraMundo();
		}	
	}
	  
    // limpa a consola, preparando-a para nova escrita
  	// implementação provisória; NÃO FAZ O PRETENDIDO;
    // apenas deixa 2 linhas de intervalo entre frames, o que facilita a visualização da animação
    public final static void limpaConsola() {
    	System.out.println("\n");  // 2 linhas de intervalo
    }

	// atrasa (i.e., empata) a execução desta thread
	public final static void atrasa(double segundos) {
		try {
			Thread.sleep((long)(segundos * 1000));
	    }
	    catch(InterruptedException ex) {
	        Thread.currentThread().interrupt();
	    }
	}	
	
	/**
	 * Cria uma versão aumentada do mundo
	 * 
	 * @return matriz aumentado do mundo
	 */
	private int[][] matrizAumentada() {
		
		int bigLinhas = numLinhas+2;
		int bigColunas = numColunas+2;
		int[][] bigWorld = new int[bigLinhas][bigColunas];
		//Centro da matriz aumentada
		for (int i = 1; i <= this.numLinhas; i++) {
			for (int j = 1; j <= this.numColunas; j++) {
				bigWorld[i][j] = this.mundo[i-1][j-1];
			}
		}
		//Fronteiras da matriz
		for (int i = 1; i < bigLinhas-1; i++) {
			for (int j = 1; j < bigColunas-1; j++) {
				bigWorld[0][j] = bigWorld[this.numLinhas][j];
				bigWorld[i][0] = bigWorld[i][this.numColunas];
				bigWorld[bigLinhas-1][j] = bigWorld[1][j];
				bigWorld[i][bigColunas-1] = bigWorld[i][1];
			}
		}
		//Cantos das fronteiras
		bigWorld[0][0] = bigWorld[this.numLinhas][this.numColunas];
		bigWorld[bigLinhas-1][0] = bigWorld[1][this.numColunas];
		bigWorld[bigLinhas-1][bigColunas-1] = bigWorld[1][1];
		bigWorld[0][bigColunas-1] = bigWorld[this.numLinhas][1];
		
		return bigWorld;
	}
}
