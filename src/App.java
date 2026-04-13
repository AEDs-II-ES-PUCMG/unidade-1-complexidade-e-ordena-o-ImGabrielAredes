import java.util.Arrays;
import java.util.Random;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };

    static Random aleatorio = new Random();

    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;
    }

    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }

    static void executarTeste(IOrdenador<Integer> ordenador, Integer[] vetorOriginal, String nomeAlgoritmo) {
        Integer[] vetorParaOrdenar = Arrays.copyOf(vetorOriginal, vetorOriginal.length);

        ordenador.ordenar(vetorParaOrdenar);

        System.out.printf("%-18s | Comp: %-10d | Mov: %-10d | Tempo: %6.2f ms%n",
                nomeAlgoritmo,
                ordenador.getComparacoes(),
                ordenador.getMovimentacoes(),
                ordenador.getTempoOrdenacao());
    }

    public static void main(String[] args) {
        IOrdenador<Integer> bolha = new BubbleSort<>();
        IOrdenador<Integer> insercao = new InsertionSort<>();
        IOrdenador<Integer> selecao = new SelectionSort<>();

        System.out.println("=========================================================================");
        System.out.println("INICIANDO BATERIA DE TESTES DE ORDENAÇÃO");
        System.out.println("=========================================================================\n");

        for (int tam : tamanhosTesteMedio) {
            System.out.println(">>> Testando com Vetor de Tamanho: " + tam + " <<<");

            Integer[] vetorBase = gerarVetorObjetos(tam);

            executarTeste(bolha, vetorBase, "Bubble Sort");
            executarTeste(insercao, vetorBase, "Insertion Sort");
            executarTeste(selecao, vetorBase, "Selection Sort");

            System.out.println("-------------------------------------------------------------------------");
        }
    }
}