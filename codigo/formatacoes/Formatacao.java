package formatacoes;

import produto.Produto;

public class Formatacao {

    public String getFormatacao(Produto p) {
        String saida = p.formataParaImpressao();
        return saida;
    }
}
