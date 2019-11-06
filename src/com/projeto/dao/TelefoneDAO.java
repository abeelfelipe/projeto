package com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.projeto.entidades.Telefone;
import com.projeto.util.HibernateUtil;

public class TelefoneDAO {
	public void saveOrUpdate(Telefone telefone) {
		EntityManager em = HibernateUtil.geteEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			if (telefone.getId() == null)
				em.persist(telefone);
			else
				em.merge(telefone);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	public void delete(Telefone telefone) {
		EntityManager em = HibernateUtil.geteEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Telefone al = em.find(Telefone.class, telefone.getId());
			em.remove(al);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	public List<Telefone> list(Long id) {
		List<Telefone> result = null;
		try {
			EntityManager em = HibernateUtil.geteEntityManagerFactory().createEntityManager();
			Query query = em.createQuery("SELECT t FROM Telefone t WHERE t.usuario.id = :id");
			query.setParameter("id", id);
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
