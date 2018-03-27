package com.myob.payslip.infrastructure.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.myob.payslip.domain.PayrollData;
import com.myob.payslip.domain.PayrollDataFixture;
import com.myob.payslip.domain.PayslipData;
import com.myob.payslip.domain.service.PayslipGeneratorService;

public class PayslipGeneratorServiceImplTest {


	private PayslipGeneratorService payslipGeneratorService;
	
	
	@Before
	public void setUp() throws Exception {
		payslipGeneratorService = new PayslipGeneratorServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public void testGeneratePayslipForLowPayEmployee() {
		PayrollData payrollData = PayrollDataFixture.createLowPayEmployee();
		
		PayslipData payslipData = payslipGeneratorService.generatePayslip(payrollData);
		assertNotNull("Result should not be null", payslipData);	
		
		// Check to see if it generated the expected results.
		assertEquals(payrollData.getFirstName() + " " + payrollData.getLastName(), payslipData.getEmployeeName());
		assertEquals(payrollData.getPayPeriod(), payslipData.getPayPeriod());
		assertEquals(5004.00f, payslipData.getGrossIncome().doubleValue(), 2);
		assertEquals(922.00f, payslipData.getIncomeTax().doubleValue(), 2);
		assertEquals(4082.00f, payslipData.getNetIncome().doubleValue(), 2);
		assertEquals(450.00f, payslipData.getSuperContribution().doubleValue(), 2);
	}
	
	@Test
	public void testGeneratePayslipForHightPayEmployee() {
		PayrollData payrollData = PayrollDataFixture.createHighPayEmployee();
		
		PayslipData payslipData = payslipGeneratorService.generatePayslip(payrollData);
		assertNotNull("Result should not be null", payslipData);	
		
		// Check to see if it generated the expected results.
		assertEquals(payrollData.getFirstName() + " " + payrollData.getLastName(), payslipData.getEmployeeName());
		assertEquals(payrollData.getPayPeriod(), payslipData.getPayPeriod());
		assertEquals(10000.00f, payslipData.getGrossIncome().doubleValue(), 2);
		assertEquals(2669.00f, payslipData.getIncomeTax().doubleValue(), 2);
		assertEquals(7331.00f, payslipData.getNetIncome().doubleValue(), 2);
		assertEquals(1000.00f, payslipData.getSuperContribution().doubleValue(), 2);
	}	
	
	@Test
	public void testGeneratePayslipWithNullPayslipData() {
		boolean exceptionThrown = false;
		
		try {
			PayrollData payrollData = null;
	    	payslipGeneratorService.generatePayslip(payrollData);
	    } catch (IllegalArgumentException anIllegalArgumentException) {
	    	exceptionThrown = true;
	    }
		
		if (!exceptionThrown) {
			fail("Expected IllegalArgumentException to be thrown");
		}			
	}	

}
