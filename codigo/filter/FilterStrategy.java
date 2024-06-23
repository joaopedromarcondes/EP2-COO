package filter;
import produto.Produto;

public interface FilterStrategy {
    public boolean filter(Produto p, String argFiltro);
}
