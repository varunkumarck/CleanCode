package com.webtwinz.learning.marksheet.reader;

import com.webtwinz.learning.marksheet.common.Subject;
import com.webtwinz.learning.marksheet.exception.InvalidMarkException;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleMarkReader implements MarkReader {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MethodHandles.lookup().lookupClass());

  //The life cycle of the reader should be managed by this class itself, in this demonstration
  //as we are using System.in (Its used in Main Program) currently not closing it after the read operation.
  private BufferedReader dataReader;

  public ConsoleMarkReader(BufferedReader dataReader) {
    this.dataReader = dataReader;
  }

  @Override
  public Map<Subject, Float> read() {
    Map<Subject, Float> marks = new LinkedHashMap<>();
    for (Subject subject : Subject.values()) {
      System.out.printf("Enter Mark for %s : ", subject);
      try {
        Float mark = parseMark(dataReader.readLine());
        marks.put(subject, mark);
      } catch (IOException e) {
        throw new InvalidMarkException(e);
      }
    }
    return marks;
  }

  private Float parseMark(String givenMark) {
    Objects.requireNonNull(givenMark);
    try {
      return Float.parseFloat(givenMark);
    } catch (Exception ex) {
      throw new InvalidMarkException(ex);
    }
  }
}
