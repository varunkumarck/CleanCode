package com.webtwinz.learning.marksheet;

import com.webtwinz.learning.marksheet.reader.ConsoleMarkReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//IMPROVEMENT: Use sl4j for logging instead of System.out, read noOfStudents from command line
public class Application {

  public static void main(String[] args) throws IOException {
    List<StudentMarkSheet> studentMarkSheetList = readNStudentMarks(2);
    printAllStudentsMarkSheet(studentMarkSheetList);
  }

  private static List<StudentMarkSheet> readNStudentMarks(int noOfStudents)
      throws IOException {

    System.out.printf("Enter the Marks for %d Students %n", noOfStudents);
    List<StudentMarkSheet> studentMarkSheets = new ArrayList<>();

    //try-with-resource automatically closes the consoleReader, this is the preferred way.
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      //Improvement : Avoid imperative way of iteration.
      for (int i = 0; i < noOfStudents; i++) {
        Student student = readStudentDetailsUsing(reader);

        //TASK: Read Data From Database, Come up with DatabaseMarkReader, in that way ideal use of
        //Polymorphism can learn.
        StudentMarkSheet studentMarkSheet = readMarkForStudentUsing(student,
            new ConsoleMarkReader(reader));
        studentMarkSheets.add(studentMarkSheet);
      }
    }

    return studentMarkSheets;
  }

  private static Student readStudentDetailsUsing(BufferedReader dataReader)
      throws IOException {
    System.out.print("Enter Name : ");
    String studentName = dataReader.readLine();
    System.out.print("Enter Roll Number : ");
    String rollNumber = dataReader.readLine();
    return new Student(studentName, rollNumber);
  }

  // The method can be easily read as "Read Mark for some Student using MarkReader"
  // Naming like this improves the readability of code
  private static StudentMarkSheet readMarkForStudentUsing(Student student,
      ConsoleMarkReader markReader) {
    StudentMarkSheet studentMarkSheet = new StudentMarkSheet(student, markReader);
    studentMarkSheet.readMarks();
    return studentMarkSheet;
  }

  private static void printAllStudentsMarkSheet(List<StudentMarkSheet> studentMarkSheetList) {
    for (StudentMarkSheet studentMarkSheet : studentMarkSheetList) {
      studentMarkSheet.print();
    }
  }
}