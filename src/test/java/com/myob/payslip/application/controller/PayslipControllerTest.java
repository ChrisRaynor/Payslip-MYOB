package com.myob.payslip.application.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.myob.payslip.application.config.MongoConfigurationTest;
import com.myob.payslip.domain.PayslipData;
import com.myob.payslip.domain.repository.PayslipRepository;
import com.myob.payslip.domain.service.PayrollReaderService;
import com.myob.payslip.domain.service.PayslipGeneratorService;
import com.myob.payslip.domain.service.PayslipWriterService;
import com.myob.payslip.infrastructure.service.PayrollReaderServiceImpl;
import com.myob.payslip.infrastructure.service.PayslipGeneratorServiceImpl;
import com.myob.payslip.infrastructure.service.PayslipWriterServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MongoConfigurationTest.class })
public class PayslipControllerTest {

	private PayslipController payslipController;
		
	private PayrollReaderService payrollReaderService;
	
	private PayslipGeneratorService payslipGeneratorService;	
	
	private PayslipWriterService payslipWriterService;
	
	@Autowired
	private PayslipRepository payslipRepository;	
	
	@Before
	public void setUp() throws Exception {
		payrollReaderService = new PayrollReaderServiceImpl(); 
		payslipGeneratorService = new PayslipGeneratorServiceImpl();
		
		payslipWriterService = new PayslipWriterServiceImpl();
		ReflectionTestUtils.setField(payslipWriterService, "payslipRepository", payslipRepository);
		
		
		payslipController = new PayslipController();
		ReflectionTestUtils.setField(payslipController, "payrollReaderService", payrollReaderService);
		ReflectionTestUtils.setField(payslipController, "payslipGeneratorService", payslipGeneratorService);
		ReflectionTestUtils.setField(payslipController, "payslipWriterService", payslipWriterService);

		payslipRepository.deleteAll();		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcessMonthlyPayrollSingleRow() {
		File payrollFile = new File("src/test/resources/payroll_data_single_row.csv");
		
		try {
			payslipController.processMonthlyPayroll(payrollFile);
		} catch (Exception e) {
			fail("Did not expect Exception to be thrown");
		}
		
		
		// test to see if the employee was inserted correctly into the database.
		List<PayslipData> results = payslipRepository.findByEmployeeName("James Brown");
		assertNotNull("Result should not be null", results);
		assertTrue("Should contain 1 result", results.size() == 1);
		PayslipData payslipDataStored = results.get(0);
		assertEquals("James Brown", payslipDataStored.getEmployeeName());
		assertEquals("01 March - 31 March", payslipDataStored.getPayPeriod());
		assertEquals(5004.00f, payslipDataStored.getGrossIncome().doubleValue(), 2);
		assertEquals(922.00f, payslipDataStored.getIncomeTax().doubleValue(), 2);
		assertEquals(4082.00f, payslipDataStored.getNetIncome().doubleValue(), 2);
		assertEquals(450.00f, payslipDataStored.getSuperContribution().doubleValue(), 2);
	}
	
	@Test
	public void testProcessMonthlyPayrollMultipleRows() {
		File payrollFile = new File("src/test/resources/payroll_data_multiple_rows.csv");
		
		try {
			payslipController.processMonthlyPayroll(payrollFile);
		} catch (Exception e) {
			fail("Did not expect Exception to be thrown");
		}
		
		
		// Test to see if first employee was inserted correctly into the database.
		List<PayslipData> results = payslipRepository.findByEmployeeName("James Brown");
		assertNotNull("Result should not be null", results);
		assertTrue("Should contain 1 result", results.size() == 1);
		PayslipData payslipDataStored = results.get(0);
		assertEquals("James Brown", payslipDataStored.getEmployeeName());
		assertEquals("01 March - 31 March", payslipDataStored.getPayPeriod());
		assertEquals(5004.00f, payslipDataStored.getGrossIncome().doubleValue(), 2);
		assertEquals(922.00f, payslipDataStored.getIncomeTax().doubleValue(), 2);
		assertEquals(4082.00f, payslipDataStored.getNetIncome().doubleValue(), 2);
		assertEquals(450.00f, payslipDataStored.getSuperContribution().doubleValue(), 2);
		
		// Test to see if  second employee was inserted correctly into the database.
		results = payslipRepository.findByEmployeeName("Roger Williams");
		assertNotNull("Result should not be null", results);
		assertTrue("Should contain 1 result", results.size() == 1);
		payslipDataStored = results.get(0);
		assertEquals("Roger Williams", payslipDataStored.getEmployeeName());
		assertEquals("01 May - 31 May", payslipDataStored.getPayPeriod());
		assertEquals(10000.00f, payslipDataStored.getGrossIncome().doubleValue(), 2);
		assertEquals(2669.00f, payslipDataStored.getIncomeTax().doubleValue(), 2);
		assertEquals(7331.00f, payslipDataStored.getNetIncome().doubleValue(), 2);
		assertEquals(1000.00f, payslipDataStored.getSuperContribution().doubleValue(), 2);	
	}
	
	@Test
	public void testShouldFailWhenPayrollFileIsNull() {
		File payrollFile = null;
				
		boolean exceptionThrown = false;
		
		try {
			payslipController.processMonthlyPayroll(payrollFile);
		} catch (Exception e) {
			exceptionThrown = true;
		} 
		
		if (!exceptionThrown) {
			fail("Expected Exception to be thrown");
		}		
	}	
	
	@Test
	public void testShouldFailWhenPayrollFileIsMissing() {
		File payrollFile = new File("src/test/resources/payroll_data_missing.csv");
				
		boolean exceptionThrown = false;
		
		try {
			payslipController.processMonthlyPayroll(payrollFile);
		} catch (Exception e) {
			exceptionThrown = true;
		} 
		
		if (!exceptionThrown) {
			fail("Expected Exception to be thrown");
		}
	}

	@Test
	public void testShouldFailWhenPayrollFileIsUnreadable() {
		File payrollFile = new File("src/test/resources/payroll_data_bad.csv");
				
		boolean exceptionThrown = false;
		
		try {
			payslipController.processMonthlyPayroll(payrollFile);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		
		if (!exceptionThrown) {
			fail("Expected Exception to be thrown");
		}
	}	

}
