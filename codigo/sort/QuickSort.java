package sort;
import criterioordenacao.CriterioOrdenacaoStrategy;
import produto.Produto;

public class QuickSort extends SortStrategy{

    public QuickSort(Produto[] produtos, CriterioOrdenacaoStrategy criterioOrdenacaoStrategy) {
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

        Produto x = this.produtos[ini];
        int i = (ini - 1);
        int j = (fim + 1);

        while(true){

            do{
                j--;

            } while(this.criterioOrdenacaoStrategy.compare(x, this.produtos[j]));

            do{
                i++;

            } while(this.criterioOrdenacaoStrategy.compare(this.produtos[i], x));



            if(i < j){
                this.swapProdutos(i, j);
            }
            else return j;
        }
    }

    private void swapProdutos(int i, int j) {
        Produto temp = this.produtos[i];
        this.produtos[i] = this.produtos[j];
        this.produtos[j] = temp;
    }


}
