package formatacoes;

import produto.Produto;

public abstract class FormatacoesDecorator extends Formatacao {
    protected Formatacao f;

    public FormatacoesDecorator(Formatacao f) {
        this.f = f;
    }

    public void setF(Formatacao f) {
        this.f = f;
    }

}
