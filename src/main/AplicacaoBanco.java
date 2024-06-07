package main;

import model.*;

import java.util.List;
import java.util.Scanner;

public class AplicacaoBanco {
    private Banco banco;
    private Scanner scanner;
    private Cliente cliente;

    public AplicacaoBanco() {
        this.banco = new Banco();
        this.scanner = new Scanner(System.in);
    }

    public void executar() {
        boolean continuar = true;
        while (continuar) {
            exibirMenuPrincipal();
            int opcaoInicial = obterOpcaoValida();

            switch (opcaoInicial) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    acessarConta();
                    break;
                case 3:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("Bem-vindo ao Banco dos DEVs!");
        System.out.println("1 - Criar Conta");
        System.out.println("2 - Acessar Conta");
        System.out.println("3 - Sair");
    }

    public void exibirMenuConta(Conta conta, Cliente cliente) {
        while (true) {
            System.out.println("\n=== Menu Conta " + conta.getTipo() + " ===");
            System.out.println("1 - Ver Saldo");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Transferir");
            System.out.println("5 - Voltar");

            int opcao = obterOpcaoValida();

            switch (opcao) {
                case 1:
                    System.out.println("Saldo: " + conta.getSaldo());
                    break;
                case 2:
                    System.out.println("Digite o valor para depositar:");
                    double valorDeposito = scanner.nextDouble();
                    scanner.nextLine();
                    conta.depositar(valorDeposito);
                    System.out.println("Depósito realizado com sucesso!");
                    conta.imprimirInfosComuns();
                    break;
                case 3:
                    System.out.println("Digite o valor para sacar:");
                    double valorSaque = scanner.nextDouble();
                    scanner.nextLine();
                    conta.sacar(valorSaque);
                    System.out.println("Saque realizado com sucesso!");
                    conta.imprimirInfosComuns();
                    break;
                case 4:
                    System.out.println("Digite o número da conta de destino:");
                    int numeroContaDestino = scanner.nextInt();
                    scanner.nextLine();
                    Conta destino = obterContaPeloNumero(cliente, numeroContaDestino);
                    if (destino != null) {
                        System.out.println("Digite o valor para transferir:");
                        double valorTransferencia = scanner.nextDouble();
                        scanner.nextLine();
                        conta.transferir(valorTransferencia, destino);
                        System.out.println("Transferência realizada com sucesso!");
                        conta.imprimirInfosComuns();
                    } else {
                        System.out.println("Conta de destino não encontrada!");
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private void criarConta() {
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        Cliente cliente = banco.obterCliente(nome);

        if (cliente == null) {
            cliente = banco.adicionarCliente(nome);
        }

        System.out.println("Digite o tipo de conta (1 - Corrente, 2 - Poupança):");
        int tipoConta = obterTipoConta();

        if ((tipoConta == 1 && cliente.getContas().stream().anyMatch(c -> c.getTipo().equals("Corrente"))) ||
                (tipoConta == 2 && cliente.getContas().stream().anyMatch(c -> c.getTipo().equals("Poupança")))) {
            System.out.println("O cliente já possui uma conta desse tipo!");
            return;
        }

        Conta conta = new Conta(cliente, tipoConta == 1 ? "Corrente" : "Poupança");
        cliente.adicionarConta(conta);

        System.out.println("Conta criada com sucesso!");
        conta.imprimirInfosComuns();

    }

    private void acessarConta() {
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        Cliente cliente = banco.obterCliente(nome);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Digite o tipo de conta (1 - Corrente, 2 - Poupança):");
        int tipoConta = obterTipoConta();

        Conta conta = cliente.getContas().stream()
                .filter(c -> (tipoConta == 1 && c.getTipo().equals("Corrente")) || (tipoConta == 2 && c.getTipo().equals("Poupança")))
                .findFirst().orElse(null);

        if (conta != null) {
            exibirMenuConta(conta, cliente);
        } else {
            System.out.println("Conta não encontrada!");
        }
    }

    private int obterTipoConta() {
        while (true) {
            int tipoConta = obterOpcaoValida();
            if (tipoConta == 1 || tipoConta == 2) {
                return tipoConta;
            } else {
                System.out.println("Opção inválida! Digite 1 para Corrente ou 2 para Poupança.");
            }
        }
    }

    private Conta obterContaPeloNumero(Cliente cliente, int numeroConta){
        Conta contaResult = null;
        List<Conta>contas = cliente.getContas();
        for (Conta c: contas){
            if(numeroConta == c.getNumero()){
                contaResult = c;
                break;
            }
        }
        return contaResult;
    }

    private int obterOpcaoValida() {
        while (!scanner.hasNextInt()) {
            System.out.println("Opção inválida! Por favor, insira um número.");
            scanner.next();
        }
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }
}
