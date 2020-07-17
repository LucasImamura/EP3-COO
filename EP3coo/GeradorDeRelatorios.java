package principal;
import java.io.PrintWriter;

import decorator.Dec;
import decorator.RelFormat;
import strategy.CritDescCresc;
import strategy.CritEstoqueCresc;
import strategy.CritPrecoCresc;
import strategy.UsaSortAlg;

import java.io.IOException;

public class GeradorDeRelatorios implements RelFormat, UsaSortAlg{

	
	public static final int CRIT_DESC_CRESC = 0;
	public static final int CRIT_PRECO_CRESC = 1;
	public static final int CRIT_ESTOQUE_CRESC = 2;
	public static final int CRIT_DESC_DECRESC = 3;
	public static final int CRIT_PRECO_DECRESC = 4;
	public static final int CRIT_ESTOQUE_DECRESC = 5;
	
	public static final int FILTRO_TODOS = 0;
	public static final int FILTRO_ESTOQUE_MENOR_OU_IQUAL_A = 1;
	public static final int FILTRO_CATEGORIA_IGUAL_A = 2;
	public static final int FILTRO_INTERVALO_DE_PRECO = 3;
	public static final int FILTRO_SUBSTRING = 4;
	
	private Produto [] produtos;
	private int algoritmo;
	private int criterio;
	private int filtro;
	private Object argFiltro;
	private int intervaloSuperior;

	public GeradorDeRelatorios(Produto [] produtos, int algoritmo, int criterio, int filtro, Object argFiltro, int intervaloSuperior){

		this.produtos = new Produto[produtos.length];
		
		for(int i = 0; i < produtos.length; i++){
		
			this.produtos[i] = produtos[i];
		}

		this.algoritmo = algoritmo;
		this.criterio = criterio;
		this.filtro = filtro;
		this.argFiltro = argFiltro;
		this.intervaloSuperior = intervaloSuperior;
	}
	
	public void inverteLista(Produto [] produtos) {
		Produto temp;
		for(int i = 0; i < produtos.length/2; i++) {
			temp = produtos[i];
			produtos[i] = produtos[(produtos.length - 1) - i];
			produtos[(produtos.length - 1) - i] = temp;
		}
	}
	
