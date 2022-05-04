package nitrogenhotel.ui.frames;

import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DimensionUIResource;
import nitrogenhotel.backend.CommandFrame;
import nitrogenhotel.backend.MainFrame;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.exceptions.databasemanager.CloseConnectionException;
import nitrogenhotel.ui.actioncommands.LogOutActionCommand;
import nitrogenhotel.ui.panels.MenuPanelUI;
import nitrogenhotel.ui.utilsgui.CustomJMenu;
import nitrogenhotel.ui.utilsgui.DateTimeUpdate;
import nitrogenhotel.ui.utilsgui.GridLayoutCustom;
import nitrogenhotel.ui.utilsgui.GuiBorderLabelUtil;
import nitrogenhotel.utils.ActiveUser;
import nitrogenhotel.utils.VersionController;
import org.tinylog.Logger;

/**
 * GUI Main frame.
 */
public class MainFrameUI implements FrameUI {

  public ImageIcon icon;
  public Image image;
  public Image newimg;
  public JLabel lbimage;
  public JLabel dateLabel;
  public JLabel timeLabel;
  public JLabel screenNameLabel;
  public JPanel newPanel;
  public JPanel topPanel;
  public JPanel leftPanel;
  public JPanel centerPanel;
  public JPanel subPanel;
  public JPanel logOutPanel;
  public JMenuBar sideMenu;
  public JButton btnLogOut;
  private MainFrame mf;

  /**
   * MainFrameUI constructor with flatlaf theme.
   */
  public MainFrameUI() {
    try {
      UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
    } catch (UnsupportedLookAndFeelException e) {
      Logger.info(e);
    }
  }

