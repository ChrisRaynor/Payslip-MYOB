package com.myob.payslip.domain.service;

import com.myob.payslip.domain.PayslipData;

public interface PayslipWriterService {
	

	String saveOrUpdatePayslip(PayslipData payslipData);


}
