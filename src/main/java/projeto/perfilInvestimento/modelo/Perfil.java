package projeto.perfilInvestimento.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Perfil implements Serializable {

    private String descricao;
    @Id
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(nullable = false, updatable = false)
    private String idUsuario;

    public Perfil() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Perfil)) return false;
        Perfil perfil = (Perfil) o;
        return Objects.equals(descricao, perfil.descricao) && codigo.equals(perfil.codigo) && idUsuario.equals(perfil.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, codigo, idUsuario);
    }
}
