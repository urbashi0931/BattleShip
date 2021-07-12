package com.soen6441.battleship.test;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class AllTestsRunner {

  public static void main(String[] args) {
    JUnitCore junit = new JUnitCore();
    junit.addListener(new TextListener(System.out));
    junit.run(AllTests.class);
  }

}