import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class CampoMinado {
	
	private char listaBombas[][];
	private char tela[][];
	private boolean terminouJogo;
	private int qtdBombas;
	private int qtdCamposAbertos;
	private ArrayList<int[]> vetorPosicoesParaVerificar;
	
	public CampoMinado() {
		vetorPosicoesParaVerificar = new ArrayList();
	}

	public void iniciaPartida(int dificuldade) {
		switch (dificuldade) {
			case 1: {
				listaBombas = new char[10][8];
				tela = new char[10][8];
				break;
			}
			
			case 2: {
				listaBombas = new char[18][14];
				tela = new char[18][14];
				break;
			}
			
			case 3: {
				listaBombas = new char[24][20];
				tela = new char[24][20];
				break;
			}
		}
		
		PopulaBombas(dificuldade);
		PopulaTela();
		terminouJogo = false;
		MostraCampo();
	}

	private void MostraCampo() {
		do {
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
			
			try {
				InformaCampo();
			} catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Posição inválida!");
			}
		} while (!terminouJogo);
	}

	private void InformaCampo() {
		Scanner teclado = new Scanner(System.in);
		int linha = 0;
		int coluna = 0;
		
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
					System.out.println("Você perdeu!");
					break;
				}
			
				case 'V': {
					System.out.println("Você ganhou!");
					break;
				}
			
			}
		}
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
			//Chamar novamente para limpar o campo
			return 'D';
		}

		if (qtdCamposAbertos == (tela.length * tela[0].length - qtdBombas)) {
			terminouJogo = true;
			return 'V';
		}
		
		tela[linha][coluna] = VerificaArredores(linha, coluna, 'U');
		++qtdCamposAbertos;
		
		do {
			linha = vetorPosicoesParaVerificar.get(0)[0];
			coluna = vetorPosicoesParaVerificar.get(0)[1];
			tela[linha][coluna] = VerificaArredores(linha, coluna, 'C');
			++qtdCamposAbertos;
		} while (!vetorPosicoesParaVerificar.isEmpty());
		return ' ';
	}

	private char VerificaArredores(int linha, int coluna, char metodo) {
		int qtdBombasEncontradas = 0;
		
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
					vetorPosicoesParaVerificar.add(new int[2]);
					vetorPosicoesParaVerificar.get(vetorPosicoesParaVerificar.size() - 1)[0] = linhaVerificacao;
					vetorPosicoesParaVerificar.get(vetorPosicoesParaVerificar.size() - 1)[1] = idx;
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
					vetorPosicoesParaVerificar.add(new int[2]);
					vetorPosicoesParaVerificar.get(vetorPosicoesParaVerificar.size() - 1)[0] = linhaVerificacao;
					vetorPosicoesParaVerificar.get(vetorPosicoesParaVerificar.size() - 1)[1] = idx;
				}
			}
			//Abaixo
			//  #
			//0 0 0
		}
		
		if (coluna != 0) {
			
			int colunaVerificacao = coluna - 1;
			
			for (int idx = linha - 1; idx < linha + 2; ++idx)
			{
				if (idx == -1)
					continue;
				
				if (idx == tela.length)
					continue;
				
				if (listaBombas[idx][colunaVerificacao] == '*')
					++qtdBombasEncontradas;
				else if (tela[idx][colunaVerificacao] == '#') {
					vetorPosicoesParaVerificar.add(new int[2]);
					vetorPosicoesParaVerificar.get(vetorPosicoesParaVerificar.size() - 1)[0] = idx;
					vetorPosicoesParaVerificar.get(vetorPosicoesParaVerificar.size() - 1)[1] = colunaVerificacao;
				}
			}
			
			//Ao lado <-
			//0
			//0 #
			//0
		}
		
		if (coluna < tela[0].length - 1) {
			
			int colunaVerificacao = coluna + 1;
			
			for (int idx = linha - 1; idx < linha + 2; ++idx)
			{
				if (idx == -1)
					continue;
				
				if (idx == tela.length)
					continue;
				
				if (listaBombas[idx][colunaVerificacao] == '*')
					++qtdBombasEncontradas;
				else if (tela[idx][colunaVerificacao] == '#') {
					vetorPosicoesParaVerificar.add(new int[2]);
					vetorPosicoesParaVerificar.get(vetorPosicoesParaVerificar.size() - 1)[0] = idx;
					vetorPosicoesParaVerificar.get(vetorPosicoesParaVerificar.size() - 1)[1] = colunaVerificacao;
				}
			}
			//Ao lado ->
			//   0
			// # 0
			//   0
		}
		
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

	public void mostraCredito() {
		System.out.println("Desenvolvido por Lucas Sandro e Vinicius");
	}

	public void limpaHistorico() {
		// TODO Auto-generated method stub
	}

	public void exibeHistorico() {
		// TODO Auto-generated method stub
	}
}
