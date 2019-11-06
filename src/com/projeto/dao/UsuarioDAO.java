package com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.projeto.entidades.Telefone;
import com.projeto.entidades.Usuario;
import com.projeto.util.HibernateUtil;

public class UsuarioDAO {
	public Usuario getUsuario(String email, String senha) {
		EntityManager em = HibernateUtil.geteEntityManagerFactory().createEntityManager();
	      try {
	        Usuario usuario = (Usuario) em
	         .createQuery(
	             "SELECT u from Usuario u where u.email = :email and u.senha = :senha")
	         .setParameter("email", email)
	         .setParameter("senha", senha).getSingleResult();
	 
	        return usuario;
	      } catch (NoResultException e) {
	            return null;
	      }
	    }
	
	public void saveOrUpdate(Usuario usuario) {
		EntityManager em = HibernateUtil.geteEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			if (usuario.getId() == null)
				em.persist(usuario);
			else
				em.merge(usuario);
			em.getTransaction().commit();
			usuario = new Usuario();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	public void delete(Usuario usuario) {
		EntityManager em = HibernateUtil.geteEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Usuario al = em.find(Usuario.class, usuario.getId());
			em.remove(al);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	public List<Usuario> list() {
		List<Usuario> result = null;
		try {
			EntityManager em = HibernateUtil.geteEntityManagerFactory().createEntityManager();
			Query query = em.createQuery("FROM Usuario");
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Usuario> listUsuarioPorEmail(String email) {
		List<Usuario> result = null;
		try {
			EntityManager em = HibernateUtil.geteEntityManagerFactory().createEntityManager();
			Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
			query.setParameter("email", email);
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
