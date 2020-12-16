import java.util.Random;

public class CampoMinado {
	
	private char listaBombas[][];
	private char tela[][];
	
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
		
		MostraCampo();
	}

	private void MostraCampo() {
		
		
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
