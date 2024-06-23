package filter;

import produto.Produto;

public class FiltrarTodos implements FilterStrategy{

    @Override
    public boolean filter(Produto p, String argFiltro) {
        return true;
    }
}
