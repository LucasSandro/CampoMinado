import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CampoMinado {
	
	private char listaBombas[][];
	private char tela[][];
	private boolean terminouJogo;
	private int qtdBombas;
	private int qtdCamposAbertos;
	private ArrayList<int[]> vetorPosicoesParaVerificar;
	private ArrayList<String> historicos;
	private long inicioCronometro;
	private String dificuldadeSelecionada;
	private Scanner teclado;
	
	public CampoMinado() {
		vetorPosicoesParaVerificar = new ArrayList();
		historicos = new ArrayList();
		qtdCamposAbertos = 0;
		teclado = new Scanner(System.in);
	}

	public void iniciaPartida(int dificuldade) {
		switch (dificuldade) {
			case 1: {
				listaBombas = new char[10][8];
				tela = new char[10][8];
				dificuldadeSelecionada = "Nível Fácil  "; 
				break;
			}
			
			case 2: {
				listaBombas = new char[18][14];
				tela = new char[18][14];
				dificuldadeSelecionada = "Nível Médio ";
				break;
			}
			
			case 3: {
				listaBombas = new char[24][20];
				tela = new char[24][20];
				dificuldadeSelecionada = "Nível Difícil";
				break;
			}
		}
		
		inicioCronometro = System.currentTimeMillis();
		PopulaBombas(dificuldade);
		PopulaTela();
		terminouJogo = false;
		MostraCampo();
	}

	private void MostraCampo() {
		do {
			MostraTela();
			
			try {
				InformaCampo();
			} catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Posição inválida!");
			}
		} while (!terminouJogo);
	}
	
	private void MostraTela() {
		for (int qtdLinha = 0; qtdLinha < 2; ++qtdLinha) {
			System.out.print("    ");
			for (int idx = 0; idx < tela[0].length; ++idx) {
				if (qtdLinha == 0) {
					System.out.print(idx + " ");
				
					if (idx < 9)
						System.out.print(" ");
					} else {
						System.out.print("---");
					}
			}
		
			System.out.println();
		}
	
		for (int idxLinha = 0; idxLinha < tela.length; ++idxLinha) {
			for (int idxColuna = 0; idxColuna < tela[idxLinha].length; ++idxColuna) {
				if (idxColuna == 0) {
					System.out.print(idxLinha);
				
					if (idxLinha < 10)
						System.out.print(" ");
				
					System.out.print("| ");
				}
				
				System.out.print(tela[idxLinha][idxColuna] + "  ");
			}
		
			System.out.println();
		}
	}

	private void InformaCampo() {
		
		int linha = -1;
		int coluna = -1;
		
		try {
			System.out.println("Digite a linha: ");
			linha = teclado.nextInt();
		
			System.out.println("Digite a coluna: ");
			coluna = teclado.nextInt();
		} catch(InputMismatchException e) {
			System.out.println("Informe somente números!");
		}
		
		if (ValidaCampos(linha, coluna)) {
			switch (AbrirCampo(linha, coluna, 'U')) {
				case 'D':{
					MostraTela();
					System.out.println("Você perdeu!");
					historicos.add(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
							+ " | " + dificuldadeSelecionada + " | " + "Duração " + CalculaTempoJogo() + " | " + "Perdeu");
					
					MostraOpcoes();
					break;
				}
			
				case 'V': {
					MostraTela();
					System.out.println("Você ganhou!");
					historicos.add(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
							+ " | " + dificuldadeSelecionada + " | " + "Duração " + CalculaTempoJogo() + " | " + "Ganhou");
					
					MostraOpcoes();
					break;
				}
			
			}
		}
	}
	
	private void MostraOpcoes() {
		int opcao = 0;
		
		System.out.println("\n1 - Voltar para o menu principal");
		System.out.println("0 - Sair");
		opcao = teclado.nextInt();
		
		if (opcao == 0)
			System.exit(0);
	}

	private boolean ValidaCampos(int linha, int coluna) {
		if (tela[linha][coluna] != '#') {
			System.out.println("Posição já foi aberta!");
			return false;
		}
		
		return true;
	}

	private char AbrirCampo(int linha, int coluna, char metodo) {
		
		if (metodo == 'U' && listaBombas[linha][coluna] == '*') {
			terminouJogo = true;
			VerificaArredores(linha, coluna, 'D');
			AbreTudo();
			return 'D';
		}

		tela[linha][coluna] = VerificaArredores(linha, coluna, 'U');

		
		while (!vetorPosicoesParaVerificar.isEmpty()) {
			linha = vetorPosicoesParaVerificar.get(0)[0];
			coluna = vetorPosicoesParaVerificar.get(0)[1];
			tela[linha][coluna] = VerificaArredores(linha, coluna, 'C');
		}

		if (ContaCamposNaoInformados() == qtdBombas) {
			terminouJogo = true;
			AbreTudo();
			return 'V';
		}
		
		return ' ';
	}
	
	private void AbreTudo() {
		for (int idxLinha = 0; idxLinha < tela.length; ++idxLinha) {
			for (int idxColuna = 0; idxColuna < tela[idxLinha].length; ++idxColuna) {
				if (listaBombas[idxLinha][idxColuna] == '*')
					tela[idxLinha][idxColuna] = '*';
				else if (tela[idxLinha][idxColuna] == '#')
					tela[idxLinha][idxColuna] = VerificaArredores(idxLinha, idxColuna, 'D');
					
			}
		}
		
	}

	private int ContaCamposNaoInformados()	{
		int qtdCamposNaoInformados = 0;
		
		for (int idxLinha = 0; idxLinha < tela.length; idxLinha++) {
			for (int idxColuna = 0; idxColuna < listaBombas[idxLinha].length; idxColuna++) {
				if (tela[idxLinha][idxColuna] == '#')
					++qtdCamposNaoInformados;
			}
		}
		
		return qtdCamposNaoInformados;
	}

	private char VerificaArredores(int linha, int coluna, char metodo) {
		int qtdBombasEncontradas = 0;
		ArrayList<int[]> posicoesParaVerificar = new ArrayList();
		
		if (linha != 0) {
			int linhaVerificacao = linha - 1;
			
			for (int idx = coluna - 1; idx < coluna + 2; ++idx)
			{
				if (idx == -1)
					continue;
				
				if (idx == tela[0].length)
					continue;
				
				if (listaBombas[linhaVerificacao][idx] == '*')
					++qtdBombasEncontradas;
				else if (tela[linhaVerificacao][idx] == '#'){
					posicoesParaVerificar.add(new int[2]);
					posicoesParaVerificar.get(posicoesParaVerificar.size() - 1)[0] = linhaVerificacao;
					posicoesParaVerificar.get(posicoesParaVerificar.size() - 1)[1] = idx;
				}
			}
			
			//Acima
			// 0 0 0
			//	 #
		}
		
		if (linha < tela.length - 1) {
			int linhaVerificacao = linha + 1;
			
			for (int idx = coluna - 1; idx < coluna + 2; ++idx)
			{
				if (idx == -1)
					continue;
				
				if (idx == tela[0].length)
					continue;
				
				if (listaBombas[linhaVerificacao][idx] == '*')
					++qtdBombasEncontradas;
				else if (tela[linhaVerificacao][idx] == '#') {
					posicoesParaVerificar.add(new int[2]);
					posicoesParaVerificar.get(posicoesParaVerificar.size() - 1)[0] = linhaVerificacao;
					posicoesParaVerificar.get(posicoesParaVerificar.size() - 1)[1] = idx;
				}
			}
			//Abaixo
			//  #
			//0 0 0
		}
		
		if (coluna != 0) {
			
			int colunaVerificacao = coluna - 1;
				
			if (listaBombas[linha][colunaVerificacao] == '*')
				++qtdBombasEncontradas;
			else if (tela[linha][colunaVerificacao] == '#') {
				posicoesParaVerificar.add(new int[2]);
				posicoesParaVerificar.get(posicoesParaVerificar.size() - 1)[0] = linha;
				posicoesParaVerificar.get(posicoesParaVerificar.size() - 1)[1] = colunaVerificacao;
			}
			
			//Ao lado <-
			//0
			//0 #
			//0
		}
		
		if (coluna < tela[0].length - 1) {
			
			int colunaVerificacao = coluna + 1;
			
			if (listaBombas[linha][colunaVerificacao] == '*')
				++qtdBombasEncontradas;
			else if (tela[linha][colunaVerificacao] == '#') {
				posicoesParaVerificar.add(new int[2]);
				posicoesParaVerificar.get(posicoesParaVerificar.size() - 1)[0] = linha;
				posicoesParaVerificar.get(posicoesParaVerificar.size() - 1)[1] = colunaVerificacao;
			}
			//Ao lado ->
			//   0
			// # 0
			//   0
		}
		
		if (qtdBombasEncontradas == 0 && metodo != 'D') 
			vetorPosicoesParaVerificar.addAll(posicoesParaVerificar);
		
		if (metodo == 'C')
			vetorPosicoesParaVerificar.remove(0);
		
		return (char) (qtdBombasEncontradas + 48);
	}

	private void PopulaTela() {
		for (int idxLinha = 0; idxLinha < tela.length; ++idxLinha) {
			for (int idxColuna = 0; idxColuna < tela[idxLinha].length; ++idxColuna)
				tela[idxLinha][idxColuna] = '#';
		}
	}

	private void PopulaBombas(int dificuldade) {
		qtdBombas = 0;
		switch (dificuldade) {
			case 1: {
				qtdBombas = 10;
				break;
			}
	
			case 2: {
				qtdBombas = 40;
				break;
			}
	
			case 3: {
				qtdBombas = 99;
				break;
			}	
		}
		
		int linha = 0;
		int coluna = 0;
		
		Random random = new Random();
		
		for (int idx = 0; idx < qtdBombas; ++idx) {
			linha = random.nextInt(listaBombas.length);
			coluna = random.nextInt(listaBombas[0].length);
			
			if (listaBombas[linha][coluna] == '*') {
				--idx;
				continue;
			}
			
			listaBombas[linha][coluna] = '*';
		}
	}
	
	private String CalculaTempoJogo() 
	{
		long total = System.currentTimeMillis() - inicioCronometro;
		String duracao = String.format("%02d:%02d:%02d", total/3600000, (total/60000) % 60, (total/1000) % 60);
		
		return duracao;
	}

	public void mostraCredito() {
		System.out.println("Desenvolvido por Lucas Sandro e Vinicius");
	}

	public void limpaHistorico() {
		historicos.clear();
	}
	
	public void exibeHistorico() {
		
		if(historicos.size() > 0) {
			for (String historico : historicos) {
				System.out.println(historico);
			}
		}else {
			System.out.println("Histórico vazio.");
		}
	}
}
