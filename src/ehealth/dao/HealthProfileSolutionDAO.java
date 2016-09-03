/**
 * 
 */
package ehealth.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
/**
 * @author getch
 *
 */
public enum HealthProfileSolutionDAO {
	instance;
	private EntityManagerFactory emf;

	private HealthProfileSolutionDAO() {
		if (emf != null) {
			emf.close();
		}
		emf = Persistence.createEntityManagerFactory("VirtualLifeCoach-jpa");
	}

	public EntityManager createEntityManager() {
		try {
			return emf.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeConnections(EntityManager em) {
		em.close();
	}

	public EntityTransaction getTransaction(EntityManager em) {
		return em.getTransaction();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
}