package com.wavelabs.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wavelabs.model.Phone;
import com.wavelabs.utility.Helper;

/**
 * Provides methods for creation and update {@link Phone} records.
 * <p>
 * Persist transient object, update the persisted object
 * </p>
 * 
 * @author gopikrishnag
 */

public class PhoneService {
	/**
	 * <h3>update persist phone object</h3>
	 * <ul>
	 * <li>Loads Phone object from table
	 * </li>
	 * <li>set the new properties</li>
	 * <li>update persist object</li>
	 * </ul>
	 * 
	 * @param id
	 *            of phone
	 * @param name
	 *            of phone
	 * @param version
	 *            of phone
	 * @param cost
	 *            of phone
	 */
	public static void updatePhone(int id, String name, String version, double cost) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Phone phone = (Phone) session.get(Phone.class, id);
		phone.setCost(cost);
		phone.setName(name);
		phone.setVersion(version);
		session.update(phone);
		tx.commit();
		session.close();
	}

	/**
	 * <h3>Persist the Phone object</h3>
	 * <ul>
	 * <li>Creates transient Phone object</li>
	 * <li>sets the values to object</li>
	 * <li>persist the object</li>
	 * </ul>
	 * 
	 * @param id
	 *            of Phone
	 * @param name
	 *            of Phone
	 * @param version
	 *            of Phone
	 * @param cost
	 *            of Phone
	 */
	public static void createPhone(int id, String name, String version, double cost) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Phone p1 = new Phone();
		p1.setId(id);
		p1.setName(name);
		p1.setVersion(version);
		p1.setCost(cost);
		session.save(p1);
		session.flush();
		tx.commit();
		session.close();
	}
}
