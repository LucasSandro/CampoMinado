import java.util.Scanner;

public class SistemaCampoMinado {
	private CampoMinado campoMinado;

	private Scanner teclado;

	public SistemaCampoMinado() {
		teclado = new Scanner(System.in);
	}

	public void iniciarSistema() {
		int opcao;
		
		do {
			opcao = MostraMenu();
		} while (opcao != 0);		
	}

	private int MostraMenu() {
		
		System.out.println("Menu Principal");
		System.out.println("1 - Novo Jogo");
		System.out.println("2 - Exibir Histórico de Vitórias");
		System.out.println("3 - Limpar Históricos");
		System.out.println("4 - Créditos");
		System.out.println("0 - Sair");
		System.out.print(">>> ");
		return teclado.nextInt();
	}
}
