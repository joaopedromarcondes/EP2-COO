package criterioordenacao;
import produto.Produto;

public class CriterioDescricaoCrescente implements CriterioOrdenacaoStrategy{
    @Override
    public boolean compare(Produto x, Produto y) {
        return ( x.getDescricao().compareToIgnoreCase(y.getDescricao()) < 0);
    }
}
