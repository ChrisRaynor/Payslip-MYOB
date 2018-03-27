package com.myob.payslip.domain;

public class PayrollDataFixture {
	
	
	public static PayrollData createWith(String firstName, String lastName, Double annualSalary,
			String superRate, String payPeriod) {
		PayrollData payrollData = new PayrollData();
		
		
		payrollData.setFirstName(firstName);
		payrollData.setLastName(lastName);
		payrollData.setAnnualSalary(annualSalary);
		payrollData.setSuperRate(superRate);
		payrollData.setPayPeriod(payPeriod);

		return payrollData;
	}
	
	
	public static PayrollData createLowPayEmployee() {
		return createWith("James", "Brown", new Double(60050), "%9", "01 March - 31 March");
	}
	
	public static PayrollData createHighPayEmployee() {
		return createWith("Roger", "Williams", new Double(120000), "%10", "01 May - 31 May");
	}
}
