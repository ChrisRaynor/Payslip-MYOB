package com.myob.payslip.infrastructure.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;


import com.myob.payslip.domain.PayrollData;
import com.myob.payslip.domain.service.PayrollReaderService;



@Service
public class PayrollReaderServiceImpl implements PayrollReaderService {

	private static final Logger logger = LoggerFactory.getLogger(PayrollReaderServiceImpl.class);
	
	@Override
	public List<PayrollData> readPayrollData(File payrollFile) throws IOException {
		if (payrollFile == null) {
			throw new IllegalArgumentException("Payroll file cannot be null");
		}
		
		logger.info("Reading payroll file: " + payrollFile.getPath());
		
		// Make sure we have a valid file.
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(payrollFile);
		} catch (FileNotFoundException e1) {
			logger.error("Could not read the CSV file: " + payrollFile.getPath());
			throw e1;
		}
		
		// Parse the CSV file.
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.typedSchemaFor(PayrollData.class);
        
        List<PayrollData> payrollData = new ArrayList<PayrollData>();
        
		try {
			List<Object> list = new CsvMapper().readerFor(PayrollData.class)
			        .with(csvSchema.withColumnSeparator(CsvSchema.DEFAULT_COLUMN_SEPARATOR))
			        .readValues(fileReader)
			        .readAll();

			
			 for (Object item : list) {
				 payrollData.add(PayrollData.class.cast(item));
			  }
			

		} catch (JsonProcessingException e) {
			logger.error("Could not read the CSV file: " + payrollFile.getPath());
			throw e;
		} catch (IOException e) {
			logger.error("Could not read the CSV file: " + payrollFile.getPath());
			throw e;
		}
		
		
	
		return payrollData;
	}

}
