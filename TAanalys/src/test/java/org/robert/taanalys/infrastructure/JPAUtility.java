package org.robert.taanalys.infrastructure;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.derby.impl.io.VFMemoryStorageFactory;
import org.robert.taanalys.domain.model.InstrumentEvent;

/**
 * Utility class used when testing with a database and JPA.
 * 
 * @author Robert Georen.
 * 
 */
public class JPAUtility {
	private static Logger logger = Logger.getLogger(JPAUtility.class.getName());
	private static boolean databaseCreated;
	protected static EntityManagerFactory emFactory;

	/**
	 * Start the Derby in-memory database.
	 */
	public static void startInMemoryDerbyDatabase() {
		try {
			logger.fine("Starting in-memory database for unit tests");

			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			// Check if database is already created.
			if (!databaseCreated) {
				logger.fine("Create new database instance.");
				DriverManager.getConnection(
						"jdbc:derby:memory:unit-testing-jpa;create=true")
						.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.severe("Exception during database startup. Message = "
					+ ex.getMessage());
		}
	}

	/**
	 * Shutdown the Derby In-memory database. There is some strange feature in
	 * Derby that makes this not work propely when try to create a new database
	 * with the same name. Some type of clean-up error.
	 * 
	 * @param em
	 *            to close before shutdown database.
	 */
	public static void shutdownInMemoryDerbyDatabase(EntityManager em) {
		logger.fine("Shuting down Hibernate JPA layer.");

		if (em != null) {
			em.close();
		}

		logger.fine("Stopping in-memory database.");

		try {
			DriverManager.getConnection(
					"jdbc:derby:memory:unit-testing-jpa;shutdown=true").close();
		} catch (SQLNonTransientConnectionException ex) {
			if (ex.getErrorCode() != 45000) {
				throw new RuntimeException(ex.getMessage());
			}
			// Shutdown success
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

		try {
			VFMemoryStorageFactory.purgeDatabase(new File("unit-testing-jpa")
					.getCanonicalPath());
		} catch (IOException e) {
			logger.severe("Error removing directory: Message = "
					+ e.getMessage());
		}
	}

	/**
	 * Create a new entity manager.
	 * 
	 * @return
	 */
	public static EntityManager createEntityManager() {
		try {
			logger.fine("Building JPA EntityManager for unit tests");
			emFactory = Persistence.createEntityManagerFactory("testPU");
			EntityManager em = emFactory.createEntityManager();

			logger.fine("JPA EntityManager for unit tests created.");
			return em;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.severe("Exception during JPA EntityManager instanciation.");
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * create an entity manager from current EntityManagerFactory.
	 */
	public static EntityManager createEntityMangerFromCurrentFactory() {
		try {
			logger.fine("Building JPA EntityManager for unit tests from current EntityManagerFactory.");
			EntityManager em = emFactory.createEntityManager();

			logger.fine("JPA EntityManager for unit tests created.");
			return em;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.severe("Exception during JPA EntityManager instanciation.");
			throw new RuntimeException(ex.getMessage());
		}
	}

	public static void remove(InstrumentEvent data, EntityManager em) {
		em.getTransaction().begin();
		em.remove(data);
		em.getTransaction().commit();
	}
}
