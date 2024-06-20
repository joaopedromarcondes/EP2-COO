package formatacoes;

public abstract class FormatacoesDecorator {
    private FormatacoesDecorator f;

    public void setF(FormatacoesDecorator f) {
        this.f = f;
    }

    public abstract void formatar();

}
