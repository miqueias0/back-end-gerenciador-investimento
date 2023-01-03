package projeto.usuario.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Usuario implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private String id;
    private String nomeUsuario;
    private String email;
    private String telefone;
    @Column(nullable = false)
    private String senha;

    public Usuario() {
    }

    public Usuario(String id, String nomeUsuario, String email, String telefone, String senha) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id) && Objects.equals(nomeUsuario, usuario.nomeUsuario)
                && Objects.equals(email, usuario.email) && Objects.equals(telefone, usuario.telefone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeUsuario, email, telefone);
    }


}
