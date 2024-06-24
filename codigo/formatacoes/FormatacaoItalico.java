package formatacoes;

import produto.Produto;


public class FormatacaoItalico extends FormatacoesDecorator {
    public FormatacaoItalico(Formatacao f) {
        super(f);
    }

    @Override
    public String getFormatacao(Produto p) {
        String saida = "";
        saida += "<span style=\"font-style:italic\">";
        saida += this.f.getFormatacao(p);
        saida += "</span>";
        return saida;
    }
}
