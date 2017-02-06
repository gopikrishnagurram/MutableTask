package com.wavelabs.service;


import org.hibernate.internal.util.xml.XmlDocument;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wavelabs.metadata.ClassAttributes;
import com.wavelabs.metadata.HbmFileMetaData;
import com.wavelabs.metadata.XmlDocumentBuilder;
import com.wavelabs.model.Phone;
import com.wavelabs.service.PhoneService;
import com.wavelabs.tableoperations.CRUDTest;
import com.wavelabs.utility.Helper;

/**
 * checks all methods in {@link PhoneService} class using unit test cases.
 * 
 * @author gopikrishnag
 *
 */
public class PhoneServiceTest {

	private HbmFileMetaData phoneHbm = null;
	private CRUDTest crud = null;

	/**
	 * <p>
	 * Initializes {@link HbmFileMetaData}, {@link CRUDTest} Class objects. This
	 * objects useful through out all unit test cases.
	 * </p>
	 */
	@BeforeTest
	public void intillization() {
		XmlDocumentBuilder xdb = new XmlDocumentBuilder();
		XmlDocument xd = xdb.getXmlDocumentObject("src/main/resources/com/wavelabs/model/Phone.hbm.xml");
		phoneHbm = new HbmFileMetaData(xd, Helper.getSessionFactory());
		crud = new CRUDTest(Helper.getSessionFactory(), Helper.getConfiguration(), Helper.getSession());
	}

	/**
	 * Tests mutable attribute value on Phone domain mapping.
	 * <p>
	 * {@code mutable = "false"} test case will pass
	 * </p>
	 * <p>
	 * {@code mutable = "true"} test case will faile
	 * </p>
	 */
	@Test(priority = 1, description = "Verifies the value of mutable for Phone class")
	public void testMutable() {
		Assert.assertEquals("false", phoneHbm.getClassAttribute(ClassAttributes.mutable),
				"mutable attribute of Phone class is not mapped correctly");
	}

	/**
	 * Provides data to {@link #testCreatePhone(int, String, String, double)}
	 * method.
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "records")
	public Object[][] dbForRecords() {
		Object obj[][] = { { 1, "NOKIA", "1100", 2000 }, { 2, "LUMIA", "12f", 12000 }, { 3, "REDME", "1.2ge", 22000 } };
		return obj;
	}

	/**
	 * takes input from data-provider, checks
	 * {@link PhoneService#createPhone(int, String, String, double)} method
	 * inserting records in table.
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
	@Test(dataProvider = "records", priority = 2, description = "verfies insertion of phone record in table", dependsOnMethods = "testMutable")
	public void testCreatePhone(int id, String name, String version, double cost) {
		PhoneService.createPhone(id, name, version, cost);
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isRecordInserted(Phone.class, id), true,
				"createPhone(int, String, String, double) fails to insert records in table");
	}

	/**
	 * Checks {@linkplain PhoneService#updatePhone(int, String, String, double)}
	 * method updating record or not in table. As per challenge requirement, it
	 * should not update record because we disable mutable nature to Phone
	 */
	@Test(priority = 3, description = "Verifies update of records in table", dependsOnMethods = "testCreatePhone")
	public void testUpdateProperty() {
		PhoneService.updatePhone(1, "nokia", "1112", 2500);
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isColumnUpdated(Phone.class, "version", "152", 1), false,
				"updatePhone(int, String, String, dobule) method updates record in table. Check mapping configuration");
	}

	/**
	 * Closing of SessionFactory.
	 */
	@AfterTest
	public void closeResources() {
		try {
			if (!Helper.getSessionFactory().isClosed()) {
				Helper.getSessionFactory().close();
			}
		} catch (Exception e) {
		}
	}
}
