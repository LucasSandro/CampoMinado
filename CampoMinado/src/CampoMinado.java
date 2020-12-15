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
		
	}

	public void mostraCredito() {
		// TODO Auto-generated method stub
		
	}

	public void limpaHistorico() {
		// TODO Auto-generated method stub
		
	}

	public void exibeHistorico() {
		// TODO Auto-generated method stub
	}
}
