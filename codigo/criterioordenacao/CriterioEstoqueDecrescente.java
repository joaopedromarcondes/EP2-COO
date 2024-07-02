package criterioordenacao;
import produto.Produto;

public class CriterioEstoqueDecrescente implements CriterioOrdenacaoStrategy{

    @Override
    public boolean compare(Produto x, Produto y) {
        return x.getQtdEstoque() > y.getQtdEstoque();
    }
}
