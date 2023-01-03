package projeto.classeInvestimento.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class RendaFixa  implements Serializable {
    private String descricao;
    @Id
    @Column(nullable = false, unique = true, length = 6)
    private String codigo;
    @Column(nullable = false, updatable = false)
    private String idUsuario;

    public RendaFixa() {
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RendaFixa)) return false;
        RendaFixa rendaFixa = (RendaFixa) o;
        return Objects.equals(descricao, rendaFixa.descricao) && codigo.equals(rendaFixa.codigo) && idUsuario.equals(rendaFixa.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, codigo, idUsuario);
    }
}
