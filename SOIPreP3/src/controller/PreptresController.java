package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class PreptresController {
	public PreptresController() {
		super();
	}
	
	private String os() { // Esse método obtem o nome do Sistema Operacional utilizado.
		String os = System.getProperty("os.name");
		return os;
	}
	
	public void listaTodasViews() {
		String os = os();
		String caminho = "";
		
		if(os.contains("Windows")) {// Validação do Sistema Operacional para definir o diretório em que o arquivo 'wiki.json' se encontra.
			caminho = "C:/TEMP";
		}else if(os.contains("Linux")) {
			caminho = "/tmp";
		}else {
			System.err.println("Sistema Operacional não compatível!");
			return;
		}
		
		File arquivo = new File(caminho, "wiki.json");
		
		if(arquivo.exists() && arquivo.isFile()) {// Valida se o arquivo 'wiki.json' existe.
			StringBuffer datasViews = new StringBuffer();
			
			try { // Lê o arquivo e passa o seu conteúdo para um StringBuffer.
				FileInputStream fluxo = new FileInputStream(arquivo);
				InputStreamReader leitor = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leitor);
				String linha;
				
				while((linha = buffer.readLine())!= null) {
					datasViews.append(linha); // Passa a linha atual do arquivo para o final do StringBuffer.
					datasViews.append("\n");
				}
				
				buffer.close();
				leitor.close();
				fluxo.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
			String[] linhas = datasViews.toString().split("}"); //Divide o conteúdo do StringBuffer em um vetor de String, separando as linhas por '}'.
			
			for(String linha : linhas) {
				
				String[] campos = linha.split(","); // Divide cada campo da linha em um vetor, separando os campos por ','.
				
				int campoViews = 0;
				String ano = "";
				String mes = "";
				String dia = "";
				int views = 0;
				
				for(String campo : campos) { // Valida os campos a serem utilizados.
					
					if(campo.contains("timestamp")) {
						
						int timestamp = campo.indexOf(":"); // Pega o index do ':' que vem em seguida do nome do campo (nesse caso por exemplo: "timestamp":"202109...) para ter uma referência das posições do substring.
						
						// Abaixo, são atribuidos os devidos valores de data nas variáveis para facilitar a manipulação das mesmas na hora de printar.
						ano = campo.substring(timestamp + 2, timestamp + 6);
						mes = campo.substring(timestamp + 6, timestamp + 8);
						dia = campo.substring(timestamp + 8, timestamp + 10);
					}
					
					if(campo.contains("views")) {
						
						campoViews = campo.indexOf(":"); // Pega o index do ':' que vem em seguida do nome do campo para ter uma referência das posições do substring.
						views = Integer.parseInt(campo.substring(campoViews+1));
						
						String data = "Data: " + dia + "/" + mes + "/" + ano + "\n Número de Views do dia: " + views + "\n"; // Padroniza a estrutura de como as datas e a quantidade de views por serão printadas.
						
						System.out.println(data); // Apresenta a data (timestamp (Sem o 00 do final)) e os views de todos os dias.
					}
					
				}
			}
		}else {
			System.err.println("O Arquivo 'wiki.json' não existe!"); // Mensagem de erro para o caso do arquivo 'wiki.json' não existir.
		}
	}
}
