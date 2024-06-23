package criterioordenacao;
import produto.Produto;

public class CriterioEstoqueCrescente implements CriterioOrdenacaoStrategy{

    @Override
    public boolean compare(Produto x, Produto y) {
        return x.getQtdEstoque() < y.getQtdEstoque();
    }
}
