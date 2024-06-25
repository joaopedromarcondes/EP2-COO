package produto.formatacoes;

import produto.Produto;


public class FormatacaoItalico extends ProdutoFormatacaoDecorator {
    public FormatacaoItalico(Produto f) {
        super(f);
    }

    @Override
    public String formataParaImpressao() {
        String saida = "";
        saida += "<span style=\"font-style:italic\">";
        saida += this.f.formataParaImpressao();
        saida += "</span>";
        return saida;
    }
}
