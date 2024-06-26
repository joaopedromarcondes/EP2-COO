package sort;
import criterioordenacao.CriterioOrdenacaoStrategy;
import produto.Produto;

import java.util.List;

public class InsertionSort extends SortStrategy {

    public InsertionSort(List<Produto> produtos, CriterioOrdenacaoStrategy criterioOrdenacaoStrategy) {
        super(produtos, criterioOrdenacaoStrategy);
    }

    public void sort(int ini, int fim) {
        for(int i = ini; i <= fim; i++){

            Produto x = this.produtos.get(i);
            int j = (i - 1);

            while(j >= ini){
                if (this.criterioOrdenacaoStrategy.compare(x, this.produtos.get(j))){
                    Produto temp = this.produtos.get(j);
                    this.produtos.set(j+1, temp);
                    j--;
                }
                else break;
            }

            this.produtos.set(j + 1, x);
        }
    }
}
