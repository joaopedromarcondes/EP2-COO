package sort;
import criterioordenacao.CriterioOrdenacaoStrategy;
import produto.Produto;

import java.util.List;

public abstract class SortStrategy {


    protected List<Produto> produtos;
    protected CriterioOrdenacaoStrategy criterioOrdenacaoStrategy;
    public SortStrategy(List<Produto> produtos, CriterioOrdenacaoStrategy criterioOrdenacaoStrategy) {
        this.produtos = produtos;
        this.criterioOrdenacaoStrategy = criterioOrdenacaoStrategy;
    }


    public abstract void sort(int i, int fim);
}
