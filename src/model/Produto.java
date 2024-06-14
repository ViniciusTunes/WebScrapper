package model;

import java.math.BigDecimal;

import static org.asynchttpclient.util.MiscUtils.isEmpty;

public class Produto {
    private String descricao;
    private String dadosValores;
    private BigDecimal valorAVista;
    private BigDecimal valorAPrazo;
    private int parcelas;

    public Produto(String descricao, String dadosValores) {
        super();
        this.descricao = descricao;
        this.dadosValores = dadosValores;
        this.parcelas = getParcelas(dadosValores);
        this.valorAVista = getValorAVista(dadosValores);
        this.valorAPrazo = getValorAPrazo(dadosValores);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDadosValores() {
        return dadosValores;
    }

    public void setDadosValores(String dadosValores) {
        this.dadosValores = dadosValores;
    }

    public BigDecimal getValorAVista() {
        return valorAVista;
    }

    public BigDecimal getValorAVista(String dadosValores) {
        if (isEmpty(dadosValores)) {
            return BigDecimal.ZERO;
        }
        int indexOfDollar = dadosValores.indexOf('$');
        int indexOfNewLine = dadosValores.indexOf('\n');

        if (indexOfDollar >= 0 && indexOfNewLine >= 0) {
            String valorString = dadosValores.substring(indexOfDollar + 1, indexOfNewLine).trim()
                    .replaceAll("\\.", "");
            return new BigDecimal(valorString + "." + dadosValores.substring(indexOfNewLine + 1, indexOfNewLine +3).trim());
        } else {
            return BigDecimal.ZERO;
        }
    }

    public void setValorAVista(BigDecimal valorAVista) {
        this.valorAVista = valorAVista;
    }

    public BigDecimal getValorAPrazo() {
        return valorAPrazo;
    }

    public BigDecimal getValorAPrazo(String dadosValores) {
        if (this.parcelas == 0)
            return BigDecimal.ZERO;

        return new BigDecimal(dadosValores.substring(dadosValores.lastIndexOf('$')+ 2,
                        dadosValores.lastIndexOf(',')+ 3)
                .replaceAll("\\.", "").replaceAll(",","\\."))
                .multiply(new BigDecimal(this.parcelas));
    }

    public void setValorAPrazo(BigDecimal valorAPrazo) {
        this.valorAPrazo = valorAPrazo;
    }

    public int getParcelas() {
        return parcelas;
    }

    public int getParcelas(String dadosValores) {
        if (isEmpty(dadosValores)) {
            return 0;
        }
        try {
            int indexOfAte = dadosValores.indexOf("até ");
            int indexOfXde = dadosValores.indexOf("x de");

            if (indexOfAte > 0 && indexOfXde > 0) {
                return Integer.parseInt(dadosValores.substring(indexOfAte + 4, indexOfXde));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido para número de parcelas: " + dadosValores);
        }
        return 0;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "descricao='" + descricao + '\'' +
                ", dadosValores='" + dadosValores + '\'' +
                ", valorAVista=" + valorAVista +
                ", ValorAPrazo=" + valorAPrazo +
                ", parcelas=" + parcelas +
                '}';
    }
}
