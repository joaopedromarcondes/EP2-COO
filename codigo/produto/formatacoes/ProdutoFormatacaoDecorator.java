package produto.formatacoes;

import produto.Produto;

public abstract class ProdutoFormatacaoDecorator implements Produto {
    protected Produto p;

    public ProdutoFormatacaoDecorator(Produto p) {
        this.p = p;
    }

    public void setQtdEstoque(int qtdEstoque) { this.p.setQtdEstoque(qtdEstoque); }
    public void setPreco(double preco) { this.p.setPreco(preco);
    }

    public int getId() { return this.p.getId();}
    public String getDescricao() { return this.p.getDescricao(); }
    public String getCategoria() { return this.p.getCategoria(); }
    public int getQtdEstoque() { return this.p.getQtdEstoque(); }
    public double getPreco() { return this.p.getPreco(); }

    public void setF(Produto p) {
        this.p = p;
    }
    //public abstract String formataParaImpressao();
}
