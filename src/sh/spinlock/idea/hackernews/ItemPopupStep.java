package sh.spinlock.idea.hackernews;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.util.PlatformIcons;
import java.awt.Color;
import java.util.List;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.spinlock.idea.hackernews.client.HackerNewsItem;

public class ItemPopupStep extends BaseListPopupStep<HackerNewsItem> {
  private final Configuration configuration;

  public ItemPopupStep(Configuration configuration, String title, List<HackerNewsItem> items) {
    super(title, items);

    this.configuration = configuration;
  }

  @NotNull
  @Override
  public String getTextFor(HackerNewsItem value) {
    return configuration
        .getItemTextFormat()
        .replace("{index}", Integer.toString(value.indexInList))
        .replace("{score}", Integer.toString(value.score))
        .replace("{title}", value.title == null ? "" : value.title)
        .replace("{url}", value.getAnyUrl())
        .replace("{attached-url}", value.url == null ? "" : value.url)
        .replace("{item-url}", value.getItemUrl());
  }

  @Nullable
  @Override
  public Color getBackgroundFor(HackerNewsItem value) {
    return null;
  }

  @Override
  public Icon getIconFor(HackerNewsItem value) {
    return PlatformIcons.WEB_ICON;
  }

  @Nullable
  @Override
  public PopupStep<HackerNewsItem> onChosen(HackerNewsItem selectedValue, boolean finalChoice) {
    BrowserUtil.browse(selectedValue.getAnyUrl());
    return null;
  }
}
