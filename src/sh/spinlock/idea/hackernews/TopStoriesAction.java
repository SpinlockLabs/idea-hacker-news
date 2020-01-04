package sh.spinlock.idea.hackernews;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import sh.spinlock.idea.hackernews.client.HackerNewsClient;
import sh.spinlock.idea.hackernews.client.HackerNewsItem;

public class TopStoriesAction extends AnAction {
  private static final int LIMIT = 30;

  @Override
  public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    List<HackerNewsItem> items = HackerNewsClient.loadTopStories(LIMIT);

    ListPopup popup =
        JBPopupFactory.getInstance()
            .createListPopup(new ItemPopupStep("Top Stories on Hacker News", items));

    popup.showInFocusCenter();
  }
}
