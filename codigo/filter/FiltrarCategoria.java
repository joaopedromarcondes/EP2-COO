package filter;

import produto.Produto;

public class FiltrarCategoria implements FilterStrategy{

    @Override
    public boolean filter(Produto p, String argFiltro) {
        return (p.getCategoria().equalsIgnoreCase(argFiltro));
    }
}
