package model;

import java.util.Scanner;

public class Conta {
    private static int SEQUENCIAL = 1;
    private int numero;
    private int agencia = 1;
    private double saldo;
    private Cliente cliente;
    private String tipo;
    private Scanner scanner;
    private int numeroConta;

    public Conta(Cliente cliente, String tipo) {
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
        this.tipo = tipo;
        this.scanner = new Scanner(System.in);
    }

    public int getNumero() {
        return numero;
    }

    public int getAgencia() {
        return agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public void sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
        } else {
            System.out.println("Saldo insuficiente!");
        }
    }

    public void transferir(double valor, Conta destino) {
        if (saldo >= valor) {
            sacar(valor);
            destino.depositar(valor);
        } else {
            System.out.println("Saldo insuficiente!");
        }
    }

    public void imprimirInfosComuns() {
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("Tipo de Conta: " + tipo);
        System.out.println("Número da Conta: " + numero);
        System.out.println("Agência: " + agencia);
        System.out.println("Saldo: " + saldo);
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

    private Conta obterContaPorNumero(int numeroConta) {
        this.numeroConta = numeroConta;
        return null;
    }
}
