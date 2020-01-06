package sh.spinlock.idea.hackernews;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.NotNull;

public abstract class HackerNewsAction extends AnAction {
  @Override
  public void update(@NotNull AnActionEvent e) {
    e.getPresentation().setEnabled(true);
    e.getPresentation().setVisible(true);
  }

  public Configuration getCurrentConfiguration() {
    return ServiceManager.getService(Configuration.class);
  }
}
