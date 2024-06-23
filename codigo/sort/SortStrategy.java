package sort;
import criterioordenacao.CriterioOrdenacaoStrategy;
import produto.Produto;

public abstract class SortStrategy {


    protected Produto[] produtos;
    protected CriterioOrdenacaoStrategy criterioOrdenacaoStrategy;
    public SortStrategy(Produto[] produtos, CriterioOrdenacaoStrategy criterioOrdenacaoStrategy) {
        this.produtos = produtos;
        this.criterioOrdenacaoStrategy = criterioOrdenacaoStrategy;
    }


    public abstract void sort(int i, int fim);
}
