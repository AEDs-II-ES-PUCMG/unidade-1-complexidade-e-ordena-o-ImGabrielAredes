public class MergeSort<T extends Comparable<T>> implements IOrdenador<T> {
    private int comparacoes;
    private int movimentacoes;
    private double tempoOrdenacao;

    @Override
    public T[] ordenar(T[] dados) {
        this.comparacoes = 0;
        this.movimentacoes = 0;
        long tempoInicio = System.nanoTime();

        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Comparable[dados.length];

        sort(dados, aux, 0, dados.length - 1);

        long tempoFim = System.nanoTime();
        this.tempoOrdenacao = (tempoFim - tempoInicio) / 1_000_000.0;

        return dados;
    }

    private void sort(T[] dados, T[] aux, int esq, int dir) {
        if (esq < dir) {
            int meio = esq + (dir - esq) / 2;

            sort(dados, aux, esq, meio);
            sort(dados, aux, meio + 1, dir);
            merge(dados, aux, esq, meio, dir);
        }
    }

    private void merge(T[] dados, T[] aux, int esq, int meio, int dir) {
        for (int k = esq; k <= dir; k++) {
            aux[k] = dados[k];
            this.movimentacoes++;
        }

        int i = esq;
        int j = meio + 1;

        for (int k = esq; k <= dir; k++) {
            if (i > meio) {
                dados[k] = aux[j++];
                this.movimentacoes++;
            } else if (j > dir) {
                dados[k] = aux[i++];
                this.movimentacoes++;
            } else {
                this.comparacoes++;
                if (aux[i].compareTo(aux[j]) <= 0) {
                    dados[k] = aux[i++];
                } else {
                    dados[k] = aux[j++];
                }
                this.movimentacoes++;
            }
        }
    }

    @Override
    public int getComparacoes() {
        return comparacoes;
    }

    @Override
    public int getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }
}