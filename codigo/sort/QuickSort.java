package sort;
import criterioordenacao.CriterioOrdenacaoStrategy;
import produto.Produto;

import java.util.List;

public class QuickSort extends SortStrategy{

    public QuickSort(List<Produto> produtos, CriterioOrdenacaoStrategy criterioOrdenacaoStrategy) {
        super(produtos, criterioOrdenacaoStrategy);
    }

    public void sort(int ini, int fim) {
        if(ini < fim) {

            int q = particiona(ini, fim);

            this.sort(ini, q);
            this.sort(q + 1, fim);
        }
    }

    private int particiona(int ini, int fim){

        Produto x = this.produtos.get(ini);
        int i = (ini - 1);
        int j = (fim + 1);

        while(true){

            do{
                j--;

            } while(this.criterioOrdenacaoStrategy.compare(x, this.produtos.get(j)));

            do{
                i++;

            } while(this.criterioOrdenacaoStrategy.compare(this.produtos.get(i), x));



            if(i < j){
                this.swapProdutos(i, j);
            }
            else return j;
        }
    }

    private void swapProdutos(int i, int j) {
        Produto temp = this.produtos.get(i);
        this.produtos.set(i, this.produtos.get(j));
        this.produtos.set(j, temp);
    }


}
