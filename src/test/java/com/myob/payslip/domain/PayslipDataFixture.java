package com.myob.payslip.domain;

public class PayslipDataFixture {
	
	
	public static PayslipData createWith(String id, String employeeName, String payPeriod,
			Double grossIncome, Double incomeTax, Double netIncome, Double superContribution) {
		PayslipData payslipData = new PayslipData();
		
		payslipData.setId(id);
		payslipData.setEmployeeName(employeeName);
		payslipData.setPayPeriod(payPeriod);
		payslipData.setGrossIncome(grossIncome);
		payslipData.setIncomeTax(incomeTax);
		payslipData.setNetIncome(netIncome);
		payslipData.setSuperContribution(superContribution);

		return payslipData;
	}
	
	
	public static PayslipData createLowPayEmployee() {
		return createWith(null, "James Brown", "01 March - 31 March",
				new Double(5004), new Double(922), new Double(4082), new Double(450));
	}
	
	public static PayslipData createHighPayEmployee() {
		return createWith(null, "Roger Williams", "01 May - 31 May",
				new Double(10000), new Double(2669), new Double(7331), new Double(1000));
	}
		
	

	

}