  /**
   * This methods setups initial window with panels and login fields.
   */
  @Override
  public void setupUI(CommandFrame f) {
    this.mf = (MainFrame) f;
    // Main panel that lies in frame as stores all the components
    newPanel = new JPanel();
    newPanel.setLayout(new GridBagLayout());

    GridBagConstraints cs = new GridBagConstraints();
    cs.fill = GridBagConstraints.BOTH;

    // Panel with logo and subpanel (top)
    topPanel = new JPanel(new GridLayoutCustom(1, 2, 5, 0));
    cs.weightx = 0.8;
    cs.gridwidth = 2;
    cs.gridx = 0;
    cs.gridy = 0;
    topPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
    topPanel.setOpaque(false);
    newPanel.add(topPanel, cs);

    // Panel with side menus bar (left)
    leftPanel = new JPanel();
    cs.weightx = 0.0;
    cs.weighty = 0.8;
    cs.gridwidth = 1;
    cs.gridx = 0;
    cs.gridy = 1;
    newPanel.add(leftPanel, cs);

    // central panel for input or displaying info (center)
    centerPanel = new JPanel();
    cs.weightx = 0.7;
    cs.weighty = 1.0;
    cs.gridwidth = 1;
    cs.gridheight = 6;
    cs.gridx = 1;
    cs.gridy = 1;
    cs.fill = GridBagConstraints.BOTH;
    centerPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
    centerPanel.setPreferredSize(new Dimension(630, 460));
    newPanel.add(centerPanel, cs);

    // creating and adding logo image to topPanel
    lbimage = new JLabel();
    icon = new ImageIcon("src/main/resources/images/n2.png");
    image = icon.getImage();
    newimg = image.getScaledInstance(710, 100, java.awt.Image.SCALE_SMOOTH);
    icon = new ImageIcon(newimg);
    lbimage.setIcon(icon);
    lbimage.setBorder(BorderFactory.createRaisedSoftBevelBorder());
    topPanel.add(lbimage);

    // date and time that will be added to subpanel in top panel
    dateLabel = new JLabel();
    dateLabel.setHorizontalAlignment(JLabel.CENTER);
    timeLabel = new JLabel();
    timeLabel.setHorizontalAlignment(JLabel.CENTER);
    screenNameLabel = new JLabel();
    screenNameLabel.setHorizontalAlignment(JLabel.CENTER);
    screenNameLabel.setBorder(new CompoundBorder(screenNameLabel.getBorder(), new EmptyBorder(0, 0, 0, 0)));
    new DateTimeUpdate(dateLabel, timeLabel).start();

    logOutPanel = new JPanel();
    btnLogOut = new JButton("Log out");
    btnLogOut.setActionCommand(LogOutActionCommand.LOGOUT.str);

    // a part of the topPanel, that holds date, time and log out button
    subPanel = new JPanel();
    subPanel.setLayout(new GridLayoutCustom(4, 0));
    subPanel.add(logOutPanel);
    subPanel.add(dateLabel);
    subPanel.add(timeLabel);
    subPanel.add(screenNameLabel);
    topPanel.add(subPanel);

    sideMenu = new JMenuBar();
    sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.PAGE_AXIS));

    leftPanel.add(sideMenu);

    f.setSize(new DimensionUIResource(850, 600));
    f.setMinimumSize(new DimensionUIResource(850, 600));
    f.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
    // positioning to the center of the screen.
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Point centerPoint = ge.getCenterPoint();
    int dx = centerPoint.x - f.getWidth() / 2;
    int dy = centerPoint.y - f.getHeight() / 2;
    f.setLocation(dx, dy);
    
    f.getContentPane().add(newPanel);
    f.setTitle("Nitrogen Hotel " + VersionController.getVersion());
    f.setVisible(true);
    f.setIconImage(icon.getImage());

    f.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        try {
          DataBaseManager.closeConnection();
        } catch (CloseConnectionException f) {
          // TODO: this seems a bit severe to be thrown how to deal with?
          Logger.error(f);
        }
      }
    });
  }

  /**
   * This method should be called after successful login. It removes login panel
   * and populates left menu bar.
   */
  public void updateMenuPanel() {

    centerPanel.removeAll();
    centerPanel.revalidate();

    btnLogOut.setFocusPainted(false);
    btnLogOut.addActionListener(mf);

    logOutPanel.add(Box.createHorizontalStrut(5));
    logOutPanel.add(btnLogOut);
    logOutPanel.add(Box.createHorizontalStrut(5));
    updateScreenNameLabel();

    MenuPanelUI mp = new MenuPanelUI();
    mp.listOfMenus();

    for (CustomJMenu menu : mp.menuList) {
      Font font = menu.getFont();
      Font big = new Font(font.toString(), Font.BOLD, 14);
      menu.setFont(big);
      menu.setForeground(Color.lightGray);
      sideMenu.add(menu);
      sideMenu.revalidate();
      leftPanel.revalidate();
      menu.setPreferredSize(new Dimension(sideMenu.getPreferredSize().width, 110));
      sideMenu.add(new JSeparator());
      sideMenu.revalidate();
      sideMenu.repaint();
      for (int i = 0; i < menu.getItemCount(); i++) {
        menu.getItem(i).addActionListener(mf);
      }
    }
  }

  /**
   * Updates dashboard aka centerPanel with edit screen name fields.
   */
  public void editScreenNameSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom());
    centerPanel.add(mf.screenNameEditPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Cleans the dashboard aka centerPanel.
   */
  public void cleanCenterPanel() {
    centerPanel.removeAll();
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Updates dashboard aka centerPanel with edit password name fields.
   */
  public void editPasswordSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom());
    centerPanel.add(mf.passwordEditPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Updates screen name in the label under date and time.
   */
  public void updateScreenNameLabel() {
    screenNameLabel.setText(ActiveUser.get().getScreenName());
    GuiBorderLabelUtil.boldText(screenNameLabel);
  }

  /**
   * Displays rooms in the NON editable table.
   */
  public void roomDetailsSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom(1, 0));
    centerPanel.add(mf.roomDetailsPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Displays rooms in the EDITABLE table.
   */
  public void roomEditSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom(1, 0));
    centerPanel.add(mf.roomEditPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Updates dashboard aka centerPanel with add room fields.
   */
  public void addRoomSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom());
    centerPanel.add(mf.roomAddPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Updates main frame after log out button is triggered.
   */
  public void resetToLoginScreen() {
    cleanCenterPanel();
    sideMenu.removeAll();
    sideMenu.revalidate();
    sideMenu.repaint();
    leftPanel.revalidate();
    logOutPanel.removeAll();
    logOutPanel.revalidate();
    logOutPanel.repaint();
    screenNameLabel.setText("");
    centerPanel.setLayout(new FlowLayout());
    centerPanel.add(mf.loginPanel);
    centerPanel.repaint();
  }

  /**
   * Displays rooms in the NON editable table for deletion option.
   */
  public void roomDeleteSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom(1, 0));
    centerPanel.add(mf.roomDeletePanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Form to add new user.
   */
  public void userAddSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom(1, 0));
    centerPanel.add(mf.userAddPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Form to add new customer.
   */
  public void customerAddSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom());
    centerPanel.add(mf.customerAddPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Displays customers in the EDITABLE table.
   */
  public void customerEditSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom(1, 0));
    centerPanel.add(mf.customerEditPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Displays customers in the NON editable table.
   */
  public void customerDetailsSetUp() {
    centerPanel.removeAll();
    centerPanel.add(mf.customerDetailsPanel);
    centerPanel.setLayout(new GridLayoutCustom(1, 0));
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Displays bookings in the NON editable table.
   */
  public void bookingDetailsSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom(1, 0));
    centerPanel.add(mf.bookingDetailsPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Sets up the mainframe with booking.
   */
  public void bookingAddSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom(1, 0));
    centerPanel.add(mf.bookingAddPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }

  /**
   * Displays bookings in the editable table.
   */
  public void bookingEditSetUp() {
    centerPanel.removeAll();
    centerPanel.setLayout(new GridLayoutCustom(1, 0));
    centerPanel.add(mf.bookingEditPanel);
    centerPanel.revalidate();
    centerPanel.repaint();
  }
}
