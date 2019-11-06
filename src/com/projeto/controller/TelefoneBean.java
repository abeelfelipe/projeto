package com.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.projeto.dao.TelefoneDAO;
import com.projeto.entidades.Telefone;
import com.projeto.entidades.Usuario;

@ManagedBean
@SessionScoped
public class TelefoneBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Telefone telefone;
	private TelefoneDAO dao;
	private List<Telefone> telefoneList;

	@PostConstruct
	public void init() {
		this.telefone = new Telefone();
		this.dao = new TelefoneDAO();
	}

	public String cadastroTelefone(Usuario usuario) {
		telefone.setUsuario(usuario);
		this.telefoneList = dao.list(usuario.getId());
		return "/cadastroTelefone";
	}

	public String save() {
		if (telefone.getNumero().matches("[0-9]+")) {
			if (telefone.getDdd() != null || (telefone.getNumero() != null && !telefone.getNumero().isEmpty())) {
				dao.saveOrUpdate(telefone);
				Usuario usuario = telefone.getUsuario();
				this.telefone = new Telefone();
				this.telefone.setUsuario(usuario);
				this.getTelefoneList();
				return "/cadastroTelefone";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Warning!", "Campos Numero e DDD não podem ser vazio."));
				return "/cadastroTelefone";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Numero inválido."));
			return "/cadastroTelefone";
		}
	}

	public String delete(Telefone telefone) {
		dao.delete(telefone);
		telefoneList.remove(telefone);
		return "cadastroTelefone";
	}

	public String edit(Telefone telefone) {
		this.telefone = telefone;
		return "cadastroTelefone";
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Telefone> getTelefoneList() {
		this.telefoneList = dao.list(telefone.getUsuario().getId());
		return telefoneList;
	}

	public void setTelefoneList(List<Telefone> telefoneList) {
		this.telefoneList = telefoneList;
	}

}
