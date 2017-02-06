package com.wavelabs.service;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.wavelabs.metadata.HbmFileMetaData;
import com.wavelabs.model.Car;
import com.wavelabs.service.CarService;
import com.wavelabs.tableoperations.CRUDTest;
import com.wavelabs.utility.Helper;

/**
 * Provides test cases to all {@link CarService} class methods.
 * @author gopikrishnag
 *
 */
public class CarServiceTest {

	CRUDTest crud = null;

	/**
	 * <p>
	 * Initializes {@link HbmFileMetaData}, {@link CRUDTest} Class objects. This
	 * objects useful through out all unit test cases.
	 * </p>
	 */
	@BeforeTest
	public void intillization() {
		crud = new CRUDTest(Helper.getSessionFactory(), Helper.getConfiguration(), Helper.getSession());
	}

	/**
	 * Checks the {@linkplain CarService#createCar(int, String, int, int)} method
	 *  inserting records in table or not, If method fails to insert record in table test case will fail.
	 */
	@Test(priority = 1, description = "verifies createCar method in CarService inserting records or not")
	public void testCreateCar() {
		CarService.createCar(1, "A", 10, 15);
		CarService.createCar(2, "B", 11, 25);
		
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isRecordInserted(Car.class, 1), true,
				"createCar method in CarService fails to insert a record in table");
		Assert.assertEquals(crud.isRecordInserted(Car.class, 2), true,
				"createCar method in CarService fails to insert a record in table");
	}

	/**
	 * Checks {@linkplain CarService#updateCarMileage(int, int)} method in
	 * CarService update records in table or not. If method fails to update record in table test case will fail. 
	 */
	@Test(priority = 2, description = " verifies updateCar method in CarService updating records or not")
	public void testUpdateCar() {
		CarService.updateCarMileage(1, 79);
		crud.setSession(Helper.getSession());
		Assert.assertEquals((crud.isColumnUpdated(Car.class, "mileage", 79, 1)), true,
				"updateCarMileage method in CarService class fails to update record");
	}

	/**
	 * Closes SessionFactory
	 */
	@AfterTest
	public void closeResources() {
		Helper.getSessionFactory().close();
	}
}
