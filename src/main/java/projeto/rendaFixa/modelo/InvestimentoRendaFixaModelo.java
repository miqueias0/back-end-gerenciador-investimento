package projeto.rendaFixa.modelo;

import projeto.classeInvestimento.modelo.RendaFixa;
import projeto.perfilInvestimento.modelo.Perfil;
import projeto.rentabilidadeTipo.modelo.Rentabilidade;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
public class InvestimentoRendaFixaModelo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false, updatable = false)
    private String idUsuario;
    @Column(name = "renda_fixa_codigo")
    private RendaFixa rendaFixa;
    @ManyToOne
    @JoinColumn(name = "perfil_codigo")
    private Perfil perfil;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "rentabilidade_codigo")
    private Rentabilidade rentabilidade;
    private String prazo;
    private String aplicacaoMinima;
    private Date vencimento;
    private BigDecimal precoUnitario;
    private String emissor;

    public InvestimentoRendaFixaModelo() {
    }

    public Long getId() {
        return id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public RendaFixa getRendaFixa() {
        return rendaFixa;
    }

    public void setRendaFixa(RendaFixa rendaFixa) {
        this.rendaFixa = rendaFixa;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Rentabilidade getRentabilidade() {
        return rentabilidade;
    }

    public void setRentabilidade(Rentabilidade rentabilidade) {
        this.rentabilidade = rentabilidade;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public String getAplicacaoMinima() {
        return aplicacaoMinima;
    }

    public void setAplicacaoMinima(String aplicacaoMinima) {
        this.aplicacaoMinima = aplicacaoMinima;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvestimentoRendaFixaModelo)) return false;
        InvestimentoRendaFixaModelo that = (InvestimentoRendaFixaModelo) o;
        return id.equals(that.id) && idUsuario.equals(that.idUsuario) && rendaFixa.equals(that.rendaFixa) && Objects.equals(perfil, that.perfil) && Objects.equals(descricao, that.descricao) && Objects.equals(rentabilidade, that.rentabilidade) && Objects.equals(prazo, that.prazo) && Objects.equals(aplicacaoMinima, that.aplicacaoMinima) && Objects.equals(vencimento, that.vencimento) && Objects.equals(precoUnitario, that.precoUnitario) && Objects.equals(emissor, that.emissor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUsuario, rendaFixa, perfil, descricao, rentabilidade, prazo, aplicacaoMinima, vencimento, precoUnitario, emissor);
    }
}
