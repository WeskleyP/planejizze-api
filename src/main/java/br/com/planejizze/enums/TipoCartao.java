package br.com.planejizze.enums;

public enum TipoCartao {

    CREDTIO(1, "CREDTIO"),
    DEBITO(2, "DEBITO");

    private final int cod;
    private final String descricao;

    TipoCartao(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCartao toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (TipoCartao x : TipoCartao.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Id invalido: " + cod);
    }
}
