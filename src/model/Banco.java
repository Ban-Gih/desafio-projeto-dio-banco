package model;

import java.util.HashMap;
import java.util.Map;

public class Banco {
    private Map<String, Cliente> clientes;

    public Banco() {
        this.clientes = new HashMap<>();
    }

    public Cliente adicionarCliente(String nome) {
        Cliente cliente = new Cliente(nome);
        clientes.put(nome, cliente);
        return cliente;
    }

    public Cliente obterCliente(String nome) {
        return clientes.get(nome);
    }
}
