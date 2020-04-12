package com.webtwinz.learning.marksheet;

public class Student {

  private String name;
  private String slNo;

  public Student(String name, String slNo) {
    this.name = name;
    this.slNo = slNo;
  }

  public String getName() {
    return name;
  }

  public void print() {
    System.out.println(toString());
  }

  @Override
  public String toString() {
    return "Name : " + name
        + ", Serial Number = " + slNo;
  }
}
