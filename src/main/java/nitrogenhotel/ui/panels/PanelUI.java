package nitrogenhotel.ui.panels;

import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.ui.utilsgui.PopUpDialog;

/** Interface for all command panel UIs. Each will accept a panel to setup. */
public interface PanelUI {
  void setupUI(CommandPanel p);

  default void warn(String msg) {
    PopUpDialog.warn(msg);
  }

  default void success(String msg) {
    PopUpDialog.info(msg, "Operation succeeded");
  }
}
