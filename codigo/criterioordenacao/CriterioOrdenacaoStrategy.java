package criterioordenacao;
import produto.Produto;

public interface CriterioOrdenacaoStrategy {
    public boolean compare(Produto x, Produto y);

}
