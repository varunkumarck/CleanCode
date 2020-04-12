package com.webtwinz.learning.marksheet.common;

import com.webtwinz.learning.marksheet.exception.NoCriteriaForResultFoundException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ExamResult {

  //IMPROVEMENT: consider minimum mark for each subject.
  //Use BigDecimal instead of Float as float should not be used for comparison.

  DISTINCTION(buildCriteriaWithMinimumAndMaximumLimit(79.99f, 100.0f), true),
  FIRST_CLASS(buildCriteriaWithMinimumAndMaximumLimit(59.99f, 80.0f), true),
  SECOND_CLASS(buildCriteriaWithMinimumAndMaximumLimit(39.99f, 60.0f), true),
  FAILED(buildCriteriaWithMinimumAndMaximumLimit(0.0f, 40.0f), false);

  private BiPredicate<Float, Integer> criteria;
  private boolean isPass;

  ExamResult(BiPredicate<Float, Integer> criteria, boolean isPass) {
    this.criteria = criteria;
    this.isPass = isPass;
  }

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MethodHandles.lookup().lookupClass());

  private static BiPredicate<Float, Integer> buildCriteriaWithMinimumAndMaximumLimit(Float minimum,
      Float maximum) {
    return (total, noOfSubjects) -> total / noOfSubjects >= minimum &&
        total / noOfSubjects <= maximum;
  }

  //Addition of new Result type needs no modification for the below function
  //This is an example of avoiding if-else ladder or switch cases and making the maintenance easy
  public static ExamResult getResult(float totalMarks, int noOfSubjects) {

    //Power of functional programming, no imperative operations.
    //It returns the first Result whose criteria evaluates to true and throws exception if no such
    //criteria is present, say here -ve marks and marks above 100 are not handled!!!, yeah fix it..
    return Arrays.stream(ExamResult.values())
        .filter(result -> result.criteria.test(totalMarks, noOfSubjects))
        .findFirst()
        .orElseThrow(getNoCriteriaForResultFoundExceptionSupplier(totalMarks, noOfSubjects));
  }

  private static Supplier<NoCriteriaForResultFoundException> getNoCriteriaForResultFoundExceptionSupplier(
      float totalMarks, int noOfSubjects) {
    return () -> new NoCriteriaForResultFoundException(
        String.format("No criteria defined for the given marks [%f] and noOfSubjects [%d]",
            totalMarks, noOfSubjects));
  }

  public void printResult() {
    System.out.println(this.isPass ? "Passed with " + this : "Failed !!!");
  }
}