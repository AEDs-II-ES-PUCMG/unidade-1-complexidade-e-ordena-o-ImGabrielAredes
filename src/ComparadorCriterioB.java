import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */

public class ComparadorCriterioB implements Comparator<Pedido> {
    @Override
    public int compare(Pedido p1, Pedido p2) {
        // 1. Volume Total
        int compVol = Integer.compare(p1.getVolumeTotalItens(), p2.getVolumeTotalItens());
        if (compVol != 0) return compVol;
        
        // 2. Data do Pedido (Desempate 1)
        int compData = p1.getDataPedido().compareTo(p2.getDataPedido());
        if (compData != 0) return compData;
        
        // 3. ID do Primeiro Item (Desempate 2)
        return Integer.compare(p1.getCodigoPrimeiroItem(), p2.getCodigoPrimeiroItem());
    }
}
