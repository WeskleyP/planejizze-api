package br.com.planejizze.enums;

public enum StatusDespesa {
    PAGO(0, "PAGO"),
    A_PAGAR(1, "A PAGAR"),
    ATRASADA(2, "ATRASADA");

    private final int cod;
    private final String descricao;

    StatusDespesa(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static StatusDespesa toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (StatusDespesa x : StatusDespesa.values()) {
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
