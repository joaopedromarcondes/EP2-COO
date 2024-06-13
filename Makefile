compile: codigo/Produto.java codigo/GeradorDeRelatorios.java codigo/ProdutoPadrao.java
	javac codigo/Produto.java codigo/GeradorDeRelatorios.java codigo/ProdutoPadrao.java

run: codigo/Produto.class codigo/GeradorDeRelatorios.class codigo/ProdutoPadrao.class
	java -cp codigo/:. GeradorDeRelatorios quick preco_c todos negrito

clear:
	rm codigo/*.class