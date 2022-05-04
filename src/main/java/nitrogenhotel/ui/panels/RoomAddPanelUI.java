package nitrogenhotel.ui.panels;

import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.ui.actioncommands.RoomAddActionCommand;
import nitrogenhotel.ui.utilsgui.GuiBorderLabelUtil;

/** UI for add room. */
public class RoomAddPanelUI implements PanelUI {
  public JComboBox<Integer> floorInput;
  public JTextField noOfBedsInput;
  public JTextArea noteInput;
  public JTextField roomNumberInput;
  public JComboBox<String> sizeInput;
  public List<Floor> floorsList;
  public List<RoomType> roomTypesList;

  public RoomAddPanelUI(List<Floor> floorsList, List<RoomType> roomTypesList) {
    this.floorsList = floorsList;
    this.roomTypesList = roomTypesList;
  }

  @Override
  public void setupUI(CommandPanel panel) {
    JLabel sizeLabel = new javax.swing.JLabel();
    roomNumberInput = new javax.swing.JTextField();
    noOfBedsInput = new javax.swing.JTextField();
    floorInput = new javax.swing.JComboBox<>();
    noteInput = new javax.swing.JTextArea();
    sizeInput = new javax.swing.JComboBox<>();

    panel.setBackground(new java.awt.Color(44, 44, 59));
    panel.setLayout(new java.awt.GridBagLayout());

    java.awt.GridBagConstraints gridBagConstraints;

    sizeLabel.setText("Size:");
    GuiBorderLabelUtil.boldText(sizeLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(22, 73, 0, 0);
    panel.add(sizeLabel, gridBagConstraints);

    JLabel roomNumberLabel = new javax.swing.JLabel();
    roomNumberLabel.setText("Room Number :");
    GuiBorderLabelUtil.boldText(roomNumberLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 5;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(44, 73, 0, 0);
    panel.add(roomNumberLabel, gridBagConstraints);

    JLabel noOfBedsLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(noOfBedsLabel);
    noOfBedsLabel.setText("No. of Beds:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(21, 73, 0, 0);
    panel.add(noOfBedsLabel, gridBagConstraints);

    JLabel floorLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(floorLabel);
    floorLabel.setText("Floor:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(22, 73, 0, 0);
    panel.add(floorLabel, gridBagConstraints);

    JLabel noteLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(noteLabel);
    noteLabel.setText("Note:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(26, 73, 0, 0);
    panel.add(noteLabel, gridBagConstraints);

    roomNumberInput.setBackground(new java.awt.Color(56, 56, 77));
    roomNumberInput.setPreferredSize(new java.awt.Dimension(125, 28));
    roomNumberInput.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        roomNumberInputKeyTyped(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 85;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(40, 18, 0, 80);
    panel.add(roomNumberInput, gridBagConstraints);

    noOfBedsInput.setBackground(new java.awt.Color(56, 56, 77));
    noOfBedsInput.setPreferredSize(new java.awt.Dimension(125, 28));
    noOfBedsInput.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        noOfBedsInputKeyTyped(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 85;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(17, 18, 0, 80);
    panel.add(noOfBedsInput, gridBagConstraints);

    floorInput.setBackground(new java.awt.Color(56, 56, 77));
    floorInput.setPreferredSize(new java.awt.Dimension(125, 28));
    floorInput.setModel(new javax.swing.DefaultComboBoxModel<>());
    for (Floor floor : floorsList) {
      floorInput.addItem(floor.getFloorNumber());
    }
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 85;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 80);
    panel.add(floorInput, gridBagConstraints);

    noteInput.setBackground(new java.awt.Color(56, 56, 77));
    noteInput.setColumns(8);
    noteInput.setLineWrap(true);
    noteInput.setRows(2);
    JScrollPane scroll = new javax.swing.JScrollPane();
    scroll.setViewportView(noteInput);
    scroll.setPreferredSize(new java.awt.Dimension(154, 70));
    noteInput.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        noteInputKeyTyped(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 55;
    gridBagConstraints.ipady = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(12, 18, 1, 80);
    panel.add(scroll, gridBagConstraints);

    JButton okButton = new javax.swing.JButton();
    okButton.setText("OK");
    okButton.setActionCommand(RoomAddActionCommand.OK.str);
    okButton.addActionListener(panel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 10;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.ipadx = 21;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(26, 91, 57, 0);
    panel.add(okButton, gridBagConstraints);

    JButton cancelButton = new javax.swing.JButton();
    cancelButton.setText("Cancel");
    cancelButton.setActionCommand(RoomAddActionCommand.CANCEL.str);
    cancelButton.addActionListener(panel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 10;
    gridBagConstraints.ipadx = 21;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(25, 46, 63, 68);
    panel.add(cancelButton, gridBagConstraints);

    sizeInput.setBackground(new java.awt.Color(56, 56, 77));
    sizeInput.setPreferredSize(new java.awt.Dimension(135, 28));
    sizeInput.setModel(new javax.swing.DefaultComboBoxModel<>());
    for (RoomType type : roomTypesList) {
      sizeInput.addItem(type.size);
    }
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 75;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 80);
    panel.add(sizeInput, gridBagConstraints);
    panel.setBorder(BorderFactory.createTitledBorder(GuiBorderLabelUtil.createTitleBorder("Add Room")));
  }

  private void roomNumberInputKeyTyped(java.awt.event.KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!Character.isDigit(c)) {
      evt.consume();
    }
  }

  private void noOfBedsInputKeyTyped(java.awt.event.KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!Character.isDigit(c)) {
      evt.consume();
    }
  }

  private void noteInputKeyTyped(java.awt.event.KeyEvent evt) {
    char c = evt.getKeyChar();
    if (noteInput.getText().trim().length() == 255 || c == java.awt.event.KeyEvent.VK_BACK_SPACE) {
      evt.consume();
    }
  }

  public String getRoomNumber() {
    return roomNumberInput.getText();
  }

  public String getRoomSize() {
    return Objects.requireNonNull(sizeInput.getSelectedItem()).toString();
  }

  public String getRoomNoOfBeds() {
    return noOfBedsInput.getText();
  }

  public String getRoomFloor() {
    return Objects.requireNonNull(floorInput.getSelectedItem()).toString();
  }

  public String getRoomNote() {
    return noteInput.getText();
  }
}