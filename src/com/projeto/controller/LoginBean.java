package com.projeto.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.projeto.dao.UsuarioDAO;
import com.projeto.entidades.Usuario;

@ManagedBean
@ApplicationScoped
public class LoginBean {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private Usuario usuario = new Usuario();
	private boolean isLogado = false;

	public String envia() {
		if (!usuario.getEmail().equals("admin") && !usuario.getSenha().equals("admin")) {
			usuario = usuarioDAO.getUsuario(usuario.getEmail(), usuario.getSenha());
			if (usuario == null) {
				usuario = new Usuario();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!", "Erro no Login!"));
				return null;
			} else {
				isLogado = true;
				return "cadastroUsuario";
			}
		} else {
			isLogado = true;
			return "cadastroUsuario";
		}

	}
	
	public String logout() {
		this.isLogado = false;
		return "login";
	}

	public boolean isLogado() {
		return isLogado;
	}

	public void setLogado(boolean isLogado) {
		this.isLogado = isLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}