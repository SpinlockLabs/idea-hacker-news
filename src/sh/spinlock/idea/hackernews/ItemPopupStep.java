package sh.spinlock.idea.hackernews;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.spinlock.idea.hackernews.client.HackerNewsItem;

public class ItemPopupStep extends BaseListPopupStep<HackerNewsItem> {
  public ItemPopupStep(String title, List<HackerNewsItem> items) {
    super(title, items);
  }

  @NotNull
  @Override
  public String getTextFor(HackerNewsItem value) {
    return value.rankInList + ". " + value.title;
  }

  @Nullable
  @Override
  public PopupStep<HackerNewsItem> onChosen(HackerNewsItem selectedValue, boolean finalChoice) {
    String url = selectedValue.url;

    if (url == null) {
      url = String.format("https://news.ycombinator.com/item?id=%s", selectedValue.id);
    }

    BrowserUtil.browse(url);
    return null;
  }
}
