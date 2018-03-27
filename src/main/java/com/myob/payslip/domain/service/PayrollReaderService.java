package com.myob.payslip.domain.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.myob.payslip.domain.PayrollData;

public interface PayrollReaderService {
	
	List<PayrollData> readPayrollData(File payrollFile) throws IOException;

}
