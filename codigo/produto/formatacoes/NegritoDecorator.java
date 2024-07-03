package produto.formatacoes;

import produto.Produto;


public class NegritoDecorator extends ProdutoFormatacaoDecorator {
    public NegritoDecorator(Produto p) {
        super(p);
    }

    @Override
    public String formataParaImpressao() {
        String saida = "";
        saida += "<span style=\"font-style:bold\">";
        saida += this.p.formataParaImpressao();
        saida += "</span>";
        return saida;
    }
}
