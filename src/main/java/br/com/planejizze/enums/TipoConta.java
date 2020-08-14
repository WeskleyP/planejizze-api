package br.com.planejizze.enums;

public enum TipoConta {

    CONTA_CORRENTE(1, "CONTA CORRENTE"),
    CONTA_POUPANCA(2, "CONTA POUPANCA"),
    CONTA_INVESTIMENTO(3, "CONTA INVESTIMENTO");

    private final int cod;
    private final String descricao;

    TipoConta(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoConta toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (TipoConta x : TipoConta.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Id invalido: " + cod);
    }
}
