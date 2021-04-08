package sh.spinlock.idea.hackernews;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import sh.spinlock.idea.hackernews.client.HackerNewsClient;
import sh.spinlock.idea.hackernews.client.HackerNewsItem;

public class TopStoriesAction extends HackerNewsAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    Configuration configuration = getCurrentConfiguration();

    List<HackerNewsItem> items =
        HackerNewsClient.shared().loadTopStories(configuration.getItemLimit());

    ListPopup popup =
        JBPopupFactory.getInstance()
            .createListPopup(new ItemPopupStep(configuration, "Top Stories on Hacker News", items));

    popup.showInFocusCenter();
  }
}
