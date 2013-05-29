package com.corejsf;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CredentialManager {
	@PersistenceContext(unitName="default")
	private EntityManager em;
	
	public int checkCredential(String name, String password) {
		TypedQuery<Credential> query = em.createQuery(
				"SELECT c FROM Credential c WHERE c.username = :username",
				Credential.class);
		query.setParameter("username", name);
		List<Credential> result = query.getResultList();
		
		if (result.size() != 1) {
			return 0;
		}
		
		Credential credential = result.get(0);
		if (credential.getPassword().equals(password)) {
			return credential.incrementLoginCount();
		} else {
			return 0;
		}
	}
}
