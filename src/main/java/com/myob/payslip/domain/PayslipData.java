package com.myob.payslip.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PayslipData {

	@Id
	private String id;

	private String employeeName;
	private String payPeriod;
	private Double grossIncome;
	private Double incomeTax;
	private Double netIncome;
	private Double superContribution;

	public PayslipData() {
	}

	public PayslipData(String id, String employeeName, String payPeriod,
			Double grossIncome, Double incomeTax, Double netIncome, Double superContribution) {
		super();
		this.id = id;
		this.employeeName = employeeName;
		this.payPeriod = payPeriod;
		this.grossIncome = grossIncome;
		this.incomeTax = incomeTax;
		this.netIncome = netIncome;
		this.superContribution = superContribution;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	
	public Double getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(Double grossIncome) {
		this.grossIncome = grossIncome;
	}

	public Double getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(Double incomeTax) {
		this.incomeTax = incomeTax;
	}

	public Double getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(Double netIncome) {
		this.netIncome = netIncome;
	}

	public Double getSuperContribution() {
		return superContribution;
	}

	public void setSuperContribution(Double superContribution) {
		this.superContribution = superContribution;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PayslipData{");
		sb.append("employeeName=").append(employeeName);
		sb.append(", payPeriod=").append(payPeriod);
		sb.append(", grossIncome=").append(grossIncome);
		sb.append(", incomeTax=").append(incomeTax);
		sb.append(", netIncome=").append(netIncome);
		sb.append(", superContribution=").append(superContribution);
		sb.append("}");
		return sb.toString();
	}


}
