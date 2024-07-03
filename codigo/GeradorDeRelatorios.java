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
	public static final String FILTRO_PRECO_ENTRE = "preco_entre";
	public static final String FILTRO_DESCRICAO_SUBSTRING = "descricao_substring";

	private List<Produto> produtos;
	private String argFiltro;


	private SortStrategy sortStrategy;
	private FilterStrategy filterStrategy;


	public GeradorDeRelatorios(List<Produto> produtos, String algoritmo, String criterio, String filtro, String argFiltro){

		this.produtos = produtos;

		this.argFiltro = argFiltro;

		CriterioOrdenacaoStrategy criterioOrdenacaoStrategy = null;

		if(criterio.equals(CRIT_DESC_CRESC)){
			criterioOrdenacaoStrategy = new CriterioDescricaoCrescente();
		}
		else if(criterio.equals(CRIT_PRECO_CRESC)){
			criterioOrdenacaoStrategy = new CriterioPrecoCrescente();
		}
		else if(criterio.equals(CRIT_ESTOQUE_CRESC)){
			criterioOrdenacaoStrategy = new CriterioEstoqueCrescente();
		}
		else if(criterio.equals(CRIT_ESTOQUE_DECRESC)){
			criterioOrdenacaoStrategy = new CriterioEstoqueDecrescente();
		}
		else if(criterio.equals(CRIT_PRECO_DECRESC)){
			criterioOrdenacaoStrategy = new CriterioPrecoDecrescente();
		}
		else if(criterio.equals(CRIT_DESC_DECRESC)){
			criterioOrdenacaoStrategy = new CriterioDescricaoDecrescente();
		}
		else{

			throw new RuntimeException("Criterio invalido!");
		}


		if(algoritmo.equals(ALG_INSERTIONSORT)){
			this.sortStrategy = new InsertionSort(this.produtos, criterioOrdenacaoStrategy);
		}
		else if(algoritmo.equals(ALG_QUICKSORT)){
			this.sortStrategy = new QuickSort(this.produtos, criterioOrdenacaoStrategy);
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
		else if(filtro.equals(FILTRO_PRECO_ENTRE)){
			this.filterStrategy = new FiltrarPreco();
		}
		else if(filtro.equals(FILTRO_DESCRICAO_SUBSTRING)){
			this.filterStrategy = new FiltrarDescricao();
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

		for(Produto p: produtos){
			if(this.filterStrategy.filter(p, this.argFiltro)){
				out.print("<li>");

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

	private static Produto geraProduto(String linha) {
		Produto p;
		int id;
		String descricao;
		String categoria;
		int quantidade_estoque;
		double preco;
		boolean negrito;
		boolean italico;
		String cor;

		Scanner entrada = new Scanner(linha);
		entrada.useDelimiter(", |\n");
		
		id = entrada.nextInt();
		descricao = entrada.next();
		categoria = entrada.next();
		quantidade_estoque = entrada.nextInt();
		preco = Double.parseDouble(entrada.next());
		p = new ProdutoPadrao(id, descricao, categoria, quantidade_estoque, preco);
		negrito = entrada.nextBoolean();
		if (negrito) {
			p = new NegritoDecorator(p);
		}
		italico = entrada.nextBoolean();
		if (italico) {
			p = new ItalicoDecorator(p);
		}
		cor = entrada.next();
		p = new CorDecorator(p, cor);
		return p;
			
	}

	public static List<Produto> carregaProdutos(){
		List<Produto> produtos = new ArrayList<>();
		Produto p;
		BufferedReader entrada = null;

		try {
			entrada = new BufferedReader(new FileReader("produtos.csv"));
			entrada.readLine();
			String linha = null;
			while ((linha = entrada.readLine()) != null) {
				p = geraProduto(linha);
				produtos.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entrada != null) {
				
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c' ou 'preco_d' ou 'descricao_d' ou 'estoque_d'");
			System.out.println("\tcriterio de filtragem: 'todos' ou 'estoque_menor_igual' ou 'categoria_igual' ou 'preco_entre' ou 'descricao_substring'"); 
			System.out.println("\tparâmetro de filtragem: argumentos adicionais necessários para a filtragem"); 
			System.out.println("\topções de formatação: 'negrito' e/ou 'italico'");
			System.out.println();
			System.exit(1);
		}

		String opcao_algoritmo = args[0];
		String opcao_criterio_ord = args[1];
		String opcao_criterio_filtro = args[2];
		String opcao_parametro_filtro = null;
		if (opcao_criterio_filtro.equals(FILTRO_PRECO_ENTRE)) {
			opcao_parametro_filtro = args[3] + " " + args[4];
		} else {
			opcao_parametro_filtro = args[3];
		}
		
		
		GeradorDeRelatorios gdr = new GeradorDeRelatorios(	carregaProdutos(), 
									opcao_algoritmo,
									opcao_criterio_ord,
									opcao_criterio_filtro,
									opcao_parametro_filtro
								 );

		try{
			gdr.geraRelatorio("saida.html");
		}
		catch(IOException e){
			
			e.printStackTrace();
		}
	}
}
