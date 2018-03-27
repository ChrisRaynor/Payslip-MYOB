package com.myob.payslip.infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.myob.payslip.domain.PayslipData;
import com.myob.payslip.domain.repository.PayslipRepository;
import com.myob.payslip.domain.service.PayslipWriterService;

@Service
public class PayslipWriterServiceImpl implements PayslipWriterService {

	@Autowired
	private PayslipRepository payslipRepository;	
	
	@Override
	public String saveOrUpdatePayslip(PayslipData payslipData) {
		if (payslipData == null) {
			throw new IllegalArgumentException("Payslip data cannot be null");
		}
		
		return payslipRepository.save(payslipData).getId();
	}
	
	


}
