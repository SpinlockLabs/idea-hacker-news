package sh.spinlock.idea.hackernews;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.SearchableConfigurable;
import javax.swing.JComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nls.Capitalization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GlobalConfigurationExtension extends BaseConfigurable
    implements SearchableConfigurable {
  private final ConfigurationExtensionUI ui = new ConfigurationExtensionUI();
  private final Configuration configuration;

  public GlobalConfigurationExtension() {
    configuration = ServiceManager.getService(Configuration.class);

    ui.setData(configuration);
  }

  @Nls(capitalization = Capitalization.Title)
  @Override
  public String getDisplayName() {
    return "Hacker News";
  }

  @NotNull
  @Override
  public String getId() {
    return "hacker-news.settings";
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return ui.panel;
  }

  @Override
  public void reset() {
    ui.setData(configuration);
  }

  @Override
  public boolean isModified() {
    return ui.isModified(configuration);
  }

  @Override
  public void apply() {
    ui.getData(configuration);
  }
}
