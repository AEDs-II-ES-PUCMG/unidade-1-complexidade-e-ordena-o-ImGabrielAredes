import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class AppOficina {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;

    static Produto[] produtosPorCodigo;
    static Produto[] produtosPorDescricao;

    static Scanner teclado;

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }
    
    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Inserção");
        System.out.println("3 - Seleção");
        System.out.println("4 - Mergesort");
        System.out.println("0 - Cancelar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Padrão (Por Nome)");
        System.out.println("2 - Por código (ID)");
        System.out.println("0 - Cancelar");
        
        return lerNumero("Digite sua opção", Integer.class);
    }

    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado. Produtos não carregados");
            dadosCarregados = null;
        }
        return dadosCarregados;
    }

    static Produto buscaBinariaPorCodigo(int codigo) {
        int inicio = 0;
        int fim = quantProdutos - 1;

        while (inicio <= fim) {
            int meio = inicio + (fim - inicio) / 2; 
            int codigoMeio = produtosPorCodigo[meio].hashCode();

            if (codigoMeio == codigo) {
                return produtosPorCodigo[meio]; 
            } else if (codigoMeio < codigo) {
                inicio = meio + 1; 
            } else {
                fim = meio - 1; 
            }
        }
        return null; 
    }

    static Produto buscaBinariaPorDescricao(String descricaoProcurada) {
        int inicio = 0;
        int fim = quantProdutos - 1;

        while (inicio <= fim) {
            int meio = inicio + (fim - inicio) / 2;
            int comparacao = produtosPorDescricao[meio].descricao.compareToIgnoreCase(descricaoProcurada);

            if (comparacao == 0) {
                return produtosPorDescricao[meio]; 
            } else if (comparacao < 0) {
                inicio = meio + 1; 
            } else {
                fim = meio - 1; 
            }
        }
        return null;
    }

    static Produto localizarProduto() {
        cabecalho();
        System.out.println("Localizando um produto (Pesquisa Binária: O(log n))");
        System.out.println("1 - Buscar por Código (ID)");
        System.out.println("2 - Buscar por Nome (Descrição)");
        
        Integer opcao = lerNumero("Escolha o critério de busca", Integer.class);
        if (opcao == null) return null;

        if (opcao == 1) {
            Integer numero = lerNumero("Digite o código numérico do produto", Integer.class);
            if (numero != null) return buscaBinariaPorCodigo(numero);
            
        } else if (opcao == 2) {
            System.out.print("Digite o nome exato do produto: ");
            String nome = teclado.nextLine();
            return buscaBinariaPorDescricao(nome);
        }

        return null; 
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Produto não encontrado ou dados inválidos.";
        
        if(produto != null){
            mensagem = String.format("Dados do produto:\n%s", produto);            
        }
        
        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        Double valor = lerNumero("valor", Double.class);
        if (valor == null) return;
        
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
            relatorio.append(produtos[i]+"\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos() {
        cabecalho();
        
        Integer opcaoMetodo = exibirMenuOrdenadores();
        if (opcaoMetodo == null || opcaoMetodo == 0) return;

        Integer opcaoCriterio = exibirMenuComparadores();
        if (opcaoCriterio == null || opcaoCriterio == 0) return;

        switch (opcaoMetodo) {
            case 1 -> ordenador = new Bubblesort<>();
            case 2 -> ordenador = new InsertSort<>(); 
            case 3 -> ordenador = new SelectionSort<>();
            case 4 -> ordenador = new Mergesort<>();
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }

        Produto[] dadosOrdenados;

        if (opcaoCriterio == 2) {
            dadosOrdenados = ordenador.ordenar(produtos, new ComparadorPorCodigo());
        } else {
            dadosOrdenados = ordenador.ordenar(produtos);
        }

        verificarSubstituicao(produtos, dadosOrdenados);
    }

    static void embaralharProdutos(){
        
        Collections.shuffle(Arrays.asList(produtos).subList(0, quantProdutos));
    }

    static void verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados){
        System.out.println("\nOrdenação concluída!");
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)? ");
        String resposta = teclado.nextLine().toUpperCase();
        if(resposta.equals("S")) {
            System.arraycopy(copiaDados, 0, produtos, 0, quantProdutos);
            System.out.println("Dados principais atualizados com sucesso.");
        }
    }

    static void listarProdutos(){
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos(nomeArquivoDados);

        if (produtos != null && quantProdutos > 0) {
            System.out.println("Criando índices de busca em memória...");
            Produto[] produtosParaDescricao = Arrays.copyOf(produtos, quantProdutos);
            Produto[] produtosParaCodigo = Arrays.copyOf(produtos, quantProdutos);
            
            produtosPorDescricao = new Mergesort<Produto>().ordenar(produtosParaDescricao);
            produtosPorCodigo = new Mergesort<Produto>().ordenar(produtosParaCodigo, new ComparadorPorCodigo());
            System.out.println("Índices criados com sucesso!\n");
        }

        embaralharProdutos();

        Integer opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            if (opcao == null) continue;

            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
                default -> System.out.println("Opção inválida.");
            }
            if (opcao != 0) pausa();
        } while (opcao != 0);
        
        teclado.close();
    }                        
}