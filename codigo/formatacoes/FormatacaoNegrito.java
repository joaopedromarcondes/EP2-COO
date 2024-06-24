package formatacoes;

import produto.Produto;


public class FormatacaoNegrito extends FormatacoesDecorator {
    public FormatacaoNegrito(Formatacao f) {
        super(f);
    }

    @Override
    public String getFormatacao(Produto p) {
        String saida = "";
        saida += "<span style=\"font-style:bold\">";
        saida += this.f.getFormatacao(p);
        saida += "</span>";
        return saida;
    }
}
