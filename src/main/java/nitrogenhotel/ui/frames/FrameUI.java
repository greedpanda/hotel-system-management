package nitrogenhotel.ui.frames;

import nitrogenhotel.backend.CommandFrame;
import nitrogenhotel.ui.utilsgui.PopUpDialog;

/**
 * Interface for the ui of frames. Implementers take the interested CommandFrame
 * as input to tweak accordingly
 */
public interface FrameUI {
  void setupUI(CommandFrame f);

  /**
   * Inform GUI that an bad backend context has arisen.
   */
  default void warn(String msg) {
    PopUpDialog.warn(msg);
  }

  /**
   * Inform GUI about successful operation.
   */
  default void success(String msg) {
    PopUpDialog.info(msg, "Operation succeeded");
  }
}
