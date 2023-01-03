package projeto.rentabilidadeTipo.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Rentabilidade implements Serializable {

    @Id
    @Column(name = "codigo", nullable = false)
    private String codigo;
    private String descricao;
    @Column(nullable = false, updatable = false)
    private String idUsuario;

    public Rentabilidade() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rentabilidade)) return false;
        Rentabilidade that = (Rentabilidade) o;
        return codigo.equals(that.codigo) && Objects.equals(descricao, that.descricao) && idUsuario.equals(that.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, descricao, idUsuario);
    }
}
