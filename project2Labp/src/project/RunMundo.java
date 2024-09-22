package project;

import java.io.IOException;

/**
 * Executa testes quase unitários informais aos métodos principais da classe Mundo,
 * que concretiza o Jogo da Vida.
 * 
 * @author csl
 */
public class RunMundo {

	public static void main(String[] args) throws IOException {
		int[] regra = {3, 2, 3};   // regra usual do Jogo da Vida

		// ---------------------------------------------------------
		// testando celulaVive com a regra {3, 2, 3}

		// sobrevive?

		int[][] sub1 = { {0, 0, 0},
		                 {0, 1, 0},
		                 {0, 1, 0} }; // não sobrevive
		if (Mundo.celulaVive(sub1, regra))
		    System.out.println("teste celulaVive sub1 falhado");

		int[][] sub2 = { {0, 0, 0},
		                 {0, 1, 0},
		                 {1, 0, 1} }; // sobrevive
		if (!Mundo.celulaVive(sub2, regra))
		    System.out.println("teste celulaVive sub2 falhado");

		int[][] sub3 = { {0, 1, 0},
		                 {0, 1, 1},
		                 {1, 0, 0} }; // sobrevive
		if (!Mundo.celulaVive(sub3, regra))
		    System.out.println("teste celulaVive sub3 falhado");

		int[][] sub4 = { {0, 1, 1},
		                 {1, 1, 0},
		                 {1, 0, 0} }; // não sobrevive
		if (Mundo.celulaVive(sub4, regra))
		    System.out.println("teste celulaVive sub4 falhado");

		// renasce?

		int[][] sub5 = { {0, 0, 1},
		                 {0, 0, 0},
		                 {0, 1, 0} }; // não renasce
		if (Mundo.celulaVive(sub5, regra))
		    System.out.println("teste celulaVive sub5 falhado");

		int[][] sub6 = { {0, 1, 0},
		                 {0, 0, 0},
		                 {1, 0, 1} }; // renasce
		if (!Mundo.celulaVive(sub6, regra))
		    System.out.println("teste celulaVive sub6 falhado");

		int[][] sub7 = { {0, 1, 0},
		                 {1, 0, 0},
		                 {1, 0, 1} }; // não renasce
		if (Mundo.celulaVive(sub7, regra))
		    System.out.println("teste celulaVive sub7 falhado");
		  
		// ---------------------------------------------------------
		// testando getters, incluindo teste básico a valorDaCelula, após invocação de construtor

		// matriz para inicializar mundo com tamanho 3 x 5
		int matrizPequena[][] = { {1, 1, 1, 1, 1},
		                          {1, 1, 1, 1, 1},
		                          {1, 1, 1, 1, 1} };
		  
		Mundo mundoPequeno = new Mundo(matrizPequena);
		  
		if (mundoPequeno.getNumLinhas() != 3)
	        System.out.println("teste numLinhas mundoPequeno falhado");
		if (mundoPequeno.getNumColunas() != 5)
			System.out.println("teste numColunas mundoPequeno falhado");
		for (int linha = 0; linha < 3; linha++)
			for (int coluna = 0; coluna < 5; coluna++)
			    if (mundoPequeno.valorDaCelula(linha, coluna) != 1)    // pouco abrangente como teste
			        System.out.println("teste valorDaCelula falhado"); // mas o problema pode estar no construtor!
		  
		  
		// ---------------------------------------------------------
		// testando zeraMundo, e testando novamente valorDaCelula
		  
		mundoPequeno.zeraMundo();
		for (int linha = 0; linha < 3; linha++)
		    for (int coluna = 0; coluna < 5; coluna++)
		        if (mundoPequeno.valorDaCelula(linha, coluna) != 0)
		    	    System.out.println("teste zeraMundo falhado");    // mas o problema pode estar no valorDaCelula!
		
		// ---------------------------------------------------------
		// criando um novo mundo para os testes seguintes: mundoQuadrado

		// matriz para inicializar mundo com tamanho  11 x 11
		int[][] cruzLargura7 =
		    { {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} };
		
		Mundo mundoQuadrado = new Mundo(cruzLargura7);

	    // ---------------------------------------------------------
	    // testando mostraMundo

	    System.out.println("\ntestando mostraMundo");
	    mundoQuadrado.mostraMundo();
	    
	    // ---------------------------------------------------------
	    // testando escreveMundo

	    System.out.println("\ntestando escreveMundo");
	    mundoQuadrado.escreveMundo("./mundos/mundo_escrito.txt");	    
	    
	    // ---------------------------------------------------------
	    // testando a leitura do mundo a partir de um ficheiro
	    
	    System.out.println("\ntestando a leitura do mundo a partir de ficheiro");

	    // novo mundo a inicializar a partir do ficheiro criado anteriormente por escreveMundo
	    Mundo novoMundo = new Mundo("./mundos/mundo_escrito.txt");

	    // testando o tamanho do mundo lido
	    if (novoMundo.getNumLinhas() != 11 || novoMundo.getNumColunas() != 11)
	    	System.out.println("teste de leitura de ficheiro falhado no tamanho do mundo lido");

	    // confirmando mundoLido correcto através de visualização
	    novoMundo.mostraMundo();
	    
	    System.out.println("\ntestando novamente a leitura do mundo a partir de ficheiro");
	    
	    // este novo ficheiro, usado para inicializar um novo mundo, tem a vantagem de estar criado
	    // previamente, não dependendo de eventuais problemas no ficheiro que foi gerado por escreveMundo
	    //
	    // optou-se por *não* terminar este ficheiro com uma mudança de linha
	    Mundo novoMundo2 = new Mundo("./mundos/mundo_escrito2.txt");

	    // testando o tamanho de mundoLido2
	    if (novoMundo2.getNumLinhas() != 11 || novoMundo2.getNumColunas() != 11)
	    	System.out.println("teste de leitura de ficheiro falhado no tamanho do mundo lido");

	    // confirmando mundoLido2 correcto através de visualização
	    novoMundo2.mostraMundo();
	    
	    // ---------------------------------------------------------
	    // testando iteraMundo
	    
	    System.out.println("\ntestando iteraMundo com mostraMundo");
	    System.out.println("\n1 iteracao de cada vez; 5 iteracoes no total");
	    
	    for (int iteracoes = 1; iteracoes <= 5; iteracoes++) {
	        mundoQuadrado.iteraMundo(regra);
	        System.out.println();
	        mundoQuadrado.mostraMundo();
	    }
	    	    
	    // ---------------------------------------------------------
	    // testando iteraMundoNgeracoes
	    
	    System.out.println("\ntestando iteraMundoNgeracoes com mostraMundo");
	    System.out.println("\n5 iteracoes");
	    
	    // reinicializar mundoQuadrado
	    mundoQuadrado = new Mundo(cruzLargura7);
	      
	    mundoQuadrado.iteraMundoNgeracoes(5, regra);
	    System.out.println();
	    mundoQuadrado.mostraMundo();
	    
	    
	    // ---------------------------------------------------------
	    // testando toString
	    System.out.println("\nTestando toString");
	    System.out.println(mundoQuadrado);
	}

}
