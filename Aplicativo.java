import java.util.Scanner;

public class Aplicativo {

    public static void main(String[] args) {
        boolean state = true;
        DataDesafio3.teclado = new Scanner(System.in);

        while (state) {
            System.out.println("\n--------------------------------------------");
            System.out.println("\nEscolha uma operação: ");
            System.out.println("1- Inserir Data no Arquivo");
            System.out.println("2- Exibir Datas do Arquivo");
            System.out.println("3- Deletar Datas do Arquivo");
            System.out.println("4- Encontrar a Maior Data no Arquivo");
            System.out.println("5- Encontrar a Menor Data no Arquivo");
            System.out.println("6- Fim");
            System.out.print("Selecione: ");
            String option = DataDesafio3.teclado.nextLine();

            switch (option) {
                case "1":
                    String data = DataDesafio3.leitura("Digite uma data no formato DD/MM/AAAA");
                    DataDesafio3.adicionarDataEmArquivo(data, "datas.txt");
                    System.out.println("Data adicionada ao arquivo 'datas.txt'");
                    break;

                case "2":
                    DataDesafio3.exibirDatas("datas.txt");
                    break;

                case "3":
                    DataDesafio3.deletarDadosDoArquivo("datas.txt");
                    break;

                case "4":
                    String maiorData = DataDesafio3.verificarDataMaior("datas.txt");
                    System.out.println("A maior data no arquivo é: " + maiorData);
                    break;

                case "5":
                    String menorData = DataDesafio3.verificarDataMenor("datas.txt");
                    System.out.println("A menor data no arquivo é: " + menorData);
                    break;

                case "6":
                    System.out.println("\nFim do programa");
                    state = false;
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        }

        DataDesafio3.teclado.close();
    }
}
