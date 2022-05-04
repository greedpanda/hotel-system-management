package nitrogenhotel.ui.utilsgui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.tinylog.Logger;

/**
 * https://stackoverflow.com/questions/13366780/how-to-add-real-time-date-and-time-into-a-jframe-component-e-g-status-bar.
 * The whole idea of date and time labels is taken from link above.
 */
public class DateTimeUpdate extends Thread {

  protected boolean isRunning;
  protected JLabel dateLabel;
  protected JLabel timeLabel;
  protected SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
  protected SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");

  /**
   * Constructor to generate date and time.
   *
   * @param dateLabel Label for date.
   * @param timeLabel Label for time.
   */
  public DateTimeUpdate(JLabel dateLabel, JLabel timeLabel) {
    this.dateLabel = dateLabel;
    this.timeLabel = timeLabel;
    this.isRunning = true;
  }

  /**
   * Responsible for running overriden method.
   */
  @Override
  public void run() {
    while (isRunning) {
      SwingUtilities.invokeLater(
          new Runnable() {
            @Override
            public void run() {
              Calendar currentCalendar = Calendar.getInstance();
              Date currentTime = currentCalendar.getTime();
              dateLabel.setText(dateFormat.format(currentTime));
              dateLabel.setForeground(Color.LIGHT_GRAY);
              timeLabel.setText(timeFormat.format(currentTime));
              timeLabel.setForeground(Color.LIGHT_GRAY);
            }
          });

      try {
        Thread.sleep(500L);
      } catch (InterruptedException e) {
        Logger.error("This statement should not be reached.");
      }
    }
  }

  /** This method helps to run itself. */
  public void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }
}
