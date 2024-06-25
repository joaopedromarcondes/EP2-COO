package produto.formatacoes;

import produto.Produto;

public abstract class ProdutoFormatacaoDecorator implements Produto {
    protected Produto f;

    public ProdutoFormatacaoDecorator(Produto f) {
        this.f = f;
    }

    public void setQtdEstoque(int qtdEstoque) { this.f.setQtdEstoque(qtdEstoque); }
    public void setPreco(double preco) { this.f.setPreco(preco);
    }

    public int getId() { return this.f.getId();}
    public String getDescricao() { return this.f.getDescricao(); }
    public String getCategoria() { return this.f.getCategoria(); }
    public int getQtdEstoque() { return this.f.getQtdEstoque(); }
    public double getPreco() { return this.f.getPreco(); }

    public void setF(Produto f) {
        this.f = f;
    }
    //public abstract String formataParaImpressao();
}
