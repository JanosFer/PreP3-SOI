package view;

import controller.PreptresController;

public class Main {
	public static void main(String[] args) {
		PreptresController prep3 = new PreptresController();
		
		prep3.listaTodasViews(); // Chama a classe de controle que realiza a leitura e apresenta todas as datas e os views de todos os dias.
	}
}
