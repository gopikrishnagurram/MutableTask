package com.wavelabs.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.wavelabs.model.Car;
import com.wavelabs.utility.Helper;

/**
 * This class Provides static methods to perform createCar, updateCar operations
 * on {@link Car}.
 * 
 * @author gopikrishnag
 *
 */
public class CarService {

	/**
	 * <h3>Persist car objects in table</h3>
	 * <ul>
	 * <li>creates Car object</li>
	 * <li>set properties to Car</li>
	 * <li>Persist Car object</li>
	 * </ul>
	 * 
	 * @param id
	 *            of Car
	 * @param name
	 *            of Car
	 * @param cost
	 *            of Car
	 * @param mileage
	 *            of Car
	 */

	public static void createCar(int id, String name, int cost, int mileage) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Car c1 = new Car();
		c1.setId(id);
		c1.setName(name);
		c1.setCost(cost);
		c1.setMileage(mileage);
		session.save(c1);
		tx.commit();
		session.close();

	}

	/**
	 * <h3>Update Persisted Car mileage property of given id.</h3>
	 * <ul>
	 * <li>Load Persisted object from of matching id</li>
	 * <li>set new mileage property</li>
	 * <li>update changes to persisted object</li>
	 * </ul>
	 * 
	 * @param id
	 *            of Car
	 * @param mileage
	 *            of Car
	 */
	public static void updateCarMileage(int id, int mileage) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Car c1 = (Car) session.get(Car.class, id);
		c1.setMileage(mileage);
		tx.commit();
		session.close();
	}
}
