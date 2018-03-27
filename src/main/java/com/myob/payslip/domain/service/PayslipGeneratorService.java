package com.myob.payslip.domain.service;

import com.myob.payslip.domain.PayrollData;
import com.myob.payslip.domain.PayslipData;

public interface PayslipGeneratorService {

	PayslipData generatePayslip(PayrollData payrollData);
}
