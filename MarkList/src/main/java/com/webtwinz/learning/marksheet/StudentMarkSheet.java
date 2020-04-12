package com.webtwinz.learning.marksheet;

import com.webtwinz.learning.marksheet.common.ExamResult;
import com.webtwinz.learning.marksheet.common.Subject;
import com.webtwinz.learning.marksheet.reader.MarkReader;
import java.util.Map;

public class StudentMarkSheet {

  private Student student;
  private Map<Subject, Float> markList;
  private MarkReader markReader;

  // Advantage of markReader is we can easily inject any kind of MarkReader,
  // say from database or REST API etc.
  public StudentMarkSheet(Student student, MarkReader markReader) {
    this.student = student;
    this.markReader = markReader;
  }

  //TASK: Come up with a MarkWriter class and give the end user the flexibility
  //to write the result to console or write the result to Database. Hint: Check MarkReader
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
