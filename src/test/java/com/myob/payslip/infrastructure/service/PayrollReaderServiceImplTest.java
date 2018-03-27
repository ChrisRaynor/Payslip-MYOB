package com.myob.payslip.infrastructure.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.myob.payslip.domain.PayrollData;
import com.myob.payslip.domain.service.PayrollReaderService;



@RunWith(SpringRunner.class)
public class PayrollReaderServiceImplTest {

	private PayrollReaderService payrollReaderService;
	
	
	@Before
	public void setUp() throws Exception {
		payrollReaderService = new PayrollReaderServiceImpl();
	}
	

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testReadPayrollDataSingleRow() {
		File payrollFile = new File("src/test/resources/payroll_data_single_row.csv");
		List<PayrollData> results = null;
		
		try {
			results = payrollReaderService.readPayrollData(payrollFile);
		} catch (FileNotFoundException e) {
			fail("Did not expect FileNotFoundException to be thrown");
		} catch (IOException e) {
			fail("Did not expect IOException to be thrown");
		}
		
		
		assertNotNull("Result should not be null", results);
		assertTrue("Should contain 1 result", results.size() == 1);
		
		// Test first result.
		PayrollData payrollData = results.get(0);
		assertEquals("James", payrollData.getFirstName());
		assertEquals("Brown", payrollData.getLastName());
		assertEquals(60050.00f, payrollData.getAnnualSalary().doubleValue(), 2);
		assertEquals(0.09f, payrollData.getSuperRate().doubleValue(), 2);
		assertEquals("01 March - 31 March", payrollData.getPayPeriod());
	}
	
	@Test
	public void testReadPayrollDataMultipleRows() {
		File payrollFile = new File("src/test/resources/payroll_data_multiple_rows.csv");
		List<PayrollData> results = null;
		
		try {
			results = payrollReaderService.readPayrollData(payrollFile);
		} catch (FileNotFoundException e) {
			fail("Did not expect FileNotFoundException to be thrown");
		} catch (IOException e) {
			fail("Did not expect IOException to be thrown");
		}
		
		
		assertNotNull("Result should not be null", results);
		assertTrue("Should contain 2 results", results.size() == 2);
		
		// Test first result.
		PayrollData payrollData = results.get(0);
		assertEquals("James", payrollData.getFirstName());
		assertEquals("Brown", payrollData.getLastName());
		assertEquals(60050.00f, payrollData.getAnnualSalary().doubleValue(), 2);
		assertEquals(0.09f, payrollData.getSuperRate().doubleValue(), 2);
		assertEquals("01 March - 31 March", payrollData.getPayPeriod());
		
		// Test second result.
		payrollData = results.get(1);
		assertEquals("Roger", payrollData.getFirstName());
		assertEquals("Williams", payrollData.getLastName());
		assertEquals(120000.00f, payrollData.getAnnualSalary().doubleValue(), 2);
		assertEquals(0.10f, payrollData.getSuperRate().doubleValue(), 2);
		assertEquals("01 May - 31 May", payrollData.getPayPeriod());
	}
	
	@Test
	public void testShouldFailWhenPayrollFileIsNull() {
		File payrollFile = null;
				
		boolean exceptionThrown = false;
		
		try {
			payrollReaderService.readPayrollData(payrollFile);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
		} catch (FileNotFoundException e) {
			fail("Did not expect FileNotFoundException to be thrown");
		} catch (IOException e) {
			fail("Did not expect IOException to be thrown");
		}

		if (!exceptionThrown) {
			fail("Expected IllegalArgumentException to be thrown");
		}		
	}	
	
	@Test
	public void testShouldFailWhenPayrollFileIsMissing() {
		File payrollFile = new File("src/test/resources/payroll_data_missing.csv");
				
		boolean exceptionThrown = false;
		
		try {
			payrollReaderService.readPayrollData(payrollFile);
		} catch (FileNotFoundException e) {
			exceptionThrown = true;
		} catch (IOException e) {
			fail("Did not expect IOException to be thrown");
		}
		
		if (!exceptionThrown) {
			fail("Expected FileNotFoundException to be thrown");
		}
	}

	@Test
	public void testShouldFailWhenPayrollFileIsUnreadable() {
		File payrollFile = new File("src/test/resources/payroll_data_bad.csv");
				
		boolean exceptionThrown = false;
		
		try {
			payrollReaderService.readPayrollData(payrollFile);
		} catch (FileNotFoundException e) {
			fail("Did not expect FileNotFoundException to be thrown");
		} catch (IOException e) {
			exceptionThrown = true;
		}
		
		if (!exceptionThrown) {
			fail("Expected FileNotFoundException to be thrown");
		}
	}
}
