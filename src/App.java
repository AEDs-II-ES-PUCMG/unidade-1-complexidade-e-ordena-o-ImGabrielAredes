import java.util.Arrays;
import java.util.Random;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };

    static Random aleatorio = new Random();

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }

    /**
     * Método auxiliar genérico para testar qualquer algoritmo de ordenação
     */
    static void executarTeste(IOrdenador<Integer> ordenador, Integer[] vetorOriginal, String nomeAlgoritmo) {
        // Cópia isolada do vetor para garantir igualdade de condições justas
        Integer[] vetorParaOrdenar = Arrays.copyOf(vetorOriginal, vetorOriginal.length);

        ordenador.ordenar(vetorParaOrdenar);

        // Imprime formatado (como uma tabela)
        System.out.printf("%-18s | Comp: %-10d | Mov: %-10d | Tempo: %6.2f ms%n",
                nomeAlgoritmo,
                ordenador.getComparacoes(),
                ordenador.getMovimentacoes(),
                ordenador.getTempoOrdenacao());
    }

    public static void main(String[] args) {
        // Instancia os algoritmos polimorficamente através da Interface
        IOrdenador<Integer> bolha = new Bubblesort<>();
        IOrdenador<Integer> insercao = new InsertionSort<>();
        IOrdenador<Integer> selecao = new SelectionSort<>();
        IOrdenador<Integer> merge = new Mergesort<>();

        System.out.println("=========================================================================");
        System.out.println("            INICIANDO BATERIA DE TESTES DE ORDENAÇÃO                     ");
        System.out.println("=========================================================================\n");


        for (int tam : tamanhosTesteMedio) {
            System.out.println(">>> Testando com Vetor de Tamanho: " + tam + " <<<");

            Integer[] vetorBase = gerarVetorObjetos(tam);

            executarTeste(bolha, vetorBase, "Bubble Sort");
            executarTeste(insercao, vetorBase, "Insertion Sort");
            executarTeste(selecao, vetorBase, "Selection Sort");
            executarTeste(merge, vetorBase, "Merge Sort");

            System.out.println("-------------------------------------------------------------------------");
        }
    }
}