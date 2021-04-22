package br.com.caelum.camel.dto;

import java.text.NumberFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Guilherme Alves Silveira
 */
public class Negociacao {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    private double preco;
    private int quantidade;
    private Calendar data;

    public double getPreco() {
        return preco;
    }

    public Negociacao setPreco(double preco) {
        this.preco = preco;
        return this;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Negociacao setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public Calendar getData() {
        return data;
    }

    public Negociacao setData(Calendar data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Negociacao that = (Negociacao) o;
        return Double.compare(that.preco, preco) == 0 &&
                quantidade == that.quantidade &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preco, quantidade, data);
    }

    @Override
    public String toString() {
        final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        return new StringJoiner(", ", Negociacao.class.getSimpleName() + "[", "]")
                .add("preco=" + currencyInstance.format(preco))
                .add("quantidade=" + quantidade)
                .add("data=" + FORMATTER.format(data.toInstant().atZone(ZoneId.systemDefault())))
                .toString();
    }
}
