package projeto.rendaVariavel.modelo;

import projeto.classeInvestimento.modelo.RendaFixa;
import projeto.classeInvestimento.modelo.RendaVariavel;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
public class InvestimentoRendaVariavelModelo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String descricao;
    private String papel;
    private BigDecimal quantidade;
    private BigDecimal quantidadeTotal;
    private BigDecimal precoUnitario;
    private BigDecimal valorToral;
    private BigDecimal valorMedio;
    private BigDecimal dividendoTotal;
    private BigDecimal dividendoMensal;
    private Date dataCompra;
    @Column(nullable = false, updatable = false)
    private String idUsuario;
    @Column(name = "renda_variavel_codigo")
    private RendaVariavel rendaVariavel;
    @Column
    private String movimentacao;

    public InvestimentoRendaVariavelModelo() {
    }

    public String getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(String movimentacao) {
        this.movimentacao = movimentacao;
    }

    public RendaVariavel getRendaVariavel() {
        return rendaVariavel;
    }

    public void setRendaVariavel(RendaVariavel rendaVariavel) {
        this.rendaVariavel = rendaVariavel;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(BigDecimal quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getValorToral() {
        return valorToral;
    }

    public void setValorToral(BigDecimal valorToral) {
        this.valorToral = valorToral;
    }

    public BigDecimal getValorMedio() {
        return valorMedio;
    }

    public void setValorMedio(BigDecimal valorMedio) {
        this.valorMedio = valorMedio;
    }

    public BigDecimal getDividendoTotal() {
        return dividendoTotal;
    }

    public void setDividendoTotal(BigDecimal dividendoTotal) {
        this.dividendoTotal = dividendoTotal;
    }

    public BigDecimal getDividendoMensal() {
        return dividendoMensal;
    }

    public void setDividendoMensal(BigDecimal dividendoMensal) {
        this.dividendoMensal = dividendoMensal;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvestimentoRendaVariavelModelo)) return false;
        InvestimentoRendaVariavelModelo that = (InvestimentoRendaVariavelModelo) o;
        return Objects.equals(id, that.id) && Objects.equals(descricao, that.descricao) && papel.equals(that.papel) && Objects.equals(quantidade, that.quantidade) && Objects.equals(quantidadeTotal, that.quantidadeTotal) && Objects.equals(precoUnitario, that.precoUnitario) && Objects.equals(valorToral, that.valorToral) && Objects.equals(valorMedio, that.valorMedio) && Objects.equals(dividendoTotal, that.dividendoTotal) && Objects.equals(dividendoMensal, that.dividendoMensal) && Objects.equals(dataCompra, that.dataCompra) && idUsuario.equals(that.idUsuario) && rendaVariavel.equals(that.rendaVariavel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, papel, quantidade, quantidadeTotal, precoUnitario, valorToral, valorMedio, dividendoTotal, dividendoMensal, dataCompra, idUsuario, rendaVariavel);
    }
}
