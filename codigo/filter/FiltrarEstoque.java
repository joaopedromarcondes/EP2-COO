package filter;

import produto.Produto;

public class FiltrarEstoque implements FilterStrategy{

    @Override
    public boolean filter(Produto p, String argFiltro) {
        return (p.getQtdEstoque() <= Integer.parseInt(argFiltro));
    }
}
