package sort;
import criterioordenacao.CriterioOrdenacaoStrategy;
import produto.Produto;

public class InsertionSort extends SortStrategy {

    public InsertionSort(Produto[] produtos, CriterioOrdenacaoStrategy criterioOrdenacaoStrategy) {
        super(produtos, criterioOrdenacaoStrategy);
    }

    public void sort(int ini, int fim) {
        for(int i = ini; i <= fim; i++){

            Produto x = this.produtos[i];
            int j = (i - 1);

            while(j >= ini){
                if (this.criterioOrdenacaoStrategy.compare(x, this.produtos[j])){
                    this.produtos[j + 1] = this.produtos[j];
                    j--;
                }
                else break;
            }

            this.produtos[j + 1] = x;
        }
    }
}
