package produto.formatacoes;

import produto.Produto;


public class FormatacaoNegrito extends ProdutoFormatacaoDecorator {
    public FormatacaoNegrito(Produto f) {
        super(f);
    }

    @Override
    public String formataParaImpressao() {
        String saida = "";
        saida += "<span style=\"font-style:bold\">";
        saida += this.f.formataParaImpressao();
        saida += "</span>";
        return saida;
    }
}
