package br.com.planejizze.enums;

public enum StatusReceita {
    RECEBIDO(0, "RECEBIDA"),
    A_RECEBER(1, "A RECEBER"),
    ATRASADA(2, "ATRASADA");

    private final int cod;
    private final String descricao;

    StatusReceita(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
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

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }
}
