import criterioordenacao.*;
import filter.*;
import produto.formatacoes.*;
import produto.*;
import sort.*;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeradorDeRelatorios {

	public static final String ALG_INSERTIONSORT = "insertion";
	public static final String ALG_QUICKSORT = "quick";

	public static final String CRIT_DESC_CRESC = "descricao_c";
	public static final String CRIT_PRECO_CRESC = "preco_c";
	public static final String CRIT_ESTOQUE_CRESC = "estoque_c";
	public static final String CRIT_DESC_DECRESC = "descricao_d";
	public static final String CRIT_PRECO_DECRESC = "preco_d";
	public static final String CRIT_ESTOQUE_DECRESC = "estoque_d";

	public static final String FILTRO_TODOS = "todos";
	public static final String FILTRO_ESTOQUE_MENOR_OU_IQUAL_A = "estoque_menor_igual";
	public static final String FILTRO_CATEGORIA_IGUAL_A = "categoria_igual";

	// operador bit a bit "ou" pode ser usado para combinar mais de  
	// um estilo de formatacao simultaneamente (veja como no main)
	public static final int FORMATO_PADRAO  = 0b0000;
	public static final int FORMATO_NEGRITO = 0b0001;
	public static final int FORMATO_ITALICO = 0b0010;

	private List<Produto> produtos;
	private String algoritmo;
	private String criterio;
	private String filtro;
	private String argFiltro;
	private int format_flags;


	private SortStrategy sortStrategy;
	private CriterioOrdenacaoStrategy criterioOrdenacaoStrategy;
	private FilterStrategy filterStrategy;


	public GeradorDeRelatorios(List<Produto> produtos, String algoritmo, String criterio, String filtro, String argFiltro, int format_flags){

		this.produtos = produtos;

		this.algoritmo = algoritmo;
		this.criterio = criterio;
		this.format_flags = format_flags;
		this.filtro = filtro;
		this.argFiltro = argFiltro;

		if(criterio.equals(CRIT_DESC_CRESC)){
			this.criterioOrdenacaoStrategy = new CriterioDescricaoCrescente();
		}
		else if(criterio.equals(CRIT_PRECO_CRESC)){
			this.criterioOrdenacaoStrategy = new CriterioPrecoCrescente();
		}
		else if(criterio.equals(CRIT_ESTOQUE_CRESC)){
			this.criterioOrdenacaoStrategy = new CriterioEstoqueCrescente();
		}
		else if(criterio.equals(CRIT_ESTOQUE_DECRESC)){
			this.criterioOrdenacaoStrategy = new CriterioEstoqueDecrescente();
		}
		else if(criterio.equals(CRIT_PRECO_DECRESC)){
			this.criterioOrdenacaoStrategy = new CriterioPrecoDecrescente();
		}
		else if(criterio.equals(CRIT_DESC_DECRESC)){
			this.criterioOrdenacaoStrategy = new CriterioDescricaoDecrescente();
		}
		else{

			throw new RuntimeException("Criterio invalido!");
		}


		if(algoritmo.equals(ALG_INSERTIONSORT)){
			this.sortStrategy = new InsertionSort(this.produtos, this.criterioOrdenacaoStrategy);
		}
		else if(algoritmo.equals(ALG_QUICKSORT)){
			this.sortStrategy = new QuickSort(this.produtos, this.criterioOrdenacaoStrategy);
		}
		else {
			throw new RuntimeException("Algoritmo invalido!");
		}

		if(filtro.equals(FILTRO_TODOS)){
			this.filterStrategy = new FiltrarTodos();
		}
		else if(filtro.equals(FILTRO_ESTOQUE_MENOR_OU_IQUAL_A)){
			this.filterStrategy = new FiltrarEstoque();
		}
		else if(filtro.equals(FILTRO_CATEGORIA_IGUAL_A)){
			this.filterStrategy = new FiltrarCategoria();
		}
		else{
			throw new RuntimeException("Filtro invalido!");
		}

	}

	
	
	public void debug(){

		System.out.println("Gerando relatório para array contendo " + produtos.size() + " produto(s)");
		System.out.println("parametro filtro = '" + argFiltro + "'"); 
	}


	public void geraRelatorio(String arquivoSaida) throws IOException {

		debug();

		this.sortStrategy.sort(0, this.produtos.size() - 1);

		PrintWriter out = new PrintWriter(arquivoSaida);

		out.println("<!DOCTYPE html><html>");
		out.println("<head><title>Relatorio de produtos</title></head>");
		out.println("<body>");
		out.println("Relatorio de Produtos:");
		out.println("<ul>");

		int count = 0;

		for(int i = 0; i < produtos.size(); i++){

			Produto p = produtos.get(i);
			boolean selecionado = false;

			if(filterStrategy.filter(p, this.argFiltro)){
				selecionado = true;
			}

			if(selecionado){
				out.print("<li>");

				if((format_flags & FORMATO_ITALICO) > 0){
					p = new FormatacaoItalico(p);
				}

				if((format_flags & FORMATO_NEGRITO) > 0){
					p = new FormatacaoNegrito(p);
				}

				out.print(p.formataParaImpressao());

				out.println("</li>");
				count++;
			}
		}

		out.println("</ul>");
		out.println(count + " produtos listados, de um total de " + produtos.size() + ".");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	public static List<Produto> carregaProdutos(){
		List<Produto> produtos = new ArrayList<>();
		Scanner entrada = null;
		Produto p;
		int id;
		String descricao;
		String categoria;
		int quantidade_estoque;
		double preco;
		boolean negrito;
		boolean italico;
		String cor;

		try {
			entrada = new Scanner(new BufferedReader(new FileReader("produtos.csv")));
			entrada.nextLine();
			entrada.useDelimiter(", |\n");
			while (entrada.hasNext()) {
				id = entrada.nextInt();
				descricao = entrada.next();
				categoria = entrada.next();
				quantidade_estoque = entrada.nextInt();
				preco = Double.parseDouble(entrada.next());
				p = new ProdutoPadrao(id, descricao, categoria, quantidade_estoque, preco);
				negrito = entrada.nextBoolean();
				italico = entrada.nextBoolean();
				cor = entrada.next();
				produtos.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entrada != null) {
				entrada.close();
			}
			return produtos;
		}
	} 

	public static void main(String [] args) {

		if(args.length < 4){

			System.out.println("Uso:");
			System.out.println("\tjava " + GeradorDeRelatorios.class.getName() + " <algoritmo> <critério de ordenação> <critério de filtragem> <parâmetro de filtragem> <opções de formatação>");
			System.out.println("Onde:");
			System.out.println("\talgoritmo: 'quick' ou 'insertion'");
			System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c'");
			System.out.println("\tcriterio de filtragem: 'todos' ou 'estoque_menor_igual' ou 'categoria_igual'"); 
			System.out.println("\tparâmetro de filtragem: argumentos adicionais necessários para a filtragem"); 
			System.out.println("\topções de formatação: 'negrito' e/ou 'italico'");
			System.out.println();
			System.exit(1);
		}

		String opcao_algoritmo = args[0];
		String opcao_criterio_ord = args[1];
		String opcao_criterio_filtro = args[2];
		String opcao_parametro_filtro = args[3];
		
		String [] opcoes_formatacao = new String[2];
		opcoes_formatacao[0] = args.length > 4 ? args[4] : null;
		opcoes_formatacao[1] = args.length > 5 ? args[5] : null;
		int formato = FORMATO_PADRAO;
		
		for(int i = 0; i < opcoes_formatacao.length; i++) {

			String op = opcoes_formatacao[i];
			formato |= (op != null ? op.equals("negrito") ? FORMATO_NEGRITO : (op.equals("italico") ? FORMATO_ITALICO : 0) : 0); 
		}
		
		GeradorDeRelatorios gdr = new GeradorDeRelatorios(	carregaProdutos(), 
									opcao_algoritmo,
									opcao_criterio_ord,
									opcao_criterio_filtro,
									opcao_parametro_filtro,
									formato 
								 );

		try{
			gdr.geraRelatorio("saida.html");
		}
		catch(IOException e){
			
			e.printStackTrace();
		}
	}
}
