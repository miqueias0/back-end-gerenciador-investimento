package projeto.usuario.api.loginModelo;

import projeto.usuario.modelo.Usuario;

public class LoginModelo {

    private Usuario usuario;
    private Boolean manterLogado = false;

    public LoginModelo() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getManterLogado() {
        return manterLogado;
    }

    public void setManterLogado(Boolean manterLogado) {
        this.manterLogado = manterLogado;
    }
}
