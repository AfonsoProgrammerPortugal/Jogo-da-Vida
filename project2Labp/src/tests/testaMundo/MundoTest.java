package testaMundo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import project.Mundo;

/** 
 * Essencialmente reproduz os testes em RunMundo.java
 * 
 * @author csl
 */
class MundoTest {
	
	int[] regra = {3, 2, 3};   // regra usual do Jogo da Vida

	@Test
	void test_celulaVive() {
		int[][] sub1 = { {0, 0, 0},
						 {0, 1, 0},
						 {0, 1, 0} }; // não sobrevive
		assertFalse(Mundo.celulaVive(sub1, regra));
   
    	int[][] sub2 = { {0, 0, 0},
    					 {0, 1, 0},
    					 {1, 0, 1} }; // sobrevive
    	assertTrue(Mundo.celulaVive(sub2, regra));
  
    	int[][] sub3 = { {0, 1, 0},
    					 {0, 1, 1},
    					 {1, 0, 0} }; // sobrevive
    	assertTrue(Mundo.celulaVive(sub3, regra));
   
    	int[][] sub4 = { {0, 1, 1},
    					 {1, 1, 0},
    					 {1, 0, 0} }; // não sobrevive
    	assertFalse(Mundo.celulaVive(sub4, regra));

    	// renasce?

    	int[][] sub5 = { {0, 0, 1},
    				   {0, 0, 0},
    				   {0, 1, 0} }; // não renasce
    	assertFalse(Mundo.celulaVive(sub5, regra));
   
    	int[][] sub6 = { {0, 1, 0},
    					 {0, 0, 0},
    					 {1, 0, 1} }; // renasce
    	assertTrue(Mundo.celulaVive(sub6, regra));
   
    	int[][] sub7 = { {0, 1, 0},
    					 {1, 0, 0},
    					 {1, 0, 1} }; // não renasce
    	assertFalse(Mundo.celulaVive(sub7, regra)); 
	}
	
	@Test
	void test_getters() {
		int matrizPequena[][] = { {1, 0, 1, 1},
								  {0, 1, 0, 1} };

		Mundo mundoPequeno = new Mundo(matrizPequena);
		
		assertEquals(mundoPequeno.getNumLinhas(), 2);
		assertEquals(mundoPequeno.getNumColunas(), 4);
		
		assertEquals(mundoPequeno.valorDaCelula(0, 1), 0);
		assertEquals(mundoPequeno.valorDaCelula(0, 2), 1);
		assertEquals(mundoPequeno.valorDaCelula(1, 0), 0);
		assertEquals(mundoPequeno.valorDaCelula(1, 3), 1);
	}
	
	@Test
	void test_aliasing_no_construtor() {
		int matrizPequena[][] = { {1, 0, 1, 1},
								  {0, 1, 0, 1} };

		Mundo mundoPequeno = new Mundo(matrizPequena);
		
		mundoPequeno.atribuiValorCelula(1, 2, 1);
		
		assertEquals(mundoPequeno.valorDaCelula(1, 2), 1);
		assertEquals(matrizPequena[1][2], 0);
	}

	@Test
	void test_zeraMundo() {
		int matrizPequena[][] = { {1, 0, 1, 1},
								  {0, 1, 0, 1},
								  {0, 1, 1, 1} };
		
		int matrizPequenaZeros[][] = { {0, 0, 0, 0},
									   {0, 0, 0, 0},
									   {0, 0, 0, 0} };

		Mundo mundoPequeno = new Mundo(matrizPequena);
		mundoPequeno.zeraMundo();
		Mundo mundoZero = new Mundo(matrizPequenaZeros);
		
		assertEquals(mundoPequeno, mundoZero);
	}
	
	@Test
	void test_ler_de_ficheiro() throws IOException {
		int[][] cruzAlterada =
		    { {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} };
		
		Mundo mundoCruzAlterada = new Mundo(cruzAlterada);
		Mundo mundoCruzAlteradaFich = new Mundo("./mundos/cruz_alterada.txt");
		
		assertEquals(mundoCruzAlterada, mundoCruzAlteradaFich);
		assertEquals(mundoCruzAlteradaFich.getNumLinhas(), 10);
		assertEquals(mundoCruzAlteradaFich.getNumColunas(), 11);
	}
	
	@Test
	void test_iteraMundo() {
		int[][] umPatinadorInicial =
		    { {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
		      {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} };
		
		int[][] umPatinador24iteracoes =
		    { {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
		      {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} };
		
		Mundo mundoUmPatinador = new Mundo(umPatinadorInicial);
		Mundo mundoUmPatinador24iteracoes = new Mundo(umPatinador24iteracoes);
		
	    for (int iteracoes = 1; iteracoes <= 24; iteracoes++) {
	    	mundoUmPatinador.iteraMundo(regra);
	    }
		
		assertEquals(mundoUmPatinador, mundoUmPatinador24iteracoes);
	}
	
	@Test
	void test_iteraMundoNgeracoes() {
		int[][] umPatinadorInicial =
		    { {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
		      {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} };
		
		int[][] umPatinador24iteracoes =
		    { {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
		      {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} };
		
		Mundo mundoUmPatinador = new Mundo(umPatinadorInicial);
		Mundo mundoUmPatinador24iteracoes = new Mundo(umPatinador24iteracoes);
	
	    mundoUmPatinador.iteraMundoNgeracoes(24, regra);
		
		assertEquals(mundoUmPatinador, mundoUmPatinador24iteracoes);
	}
	
	@Test
	void test_toString() {
		int[][] matrizMixed =
		    { {0, 0, 0, 1, 0, 0},
		      {0, 1, 1, 1, 0, 0},
		      {0, 0, 0, 0, 0, 1} };
			
		Mundo mundoMixed = new Mundo(matrizMixed);
		
		String esperada = ". . . X . . \n. X X X . . \n. . . . . X \n";
	   
		assertEquals(mundoMixed.toString(), esperada);
	}
	
}
