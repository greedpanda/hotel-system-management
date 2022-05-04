package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nitrogenhotel.backend.MainFrame;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestMainFrame {

  // https://stackoverflow.com/questions/51004447/spring-boot-java-awt-headlessexception
  @BeforeClass
  public static void setupHeadlessMode() {
    System.setProperty("java.awt.headless", "false");
  }

  @Test
  public void test() {
    boolean ciRunning = Boolean.parseBoolean(System.getProperty("ciRunning"));
    Assumptions.assumeFalse(ciRunning);
    MainFrame mf = new MainFrame();
  }
}
