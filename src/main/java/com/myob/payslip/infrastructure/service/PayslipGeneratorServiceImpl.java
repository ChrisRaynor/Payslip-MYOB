package com.myob.payslip.infrastructure.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myob.payslip.domain.PayrollData;
import com.myob.payslip.domain.PayslipData;
import com.myob.payslip.domain.TaxBracket;
import com.myob.payslip.domain.service.PayslipGeneratorService;
import com.myob.payslip.domain.util.IncomeTaxCalulator;

@Service
public class PayslipGeneratorServiceImpl implements PayslipGeneratorService {

	private static final Logger logger = LoggerFactory.getLogger(PayslipGeneratorServiceImpl.class);

	private List<TaxBracket> taxBrackets;


	public PayslipGeneratorServiceImpl() {
		super();

		taxBrackets = new ArrayList<TaxBracket>();

		// Note - this data would not be hard-coded in a production application!
		taxBrackets.add(new TaxBracket(0.0f, 18200f));
		taxBrackets.add(new TaxBracket(0.19f, 37000f));
		taxBrackets.add(new TaxBracket(0.325f, 87000f));
		taxBrackets.add(new TaxBracket(0.37f, 180000f));
		taxBrackets.add(new TaxBracket(00.45f, null));
	}

	@Override
	public PayslipData generatePayslip(PayrollData payrollData) {
		if (payrollData == null) {
			throw new IllegalArgumentException("Payroll data cannot be null");
		}

		double annualSalary = payrollData.getAnnualSalary();
		double superRate = payrollData.getSuperRate();
		double monthlyGrossIncome = (float) Math.floor(annualSalary / 12.0f);

		logger.info("annual salary = " + annualSalary);
		logger.info("super rate = " + superRate);
		logger.info("monthly gross income = " + monthlyGrossIncome);

		// Poplulate the payslip data bean.
	    PayslipData data = new PayslipData();

		data.setEmployeeName(payrollData.getFirstName() + " " + payrollData.getLastName());
		data.setPayPeriod(payrollData.getPayPeriod());
		data.setGrossIncome(monthlyGrossIncome);
		
		// Calculate income tax.
		double monthlyIncomeTax = (double) Math.floor(calculateAnnualIncomeTax(annualSalary) / 12 + 0.5f);
		logger.info("monthly income tax = " + monthlyIncomeTax);
		data.setIncomeTax(monthlyIncomeTax);
		
		
		data.setNetIncome(monthlyGrossIncome - monthlyIncomeTax);
	    data.setSuperContribution((double) Math.floor(monthlyGrossIncome * superRate));
						
		return data;
	}

	private double calculateAnnualIncomeTax(double annualSalary) {
		double salaryRemaining = annualSalary;
		double taxAccrued = 0f;

		// Lambda definition.
		IncomeTaxCalulator<TaxBracket, Double, Double, Double> incomeTaxCalulator = (t, s, a) -> {
			if (t.getUpperLimit() == null) {
				return s;
			} else {
				return Math.min(s, t.getUpperLimit() - (a - s));
			}
		};

		for (TaxBracket taxBracket : taxBrackets) {
			double salaryAmountToTax = 0;

			// Lambda execution.
			salaryAmountToTax = incomeTaxCalulator.calculate(taxBracket, salaryRemaining, annualSalary);
			if (salaryAmountToTax > salaryRemaining) {
				salaryAmountToTax = salaryRemaining;
			}

			taxAccrued += salaryAmountToTax * taxBracket.getRate();
			salaryRemaining -= salaryAmountToTax;

			if (salaryRemaining <= 0) {
				break;
			}
		}

		return taxAccrued;
	}

}
