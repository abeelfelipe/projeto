package com.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.projeto.dao.UsuarioDAO;
import com.projeto.entidades.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private UsuarioDAO dao;
	private List<Usuario> UsuarioList;

	@PostConstruct
	public void init() {
		this.usuario = new Usuario();
		this.dao = new UsuarioDAO();
		this.UsuarioList = dao.list();
	}

	public String newUsuario() {
		return "cadastroUsuario";
	}

	public String delete(Usuario usuario) {
		dao.delete(usuario);
		UsuarioList.remove(usuario);
		return "cadastroUsuario";
	}

	public String edit(Usuario usuario) {
		this.usuario = usuario;
		return "cadastroUsuario";
	}

	public String save() {
		List<Usuario> verificaEmailCadastrado = dao.listUsuarioPorEmail(usuario.getEmail());
		if (verificaEmailCadastrado != null && verificaEmailCadastrado.isEmpty()) {
			if ((usuario.getEmail() != null && !usuario.getEmail().isEmpty())
					|| usuario.getNome() != null && !usuario.getNome().isEmpty()
					|| usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
				dao.saveOrUpdate(usuario);
				this.usuario = new Usuario();
				return "cadastroUsuario";
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Campos não podem ser nulos."));
				return "cadastroUsuario";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Já existe usuário com este email."));
			return "cadastroUsuario";
		}
	}

	// Geters and seters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarioList() {
		this.UsuarioList = dao.list();
		return UsuarioList;
	}

	public void setUsuarioList(List<Usuario> usuarioList) {
		UsuarioList = usuarioList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}