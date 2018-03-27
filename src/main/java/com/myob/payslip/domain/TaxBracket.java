package com.myob.payslip.domain;

public class TaxBracket {
	
	private Float rate;
	private Float upperLimit;
	
	public TaxBracket(Float rate, Float upperLimit) {
		this.rate = rate;
		this.upperLimit = upperLimit;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Float getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Float upperLimit) {
		this.upperLimit = upperLimit;
	}
	
	

}
