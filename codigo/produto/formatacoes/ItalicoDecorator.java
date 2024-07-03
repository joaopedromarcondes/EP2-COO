package produto.formatacoes;

import produto.Produto;


public class ItalicoDecorator extends ProdutoFormatacaoDecorator {
    public ItalicoDecorator(Produto p) {
        super(p);
    }

    @Override
    public String formataParaImpressao() {
        String saida = "";
        saida += "<span style=\"font-style:italic\">";
        saida += this.p.formataParaImpressao();
        saida += "</span>";
        return saida;
    }
}
