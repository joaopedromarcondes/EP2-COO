package filter;

import produto.Produto;
import java.util.Scanner;

public class FiltrarPreco implements FilterStrategy{

    @Override
    public boolean filter(Produto p, String argFiltro) {
        Scanner s = new Scanner(argFiltro);
        int menor = Integer.parseInt(s.next());
        int maior = Integer.parseInt(s.next());
        return (p.getPreco() > menor && p.getPreco() < maior);
    }
}
