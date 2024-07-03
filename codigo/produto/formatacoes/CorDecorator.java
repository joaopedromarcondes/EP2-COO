package produto.formatacoes;

import produto.Produto;


public class CorDecorator extends ProdutoFormatacaoDecorator {
    private String color;
    public CorDecorator(Produto p, String color) {
        super(p);
        this.color = color;
    }

    @Override
    public String formataParaImpressao() {
        String saida = "";
        saida += "<span style=\"color:";
        saida += this.color;
        saida += "\">";
        saida += this.p.formataParaImpressao();
        saida += "</span>";
        return saida;
    }
}
