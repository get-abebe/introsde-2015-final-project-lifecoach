package ehealth.models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ehealth.dao.HealthProfileSolutionDAO;

/**
 * 
 * Lets you communicate with database using HealthProfileSolutionDAO class.
 * Contains all database operations used by classes.
 * 
 * @author getch
 *
 */
public class PersistanceFunctions {

	// TODO ======= Advisor Operations ========
	/**
	 * Returns advice specified by id.
	 * 
	 * @param id
	 * @return {@link ehealth.model.Advisor}
	 * 
	 * @see ehealth.model.Advisor
	 */
	public static Advisor getAdviceById(int id) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		Advisor p = em.find(Advisor.class, id);
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Returns random advice
	 * 
	 * @return {@link ehealth.model.Advisor}
	 * 
	 * @see ehealth.model.Advisor
	 */
	public static Advisor getRandomAdvice() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		int rand = (int) (Math.random() * 100 + 1);
		Advisor p = em.find(Advisor.class, rand);
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Returns all advice
	 * 
	 * @return {@link ehealth.model.Advisor}
	 * 
	 * @see ehealth.model.Advisor
	 */
	public static List<Advisor> getAllAdvisor() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Advisor> list = em.createNamedQuery("Advisor.findAll",
				Advisor.class).getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}

	/**
	 * Saves advice.
	 * 
	 * @param advice
	 * @return {@link ehealth.model.Advisor}
	 * 
	 * @see ehealth.model.Advisor
	 */
	public static Advisor saveAdvice(Advisor advice) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(advice);
			tx.commit();
			HealthProfileSolutionDAO.instance.closeConnections(em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return advice;
	}

	/**
	 * Updates advice
	 * 
	 * @param advice
	 * @return {@link ehealth.model.Advisor}
	 * 
	 * @see ehealth.model.Advisor
	 */
	public static Advisor updateAdvice(Advisor advice) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		advice = em.merge(advice);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return advice;
	}

	/**
	 * Removes advice from database
	 * 
	 * @param advice
	 * 
	 * @see ehealth.model.Advisor
	 */
	public static void removeAdvice(Advisor advice) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		advice = em.merge(advice);
		em.remove(advice);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
	}

	// TODO ======= User Care Giver Operations ========
	/**
	 * Returns care giver specified by id.
	 * 
	 * @param id
	 * @return {@link ehealth.model.Caregiver}
	 * 
	 * @see ehealth.model.Caregiver
	 */
	public static Caregiver getCareGiverById(int id) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		Caregiver p = em.find(Caregiver.class, id);
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Returns all advice
	 * 
	 * @return {@link ehealth.model.Caregiver}
	 * 
	 * @see ehealth.model.Caregiver
	 */
	public static List<Caregiver> getAllCaregiver() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Caregiver> list = em.createNamedQuery("Caregiver.findAll",
				Caregiver.class).getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}

	/**
	 * Returns all care givers of a specific user
	 * 
	 * @param userId
	 * @return {@link ehealth.model.Caregiver}
	 * 
	 * @see ehealth.model.Caregiver
	 */
	public static List<Caregiver> getAllCareGiversOfUserId(int userId) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Caregiver> list = em.createQuery(
				"SELECT e FROM Caregiver e WHERE e.UPid = " + userId,
				Caregiver.class).getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}

	/**
	 * Saves care giver of a specific user
	 * 
	 * @param caregiver
	 * @param user
	 * 
	 * @see ehealth.model.Caregiver
	 * @see ehealth.model.Userprofile
	 */
	public static void addCareGiverOfUser(Userprofile caregiver,
			Userprofile user) {
		Caregiver newCG = new Caregiver();
		// newCG.setCareGiver(caregiver);
		// newCG.setUserprofile(user);

		saveCareGiver(newCG);
	}

	/**
	 * Saves care giver
	 * 
	 * @param caregiver
	 * @return {@link ehealth.model.Caregiver}
	 * 
	 * @see ehealth.model.Caregiver
	 */
	public static Caregiver saveCareGiver(Caregiver caregiver) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(caregiver);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return caregiver;
	}

	/**
	 * Updates care giver
	 * 
	 * @param caregiver
	 * @return {@link ehealth.model.Caregiver}
	 * 
	 * @see ehealth.model.Caregiver
	 */
	public static Caregiver updateCaregiver(Caregiver caregiver) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		caregiver = em.merge(caregiver);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return caregiver;
	}

	/**
	 * Delets care giver from database
	 * 
	 * @param caregiver
	 * 
	 * @see ehealth.model.Caregiver
	 */
	public static void removeCaregiver(Caregiver caregiver) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		caregiver = em.merge(caregiver);
		em.remove(caregiver);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
	}

	// TODO ======= User Current Life Statuses Operations ========
	/**
	 * Returns current life status specifies by cid
	 * 
	 * @param cid
	 * @return {@link ehealth.model.Currentlifestatus}
	 * 
	 * @see ehealth.model.Currentlifestatus
	 */
	public static Currentlifestatus getCurrentLifeStatusById(int cid) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		Currentlifestatus p = em.find(Currentlifestatus.class, cid);
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Returns all current life status
	 * 
	 * @return {@link ehealth.model.Currentlifestatus}
	 * 
	 * @see ehealth.model.Currentlifestatus
	 */
	public static List<Currentlifestatus> getAllCurrentlifestatus() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Currentlifestatus> list = em.createNamedQuery(
				"Currentlifestatus.findAll", Currentlifestatus.class)
				.getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}

	/**
	 * Returns current life status of user of specific type
	 * 
	 * @param userId
	 * @param measure
	 * @return {@link ehealth.model.Currentlifestatus}
	 * 
	 * @see ehealth.model.Currentlifestatus
	 */
	public static Currentlifestatus getCurrentLifeStatusOfUserOfMeasureType(
			int userId, int measure) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		Currentlifestatus c = (Currentlifestatus) em.createNativeQuery(
				"SELECT * FROM Currentlifestatus WHERE UPid=" + userId
						+ " AND MDid=" + measure, Currentlifestatus.class)
				.getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return c;
	}

	/**
	 * Saves current life status
	 * 
	 * @param currentlifestatus
	 * @return {@link ehealth.model.Currentlifestatus}
	 * 
	 * @see ehealth.model.Currentlifestatus
	 */
	public static Currentlifestatus saveCurrentLifeStatus(
			Currentlifestatus currentlifestatus) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(currentlifestatus);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return currentlifestatus;
	}

	/**
	 * Updates current life status
	 * 
	 * @param currentlifestatus
	 * @return {@link ehealth.model.Currentlifestatus}
	 * 
	 * @see ehealth.model.Currentlifestatus
	 */
	public static Currentlifestatus updateCurrentLifeStatus(
			Currentlifestatus currentlifestatus) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		currentlifestatus = em.merge(currentlifestatus);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return currentlifestatus;
	}

	/**
	 * Delets current life status
	 * 
	 * @param currentlifestatus
	 * 
	 * @see ehealth.model.Currentlifestatus
	 */
	public static void removeCurrentLifeStatus(
			Currentlifestatus currentlifestatus) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		currentlifestatus = em.merge(currentlifestatus);
		em.remove(currentlifestatus);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
	}

	// TODO ======= User Goals Operations ========
	/**
	 * Gets user's goal specified by goalId from database and returns
	 * 
	 * @param goalId
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 * 
	 */
	public static Goalsetting getUserGoalById(int goalId) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		Goalsetting p = em.find(Goalsetting.class, goalId);
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Gets all goals specified user from database and returns
	 * 
	 * @param userId
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	@SuppressWarnings("unchecked")
	public static List<Goalsetting> getUserGoalsByUserId(int userId) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Goalsetting> list = (List<Goalsetting>) em.createNativeQuery(
				"SELECT * FROM Goalsettings WHERE UPid=" + userId,
				Goalsetting.class).getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}

	/**
	 * Gets all goals specified user from database and returns
	 * 
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	public static List<Goalsetting> getAllGoalsetting() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Goalsetting> list = em.createNamedQuery("Goalsetting.findAll",
				Goalsetting.class).getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}

	/**
	 * Gets count of goals
	 * 
	 * @return int
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	public static int getCountGoalsetting() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Goalsetting> list = em.createNamedQuery("Goalsetting.findAll",
				Goalsetting.class).getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list.size();
	}

	/**
	 * Saves goal
	 * 
	 * @param goal
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	public static Goalsetting saveGoal(Goalsetting goal) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(goal);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return goal;
	}

	/**
	 * Updates goal
	 * 
	 * @param goal
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	public static Goalsetting updateGoal(Goalsetting goal) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		goal = em.merge(goal);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return goal;
	}

	/**
	 * Delets goal
	 * 
	 * @param goal
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	public static void removeGoal(Goalsetting goal) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		goal = em.merge(goal);
		em.remove(goal);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
	}

	// TODO ====== Health Measure History Operations ========
	/**
	 * Returns health measure history specified by id
	 * 
	 * @param id
	 * @return {@link ehealth.model.Healthmeasurehistory}
	 * 
	 * @see ehealth.model.Healthmeasurehistory
	 */
	public static Healthmeasurehistory getHealthMeasureHistoryById(int id) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		Healthmeasurehistory p = em.find(Healthmeasurehistory.class, id);
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Returns all health measure history
	 * 
	 * @return {@link ehealth.model.Healthmeasurehistory}
	 * 
	 * @see ehealth.model.Healthmeasurehistory
	 */
	public static List<Healthmeasurehistory> getAllHealthmeasurehistory() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Healthmeasurehistory> list = em.createNamedQuery(
				"Healthmeasurehistory.findAll", Healthmeasurehistory.class)
				.getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}

	/**
	 * Returns all health measure history of user specified by userId
	 * 
	 * @param userID
	 * @return {@link ehealth.model.Healthmeasurehistory}
	 * 
	 * @see ehealth.model.Healthmeasurehistory
	 */
	public static List<Healthmeasurehistory> getAllHistoryByUserId(int userId) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Healthmeasurehistory> list = (List<Healthmeasurehistory>) em
				.createNativeQuery(
						"SELECT * FROM Healthmeasurehistory WHERE UPid = "
								+ userId, Healthmeasurehistory.class)
				.getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}

	/**
	 * Saves health measure history
	 * 
	 * @param healthmeasurehistory
	 * @return {@link ehealth.model.Healthmeasurehistory}
	 * 
	 * @see ehealth.model.Healthmeasurehistory
	 */
	public static Healthmeasurehistory saveHealthMeasureHistory(
			Healthmeasurehistory healthmeasurehistory) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(healthmeasurehistory);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return healthmeasurehistory;
	}

	/**
	 * Updates health measure history
	 * 
	 * @param healthmeasurehistory
	 * @return {@link ehealth.model.Healthmeasurehistory}
	 * 
	 * @see ehealth.model.Healthmeasurehistory
	 */
	public static Healthmeasurehistory updateHealthMeasureHistory(
			Healthmeasurehistory healthmeasurehistory) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		healthmeasurehistory = em.merge(healthmeasurehistory);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return healthmeasurehistory;
	}

	/**
	 * Delete health measure history
	 * 
	 * @param id
	 * 
	 * @see ehealth.model.Healthmeasurehistory
	 */
	public static void removeHealthMeasureHistory(
			Healthmeasurehistory healthmeasurehistory) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		healthmeasurehistory = em.merge(healthmeasurehistory);
		em.remove(healthmeasurehistory);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
	}

	// TODO ======= User Profile Operation ========
	/**
	 * Gets user profile specified by personId from database and returns
	 * 
	 * @param personId
	 * @return {@link ehealth.model.Userprofile}
	 * 
	 * @see ehealth.model.Userprofile
	 */
	public static Userprofile getUserProfileById(int personId) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		Userprofile p = em.find(Userprofile.class, personId);
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Gets user profile specified by username from database and returns
	 * 
	 * @param username
	 * @return {@link ehealth.model.Userprofile}
	 * 
	 * @see ehealth.model.Userprofile
	 */
	public static Userprofile getUserProfileByUsername(String username) {
		EntityManager em = HealthProfileSolutionDAO.instance.createEntityManager();
		Userprofile p = (Userprofile) em.createQuery(
				"SELECT e FROM Userprofile e where e.username='" + username
						+ "'", Userprofile.class).getSingleResult();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Gets profile of all users from database and returns
	 * 
	 * @return {@link ehealth.model.Userprofile}
	 * 
	 * @see ehealth.model.Userprofile
	 */
	public static List<Userprofile> getAllUserprofile() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Userprofile> list = em.createNamedQuery("Userprofile.findAll",
				Userprofile.class).getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}

	/**
	 * Returns users count
	 * 
	 * @return int
	 * 
	 * @see ehealth.model.Userprofile
	 */
	public static int getCount() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Userprofile> list = em.createNamedQuery("Userprofile.findAll",
				Userprofile.class).getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list.size();
	}

	/**
	 * Saves user profile
	 * 
	 * @param p
	 * @return {@link ehealth.model.Userprofile}
	 * 
	 * @see ehealth.model.Userprofile
	 */
	public static Userprofile savePerson(Userprofile p) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Updates user profile
	 * 
	 * @param p
	 * @return {@link ehealth.model.Userprofile}
	 * 
	 * @see ehealth.model.Userprofile
	 */
	public static Userprofile updatePerson(Userprofile p) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return p;
	}

	/**
	 * Deletes user profile
	 * 
	 * @param p
	 * 
	 * @see ehealth.model.Userprofile
	 */
	public static void removePerson(Userprofile p) {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		em.remove(p);
		tx.commit();
		HealthProfileSolutionDAO.instance.closeConnections(em);
	}

	/**
	 * Saves GUID of user specified by username
	 * 
	 * @param username
	 * @param token
	 * @return java.lang.String
	 * 
	 * @see ehealth.model.Userprofile
	 */
	public static String saveGUID(String username, String token) {
		Userprofile p = getUserProfileByUsername(username);
		p.setGuid(token);
		Userprofile updated = updatePerson(p);
		return updated.getGuid();
	}

	// TODO ======= MeasureDefinition Operations ======
	/**
	 * Gets all Measures Definition from database and returns
	 * 
	 * @return {@link ehealth.model.Measuredefinition}
	 * 
	 * @see ehealth.model.Measuredefinition
	 */
	public static List<Measuredefinition> getAllMeasureDefinition() {
		EntityManager em = HealthProfileSolutionDAO.instance
				.createEntityManager();
		List<Measuredefinition> list = em.createNamedQuery(
				"Measuredefinition.findAll", Measuredefinition.class)
				.getResultList();
		HealthProfileSolutionDAO.instance.closeConnections(em);
		return list;
	}
}
