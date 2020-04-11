package sh.spinlock.idea.hackernews;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Objects;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ConfigurationExtensionUI {

  public JPanel panel;
  public ComboBox<Integer> itemLimitSelector;
  public JBTextField itemTextFormat;

  public ConfigurationExtensionUI() {
    setup();
  }

  public void setup() {
    itemLimitSelector.addItem(5);
    itemLimitSelector.addItem(10);
    itemLimitSelector.addItem(20);
    itemLimitSelector.addItem(30);
    itemLimitSelector.addItem(40);
    itemLimitSelector.addItem(50);
  }

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    panel = new JPanel();
    panel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
    itemLimitSelector = new ComboBox();
    itemLimitSelector.setEditable(false);
    final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
    itemLimitSelector.setModel(defaultComboBoxModel1);
    itemLimitSelector.setSwingPopup(false);
    panel.add(itemLimitSelector,
        new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null,
            null, 0, false));
    final JBLabel jBLabel1 = new JBLabel();
    jBLabel1.setText("Item Text Format");
    panel.add(jBLabel1,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final Spacer spacer1 = new Spacer();
    panel.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final JBLabel jBLabel2 = new JBLabel();
    jBLabel2.setText("Item Limit");
    panel.add(jBLabel2,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, 1, null, null, null, 0, false));
    itemTextFormat = new JBTextField();
    itemTextFormat.setToolTipText(
        "The following variables are provided:<br/><br/>\n<b>{index}</b>: The index in the item list.<br/>\n<b>{title}</b>: The title of the item.<br/>\n<b>{score}</b>: The current score of the item.<br/>\n<b>{url}</b>: If the item is a URL, this is the URL that was posted, otherwise it is the URL to the Hacker News story page.<br/>\n<b>{attached-url}</b>: If there was a URL attached to the item, this is the URL, otherwise an empty string.<br/>\n<b>{item-url}</b>: The URL to the story on Hacker News.");
    panel.add(itemTextFormat, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return panel;
  }

  public void setData(Configuration data) {
    itemLimitSelector.setSelectedItem(data.getItemLimit());
    itemTextFormat.setText(data.getItemTextFormat());
  }

  public void getData(Configuration data) {
    if (itemLimitSelector.getSelectedIndex() != -1) {
      data.setItemLimit(itemLimitSelector.getItemAt(itemLimitSelector.getSelectedIndex()));
    }

    if (itemTextFormat.getText() != null) {
      data.setItemTextFormat(itemTextFormat.getText());
    }
  }

  public boolean isModified(Configuration data) {
    Integer selectedItemLimit = itemLimitSelector.getItemAt(itemLimitSelector.getSelectedIndex());
    String setItemTextFormat = itemTextFormat.getText();

    return !Objects.equals(data.getItemLimit(), selectedItemLimit)
        || !setItemTextFormat.equals(data.getItemTextFormat());
  }
}
