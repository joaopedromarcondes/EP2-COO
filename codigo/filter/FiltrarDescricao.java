package filter;

import produto.Produto;

public class FiltrarDescricao implements FilterStrategy{

    @Override
    public boolean filter(Produto p, String argFiltro) {
        return (p.getDescricao().contains(argFiltro));
    }
}
