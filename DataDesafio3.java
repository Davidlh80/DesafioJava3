import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;

public class DataDesafio3 {

    static Scanner teclado;

    // Vetor que armazena o maximo de dias de cada mes
    static int[] DIASDOMES = { 0, 31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31 };

    // Data retornada caso ocorra problemas com a validacao da data
    static String DATAERRO = "01/01/1900";

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String leitura(String mensagem) {
        System.out.print("\n" + mensagem + ": ");
        return teclado.nextLine();
    }

    // Metodo para dividir data em um vetor de tamanho 3, armazenando dia, mes e ano
    // Utilizado para evitar repeticao pois foi utilizado nos metodos de verificar a
    // maior e a menor data e validacao
    public static int[] obterDetalhesData(String data) {
        String[] detalhes = data.split("/");
        int dia = Integer.parseInt(detalhes[0]);
        int mes = Integer.parseInt(detalhes[1]);
        int ano = Integer.parseInt(detalhes[2]);

        int[] detalhesData = { dia, mes, ano };
        return detalhesData;
    }

    // Verifica se a data for valida, caso nao seja retorna uma nova data (DATAERRO)
    public static String dataValida(String data) {

        obterDetalhesData(data);
        int maxDia;

        if (obterDetalhesData(data).length != 3 || data.length() != 10) {
            data = DATAERRO;
        }
        if (obterDetalhesData(data)[2] < 1900) {
            data = DATAERRO;
        }

        else {
            if (obterDetalhesData(data)[1] < 1 || obterDetalhesData(data)[1] > 12) {
                data = DATAERRO;
            }

            else {
                maxDia = DIASDOMES[obterDetalhesData(data)[1]];
                if (anoBissexto(obterDetalhesData(data)[2]) && obterDetalhesData(data)[1] == 2)
                    maxDia++;

                if (obterDetalhesData(data)[0] > maxDia) {
                    data = DATAERRO;
                }

            }
        }
        return data;
    }

    // Verifica o dia da semana referente a mesma data do ano de 2024
    public static String diaSemana(String data) {

        String[] result = { "Domingo", "Segunda-feira", "Terca-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira",
                "Sabado" };
        int contDias = 1;
        int auxMes = 0;

        obterDetalhesData(data);

        int dia = obterDetalhesData(data)[0];
        int mes = obterDetalhesData(data)[1];

        if (mes == 1) {
            contDias = dia % 7;
        } else {
            for (int i = mes - 1; i > 0; i--) {
                auxMes = auxMes + DIASDOMES[i];
            }
            contDias = (contDias + dia + auxMes) % 7;
        }

        return result[contDias];
    }

    public static boolean anoBissexto(int ano) {
        boolean resposta = false;
        if (ano % 400 == 0)
            resposta = true;
        else if (ano % 4 == 0 && ano % 100 != 0)
            resposta = true;

        return resposta;
    }

    // Metodo que verifica a "maior" data dentre as existentes no arquivo,
    // comparando uma a uma comecando pelo ano,
    // depois mes e por fim o dia
    public static String verificarDataMaior(String datas) {
        String maiorData = DATAERRO; // Inicializa com a menor data possivel
        try {
            FileReader fileReader = new FileReader(datas);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String data = linha.trim(); // Lê e remove espaços em branco das extremidades da linha

                // O código abaixo compara a data atual com a menorData encontrada até o momento
                obterDetalhesData(data);
                obterDetalhesData(maiorData);

                int dia = obterDetalhesData(data)[0];
                int mes = obterDetalhesData(data)[1];
                int ano = obterDetalhesData(data)[2];

                int maiorDia = obterDetalhesData(maiorData)[0];
                int maiorMes = obterDetalhesData(maiorData)[1];
                int maiorAno = obterDetalhesData(maiorData)[2];

                if (ano > maiorAno ||
                        (ano == maiorAno && mes > maiorMes ||
                                (ano == maiorAno && mes == maiorMes
                                        && dia > maiorDia))) {
                    maiorData = data; // Atualiza a maiorData se a data atual for menor
                }
            }

            bufferedReader.close();
            fileReader.close();

            return maiorData;

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return DATAERRO; // Retorna a data de erro em caso de erro na leitura do arquivo
        }

    }

    // Metodo que adiciona as datas no arquivo
    public static void adicionarDataEmArquivo(String data, String datas) {
        data = dataValida(data); // Valida a data antes

        if (data.equals(DATAERRO)) {
            data = DATAERRO; // Altera a data para "01/01/1900"
            System.out.println("Data inválida. A data foi alterada para '01/01/1900' e adicionada ao arquivo.");
        }

        try {
            FileWriter fileWriter = new FileWriter(datas, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(data); // Escrita da data no arquivo
            bufferedWriter.newLine(); // Adiciona uma nova linha

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Erro ao adicionar a data no arquivo: " + e.getMessage());
        }
    }

    // Metodo que limpa todas as datas do arquivo
    public static void deletarDadosDoArquivo(String datas) {
        try {
            FileWriter fileWriter = new FileWriter(datas);
            fileWriter.close(); // Apaga o conteudo do arquivo sem remover o arquivo

            System.out.println("Conteúdo do arquivo '" + datas + "' apagado com sucesso!");

        } catch (IOException e) {
            System.err.println("Erro ao apagar o conteúdo do arquivo: " + e.getMessage());
        }
    }

    // Metodo que verifica a "menor" data dentre as existentes no arquivo,
    // comparando uma a uma comecando pelo ano,
    // depois mes e por fim o dia
    public static String verificarDataMenor(String datas) {
        String menorData = "31/12/9999"; // Inicializa com a maior data possivel
        try {
            FileReader fileReader = new FileReader(datas);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String data = linha.trim(); // Lê e remove espaços em branco das extremidades da linha

                // O código abaixo compara a data atual com a menorData encontrada até o momento
                obterDetalhesData(data);
                obterDetalhesData(menorData);

                int dia = obterDetalhesData(data)[0];
                int mes = obterDetalhesData(data)[1];
                int ano = obterDetalhesData(data)[2];

                int menorDia = obterDetalhesData(menorData)[0];
                int menorMes = obterDetalhesData(menorData)[1];
                int menorAno = obterDetalhesData(menorData)[2];

                if (ano < menorAno ||
                        (ano == menorAno && mes < menorMes ||
                                (ano == menorAno && mes == menorMes
                                        && dia < menorDia))) {
                    menorData = data; // Atualiza a maiorData se a data atual for menor
                }
            }

            bufferedReader.close();
            fileReader.close();

            return menorData;

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return DATAERRO; // Retorna a data de erro em caso de erro na leitura do arquivo
        }

    }

    // Metodo para exibir no terminal todas as datas "cadastradas" no arquivo
    public static void exibirDatas(String datas) {
        try {
            FileReader fileReader = new FileReader(datas);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linha;
            System.out.println("\nDatas no arquivo '" + datas + "':");
            while ((linha = bufferedReader.readLine()) != null) {
                System.out.println(linha);
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

}