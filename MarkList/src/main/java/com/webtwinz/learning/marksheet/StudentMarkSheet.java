package com.webtwinz.learning.marksheet;

import com.webtwinz.learning.marksheet.common.ExamResult;
import com.webtwinz.learning.marksheet.common.Subject;
import com.webtwinz.learning.marksheet.reader.MarkReader;
import java.util.Map;

public class StudentMarkSheet {

  private Student student;
  private Map<Subject, Float> markList;
  private MarkReader markReader;

  //Open Closed Principle.. See any kind of Reader can be used to read Marks.
  //In case if data needs to be read from File this class remains unchanged and we need to simply
  //write a FileMarkReader class and inject that here.
  public StudentMarkSheet(Student student, MarkReader markReader) {
    this.student = student;
    this.markReader = markReader;
  }

  //TASK: Come up with a MarkWriter class and give the end user the flexibility
  //to write the result to console or or to a Database. Hint: Check MarkReader
  public void print() {
    student.print();
    //Avoid imperative looping where ever possible and use Declarative looping as follows
    markList.keySet()
        .forEach(subject -> System.out.printf("%20s : %2s %n", subject, markList.get(subject)));

    Float totalMarks = getTotalMarks();
    ExamResult
        .getResult(totalMarks, markList.size())
        .printResult();
  }

  public Float getTotalMarks() {
    return markList.values().stream()
        .reduce(0.0f, (a, b) -> a + b);
  }

  public void readMarks() {
    this.markList = markReader.read();
  }
}
