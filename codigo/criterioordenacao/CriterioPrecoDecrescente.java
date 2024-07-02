package criterioordenacao;
import produto.Produto;

public class CriterioPrecoCrescente implements CriterioOrdenacaoStrategy{

    @Override
    public boolean compare(Produto x, Produto y) {
        return (x.getPreco() > y.getPreco());
    }


}
