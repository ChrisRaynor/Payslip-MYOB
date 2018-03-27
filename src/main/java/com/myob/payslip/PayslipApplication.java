package com.myob.payslip;

import java.io.File;
import java.util.Arrays;

//import org.apache.commons.cli.CommandLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.myob.payslip.application.controller.PayslipController;


@SpringBootApplication
public class PayslipApplication {
	
	private static final String PAYROLL_DATA_FILE = "payroll.data.file";
	private static final Logger logger = LoggerFactory.getLogger(PayslipApplication.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PayslipApplication.class, args);

		logger.info("Application started with command-line arguments: {}", Arrays.toString(args));

		SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);

		String payrollFile = ps.getProperty(PAYROLL_DATA_FILE);
		logger.info("Using payroll file: " + payrollFile);

		PayslipController controller = ctx.getBean(PayslipController.class);
		try {
			controller.processMonthlyPayroll(new File(payrollFile));
		} catch (Exception e) {
			logger.error("Could not process payroll file: " + payrollFile);
		}
	}

}
