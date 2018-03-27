package com.myob.payslip.application.controller;



import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myob.payslip.domain.PayrollData;
import com.myob.payslip.domain.service.PayrollReaderService;
import com.myob.payslip.domain.service.PayslipGeneratorService;
import com.myob.payslip.domain.service.PayslipWriterService;

@Component
public class PayslipController {
	
	private static final Logger logger = LoggerFactory.getLogger(PayslipController.class);
	
	@Autowired
	private PayrollReaderService payrollReaderService;
	
	@Autowired
	private PayslipGeneratorService payslipGeneratorService;
	
	@Autowired
	private PayslipWriterService payslipWriterService;
	
		
	public void processMonthlyPayroll(File payrollFile) throws Exception  {
		
		logger.info("Processing payroll file: " + payrollFile);
		
		
		List<PayrollData> payrollData = null;
		try {
			payrollData = payrollReaderService.readPayrollData(payrollFile);
		} catch (IOException e) {
			logger.error("Could not read payroll data file");
			throw e;
		}
		

		payrollData.forEach((data)->payslipWriterService.saveOrUpdatePayslip(payslipGeneratorService.generatePayslip(data)));
	}

}
