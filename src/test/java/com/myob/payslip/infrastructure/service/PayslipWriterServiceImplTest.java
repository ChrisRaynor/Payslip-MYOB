package com.myob.payslip.infrastructure.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

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
import com.myob.payslip.domain.PayslipDataFixture;
import com.myob.payslip.domain.repository.PayslipRepository;
import com.myob.payslip.domain.service.PayslipWriterService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MongoConfigurationTest.class })
public class PayslipWriterServiceImplTest {

	private PayslipWriterService payslipWriterService;

	@Autowired
	private PayslipRepository payslipRepository;

	@Before
	public void setUp() throws Exception {
		payslipWriterService = new PayslipWriterServiceImpl();
		ReflectionTestUtils.setField(payslipWriterService, "payslipRepository", payslipRepository);

		payslipRepository.deleteAll();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testSavePayslip() {
		assertNotNull(payslipWriterService);
		assertNotNull(payslipRepository);

		
		PayslipData payslipDataToStore = PayslipDataFixture.createLowPayEmployee();
		
		
		// test to see if this data is not already present in the repository.
		PayslipData payslipDataStored = payslipRepository.findById(payslipDataToStore.getId());
		assertNull("Result should be null", payslipDataStored);		
		
		
		String id = payslipWriterService.saveOrUpdatePayslip(payslipDataToStore);
		assertNotNull(id);
		assertEquals(payslipDataToStore.getId(), id);
		
		// test to see if the data was inserted as expected.
		payslipDataStored = payslipRepository.findById(id);
		assertNotNull("Result should not be null", payslipDataStored);
			
		assertEquals(payslipDataToStore.getEmployeeName(), payslipDataStored.getEmployeeName());
		assertEquals(payslipDataToStore.getPayPeriod(), payslipDataStored.getPayPeriod());
		assertEquals(payslipDataToStore.getGrossIncome(), payslipDataStored.getGrossIncome());
		assertEquals(payslipDataToStore.getIncomeTax(), payslipDataStored.getIncomeTax());
		assertEquals(payslipDataToStore.getNetIncome(), payslipDataStored.getNetIncome());
		assertEquals(payslipDataToStore.getSuperContribution(), payslipDataStored.getSuperContribution());
	}
	
	@Test
	public void testUpdatePayslip() {
		assertNotNull(payslipWriterService);
		assertNotNull(payslipRepository);

		
		PayslipData payslipDataToStore = PayslipDataFixture.createHighPayEmployee();
		
		
		// test to see if this data is not already present in the repository.
		PayslipData payslipDataStored = payslipRepository.findById(payslipDataToStore.getId());
		assertNull("Result should be null", payslipDataStored);		
		
		// Create the payslip
		String id = payslipWriterService.saveOrUpdatePayslip(payslipDataToStore);
		assertNotNull(id);
		assertEquals(payslipDataToStore.getId(), id);
		
		
		// Update the playslip.
		payslipDataToStore.setPayPeriod("01 May - 31 May");
		payslipWriterService.saveOrUpdatePayslip(payslipDataToStore);
		
		
		// test to see if the data was updated as expected.
		payslipDataStored = payslipRepository.findById(id);
		assertNotNull("Result should not be null", payslipDataStored);
			
		assertEquals(payslipDataToStore.getEmployeeName(), payslipDataStored.getEmployeeName());
		assertEquals(payslipDataToStore.getPayPeriod(), payslipDataStored.getPayPeriod());
		assertEquals(payslipDataToStore.getGrossIncome(), payslipDataStored.getGrossIncome());
		assertEquals(payslipDataToStore.getIncomeTax(), payslipDataStored.getIncomeTax());
		assertEquals(payslipDataToStore.getNetIncome(), payslipDataStored.getNetIncome());
		assertEquals(payslipDataToStore.getSuperContribution(), payslipDataStored.getSuperContribution());
	}	
	
	@Test
	public void testSaveOrUpdatePayslipWithNullPayslipData() {
		boolean exceptionThrown = false;
		
		try {
	    	PayslipData payslipDataToStore = null;
	    	payslipWriterService.saveOrUpdatePayslip(payslipDataToStore);
	    } catch (IllegalArgumentException anIllegalArgumentException) {
	    	exceptionThrown = true;
	    }
		
		if (!exceptionThrown) {
			fail("Expected IllegalArgumentException to be thrown");
		}			
	}	
		

}
