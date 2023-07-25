package org.example;

import model.Carro;
import sistema.SistemaEstacionamento;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TesteEstacionamento {
    static Scanner entrada = new Scanner(System.in);

    public static int menuExibir() {
        int opcao = 0;
        try {
            System.out.println("----------------------------------------------------- ");
            System.out.println("| 1 - Inserir novos carros                           |");
            System.out.println("| 2 - Inserir hora de entrada                        |");
            System.out.println("| 3 - Inserir hora de saida                          |");
            System.out.println("| 4 - Inativar carros que sairam                     |");
            System.out.println("| 5 - Visualizar tabela de carros                    |");
            System.out.println("| 6 - Visualizar tabela de carros no estacionamento  |");
            System.out.println("| 7 - Sair                                           |");
            System.out.println("----------------------------------------------------- ");
            System.out.println("Escolha uma opção ");
            opcao = entrada.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Valor invalido! Digite um número inteiro positivo");
            entrada.nextLine();
        }

        return opcao;
    }

    public static void main(String[] args) {

        SistemaEstacionamento sistema = new SistemaEstacionamento();

        String nomeDono;
        String marcaCarro;
        String placaCarro;
        String horaEntrada;
        String horaSaida;
        int idCarro;

        boolean continuar = true;

        while (continuar){
            int opcao = menuExibir();
            switch (opcao){
                case 1:
                    System.out.println("\n==========Inserir novos carros ======\n");

                    System.out.println("Qual a marca do carro ?");
                    marcaCarro = entrada.next();

                    System.out.println("Qual a placa do Dono do carro ?");
                    placaCarro = entrada.next();

                    System.out.println("Qual o nome do Dono do carro ?");
                    entrada.nextLine();
                    nomeDono = entrada.nextLine();

                    sistema.inserirCarro(new Carro(nomeDono,marcaCarro,placaCarro));
                    break;

                case 2:
                    System.out.println("\n==========Inserir hora de entrada do carro ======\n");

                    System.out.println("Qual o id do Carro ?");
                    idCarro = entrada.nextInt();

                    System.out.println("Qual a hora de Entrada ?");
                    horaEntrada = entrada.next();

                    sistema.inserirEntrada(idCarro,horaEntrada);

                    break;

                case 3:
                    System.out.println("\n==========Inserir saida do carro ======\n");

                    System.out.println("Qual o id do Carro ?");
                    idCarro = entrada.nextInt();

                    System.out.println("Qual a hora de saida ?");
                    horaSaida = entrada.next();

                    sistema.inserirSaida(idCarro,horaSaida);
                    sistema.inserirNaTabelaValorPermanencia(idCarro);
                    sistema.inserirNaTabelaValorPagar(idCarro);

                    break;

                case 4:
                    System.out.println("\n==========Inativar carros que não estão no estacionamento ======\n");

                    System.out.println("Qual o id do Carro ?");
                    idCarro = entrada.nextInt();

                    sistema.inativarCarrosQueSairam(idCarro);
                    //sistema.deletarCarronoEstacionamento(idCarro);

                    break;

                case 5:
                    System.out.println("\n==========Visualizar tabelas de carros ======\n");
                    sistema.consultarTabeladeCarros();

                    break;

                case 6:
                    System.out.println("\n==========Visualizar tabelas de no estacionamento ======\n");
                    sistema.consultarTabelaEstacionamento();

                    break;

                case 7:
                    System.out.println("\n++++++++Saindo++++++++\n");
                    entrada.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("opção invalida!!!!");


            }
        }

    }
}
