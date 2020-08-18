package br.com.planejizze.enums;

public enum StatusReceita {
    RECEBIDO(1, "RECEBIDO"),
    A_RECEBER(2, "A RECEBER"),
    ATRASADA(3, "ATRASADA");

    private final int cod;
    private final String descricao;

    StatusReceita(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusReceita toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (StatusReceita x : StatusReceita.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Id invalido: " + cod);
    }
}
