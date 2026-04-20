import java.util.Comparator;

/**
 * Critério A - Valor Final do Pedido (crescente).
 * Desempate 1: Volume Total de Itens (quantProdutos).
 * Desempate 2: Código Identificador do primeiro item do pedido.
 */;

 public class ComparadorCriterioA implements Comparator<Pedido> {
    @Override
    public int compare(Pedido p1, Pedido p2) {
        // 1. Valor Final
        int compValor = Double.compare(p1.valorFinal(), p2.valorFinal());
        if (compValor != 0) return compValor;
        
        // 2. Volume Total (Desempate 1)
        int compVol = Integer.compare(p1.getVolumeTotalItens(), p2.getVolumeTotalItens());
        if (compVol != 0) return compVol;
        
        // 3. ID do Primeiro Item (Desempate 2)
        return Integer.compare(p1.getCodigoPrimeiroItem(), p2.getCodigoPrimeiroItem());
    }
}