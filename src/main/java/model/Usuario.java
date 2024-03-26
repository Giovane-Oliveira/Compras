package model;

public class Usuario extends Colaborador {
	int UsuarioId, colaboradorId, logado;
	String usuario, senha, perfil, hash;
	

	public int getLogado() {
		return logado;
	}
	public void setLogado(int logado) {
		this.logado = logado;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getUsuarioId() {
		return UsuarioId;
	}
	public void setUsuarioId(int usuarioId) {
		UsuarioId = usuarioId;
	}
	public int getColaboradorId() {
		return colaboradorId;
	}
	public void setColaboradorId(int colaboradorId) {
		this.colaboradorId = colaboradorId;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
}

	
