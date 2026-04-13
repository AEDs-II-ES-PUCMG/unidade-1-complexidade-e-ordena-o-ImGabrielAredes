public class SelectionSort<T extends Comparable<T>> implements IOrdenador<T> {
    private int comparacoes;
    private int movimentacoes;
    private double tempoOrdenacao;

    @Override
    public T[] ordenar(T[] dados) {
        this.comparacoes = 0;
        this.movimentacoes = 0;
        long tempoInicio = System.nanoTime();

        int n = dados.length;
        for (int i = 0; i < n - 1; i++) {
            int indiceMenor = i;

            for (int j = i + 1; j < n; j++) {
                this.comparacoes++;
                if (dados[j].compareTo(dados[indiceMenor]) < 0) {
                    indiceMenor = j;
                }
            }

            if (indiceMenor != i) {
                T temp = dados[indiceMenor];
                dados[indiceMenor] = dados[i];
                dados[i] = temp;
                this.movimentacoes += 3;
            }
        }

        long tempoFim = System.nanoTime();
        this.tempoOrdenacao = (tempoFim - tempoInicio) / 1_000_000.0;

        return dados;
    }

    @Override
    public int getComparacoes() {
        return this.comparacoes;
    }

    @Override
    public int getMovimentacoes() {
        return this.movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return this.tempoOrdenacao;
    }
}