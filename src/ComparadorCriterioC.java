import java.util.Comparator;

/**
 * Critério C - Índice de Economia (decrescente).
 * O índice de economia é a diferença entre o valor de catálogo atual e o valor efetivamente pago.
 * Desempate 1: Valor Final do Pedido (crescente).
 * Desempate 2: Código Identificador do pedido (crescente).
 */

public class ComparadorCriterioC implements Comparator<Pedido> {
    @Override
    public int compare(Pedido p1, Pedido p2) {
        // 1. Índice de Economia (Decrescente: inverte-se p1 e p2 na chamada)
        int compEco = Double.compare(p2.getIndiceEconomia(), p1.getIndiceEconomia());
        if (compEco != 0) return compEco;
        
        // 2. Valor Final (Desempate 1)
        int compValor = Double.compare(p1.valorFinal(), p2.valorFinal());
        if (compValor != 0) return compValor;
        
        // 3. ID do Primeiro Item (Desempate 2)
        return Integer.compare(p1.getCodigoPrimeiroItem(), p2.getCodigoPrimeiroItem());
    }
}

