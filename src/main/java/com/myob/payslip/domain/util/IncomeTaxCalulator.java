package com.myob.payslip.domain.util;

@FunctionalInterface
public interface IncomeTaxCalulator<T, S, A, R> {
  R calculate(T t, S s, A a);
}

