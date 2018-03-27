package com.myob.payslip.domain.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.myob.payslip.domain.PayslipData;

public interface PayslipRepository extends MongoRepository<PayslipData, String> {

	public PayslipData findById(String id);
	
    public List<PayslipData> findByEmployeeName(String employeeName);

    public List<PayslipData> findByPayPeriod(String payPeriod);
}
