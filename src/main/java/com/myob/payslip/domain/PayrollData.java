package com.myob.payslip.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

@JsonPropertyOrder(value = { "firstName", "lastName", "annualSalary", "superRate", "payPeriod" })
@JsonRootName("transaction")
public class PayrollData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6638978418937889769L;

	@JsonProperty
	private String firstName;

	@JsonProperty
	private String lastName;

	@JsonProperty
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Double annualSalary;

	@JsonProperty
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Double superRate;

	@JsonProperty
	private String payPeriod;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(Double annualSalary) {
		this.annualSalary = annualSalary;
	}

	public Double getSuperRate() {
		return superRate;
	}

	public void setSuperRate(String superRate) {
		if (superRate != null) {
			this.superRate = new Double(superRate.trim().replace("%", "")).doubleValue() / 100;
		} else {
			this.superRate = null;
		}
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PayrollData{");
		sb.append("firstName=").append(firstName);
		sb.append(", lastName=").append(lastName);
		sb.append(", annualSalary=").append(annualSalary);
		sb.append(", superRate=").append(superRate);
		sb.append(", payPeriod=").append(payPeriod);
		sb.append("}");
		return sb.toString();
	}
}
