import java.util.Random;
import java.util.Scanner;

public class CampoMinado {
	
	private char listaBombas[][];
	private char tela[][];
	private boolean terminouJogo;
	
	public CampoMinado() {
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
		
			InformaCampo();
		} while (!terminouJogo);
	}

	private void InformaCampo() {
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("Digite a linha: ");
		int linha = teclado.nextInt();
		
		System.out.println("Digite a coluna: ");
		int coluna = teclado.nextInt();
		
		if (ValidaCampos(linha, coluna)) {
			switch (AbrirCampo(linha, coluna, 'U')) {
			
			}
		}
	}
	
	private boolean ValidaCampos(int linha, int coluna) {
		return true;
	}

	private char AbrirCampo(int linha, int coluna, char metodo) {
		
		
		
		if (metodo == 'U' && tela[linha][coluna] == '*') {
			terminouJogo = true;
			//Chamar novamente para limpar o campo
			return 'D';
		}
		terminouJogo = true;
		return 'V';
	}

	private void PopulaTela() {
		for (int idxLinha = 0; idxLinha < tela.length; ++idxLinha) {
			for (int idxColuna = 0; idxColuna < tela[idxLinha].length; ++idxColuna)
				tela[idxLinha][idxColuna] = '#';
		}
	}

	private void PopulaBombas(int dificuldade) {
		int qtdBombas = 0;
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