	public void geraRelatorio(String arquivoSaida) throws IOException {

		// ordena(0, produtos.length - 1);
		if(criterio == CRIT_DESC_CRESC) {
			CritDescCresc crit = new CritDescCresc(produtos, algoritmo);
			crit.ordena(0,  produtos.length - 1);
		}
		
		else if(criterio == CRIT_PRECO_CRESC) {
			CritPrecoCresc crit = new CritPrecoCresc(produtos, algoritmo);
			crit.ordena(0,  produtos.length - 1);
		}
		
		else if(criterio == CRIT_ESTOQUE_CRESC) {
			CritEstoqueCresc crit = new CritEstoqueCresc(produtos, algoritmo);
			crit.ordena(0,  produtos.length - 1);
		}
		
		else if(criterio == CRIT_DESC_DECRESC) {
			CritDescCresc crit = new CritDescCresc(produtos, algoritmo);
			crit.ordena(0,  produtos.length - 1);
			inverteLista(produtos);
		}
		
		else if(criterio == CRIT_PRECO_DECRESC) {
			CritPrecoCresc crit = new CritPrecoCresc(produtos, algoritmo);
			crit.ordena(0,  produtos.length - 1);
			inverteLista(produtos);
		}
		
		else if(criterio == CRIT_ESTOQUE_DECRESC) {
			CritEstoqueCresc crit = new CritEstoqueCresc(produtos, algoritmo);
			crit.ordena(0,  produtos.length - 1);
			inverteLista(produtos);
		}

		PrintWriter out = new PrintWriter(arquivoSaida);

		out.println("<!DOCTYPE html><html>");
		out.println("<head><title>Relatorio de produtos</title></head>");
		out.println("<body>");
		out.println("Relatorio de Produtos:");
		out.println("<ul>");

		int count = 0;

		for(int i = 0; i < produtos.length; i++){
			
				Produto p = produtos[i];
			
			
			boolean selecionado = false;

			if(filtro == FILTRO_TODOS){

				selecionado = true;
			}
			else if(filtro == FILTRO_ESTOQUE_MENOR_OU_IQUAL_A){

				if(p.getQtdEstoque() <= (Integer) argFiltro) selecionado = true;	
			}
			else if(filtro == FILTRO_CATEGORIA_IGUAL_A){

				if(p.getCategoria().equalsIgnoreCase((String)argFiltro)) selecionado = true;
			}
			
			else if(filtro == FILTRO_INTERVALO_DE_PRECO) {
				if(p.getPreco() <= intervaloSuperior && p.getPreco() >= (Integer) argFiltro) selecionado = true;
			}
			
			else if(filtro == FILTRO_SUBSTRING) {
				if(p.getDescricao().contains((String)argFiltro)) selecionado = true;
			}
			
			else{
				throw new RuntimeException("Filtro invalido!");			
			}

			if(selecionado){

				
			
				out.print(p.formataParaImpressao());

				
				count++;
			}
		}

		out.println("</ul>");
		out.println(count + " produtos listados, de um total de " + produtos.length + ".");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	public static Produto [] carregaProdutos(){

		return new Produto [] { 

			new Dec( 1, "O Hobbit", "Livros", 2, 34.90, FORMATO_PADRAO, "black"),
			new Dec( 2, "Notebook Core i7", "Informatica", 5, 1999.90,FORMATO_PADRAO | FORMATO_NEGRITO, "blue"),
			new Dec( 3, "Resident Evil 4", "Games", 7, 79.90,FORMATO_PADRAO |  FORMATO_ITALICO, "red"),
			new Dec( 4, "iPhone", "Telefonia", 8, 4999.90,FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO,"purple"),
			new Dec( 5, "Calculo I", "Livros", 20, 55.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "black"),
			new Dec( 6, "Power Glove", "Games", 3, 499.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "yellow"),
			new Dec( 7, "Microsoft HoloLens", "Informatica", 1, 19900.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "red"),
			new Dec( 8, "OpenGL Programming Guide", "Livros", 4, 89.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "blue"),
			new Dec( 9, "Vectrex", "Games", 1, 799.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "yellow"),
			new Dec(10, "Carregador iPhone", "Telefonia", 15, 499.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "red"),
			new Dec(11, "Introduction to Algorithms", "Livros", 7, 315.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "black"),
			new Dec(12, "Daytona USA (Arcade)", "Games", 1, 12000.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "yellow"),
			new Dec(13, "Neuromancer", "Livros", 5, 45.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "blue"),
			new Dec(14, "Nokia 3100", "Telefonia", 4, 249.99, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "black"),
			new Dec(15, "Oculus Rift", "Games", 1, 3600.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "black"),
			new Dec(16, "Trackball Logitech", "Informatica", 1, 250.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "black"),
			new Dec(17, "After Burner II (Arcade)", "Games", 2, 8900.0, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "blue"),
			new Dec(18, "Assembly for Dummies", "Livros", 30, 129.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "red"),
			new Dec(19, "iPhone (usado)", "Telefonia", 3, 3999.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "yellow"),
			new Dec(20, "Game Programming Patterns", "Livros", 1, 299.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "black"),
			new Dec(21, "Playstation 2", "Games", 10, 499.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "black"),
			new Dec(22, "Carregador Nokia", "Telefonia", 14, 89.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "black"),
			new Dec(23, "Placa Aceleradora Voodoo 2", "Informatica", 4, 189.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "black"),
			new Dec(24, "Stunts", "Games", 3, 19.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "yellow"),
			new Dec(25, "Carregador Generico", "Telefonia", 9, 30.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "red"),
			new Dec(26, "Monitor VGA 14 polegadas", "Informatica", 2, 199.90, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "blue"),
			new Dec(27, "Nokia N-Gage", "Telefonia", 9, 699.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "red"),
			new Dec(28, "Disquetes Maxell 5.25 polegadas (caixa com 10 unidades)", "Informatica", 23, 49.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "blue"),
			new Dec(29, "Alone in The Dark", "Games", 11, 59.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "red"),
			new Dec(30, "The Art of Computer Programming Vol. 1", "Livros", 3, 240.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "pink"),
			new Dec(31, "The Art of Computer Programming Vol. 2", "Livros", 2, 200.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "purple"),
			new Dec(32, "The Art of Computer Programming Vol. 3", "Livros", 4, 270.00, FORMATO_PADRAO | FORMATO_NEGRITO |  FORMATO_ITALICO, "blue")
		};
	} 

	public static void main(String [] args) {
	
		Produto [] produtos = carregaProdutos();

		GeradorDeRelatorios gdr;

		gdr = new GeradorDeRelatorios(	produtos, ALG_INSERTIONSORT, CRIT_PRECO_CRESC, 
						//FILTRO_ESTOQUE_MENOR_OU_IQUAL_A, 100);
				FILTRO_INTERVALO_DE_PRECO, 1500, 2000);
		
		try{
			gdr.geraRelatorio("saida.html");
			System.out.println("aaa");
		}
		catch(IOException e){
			
			e.printStackTrace();
		}
	}
}
