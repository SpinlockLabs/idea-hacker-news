package sh.spinlock.idea.hackernews;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
    name = "SpinlockLabs-HackerNewsGlobalConfiguration1",
    storages = @Storage("SpinlockLabs-HackerNews1.xml"))
public class Configuration implements PersistentStateComponent<Configuration.State> {
  private State state = new State();

  public Integer getItemLimit() {
    return state.itemLimit;
  }

  public void setItemLimit(Integer itemLimit) {
    state.itemLimit = itemLimit;
  }

  public String getItemTextFormat() {
    return state.itemTextFormat;
  }

  public void setItemTextFormat(String format) {
    state.itemTextFormat = format;
  }

  @Nullable
  @Override
  public State getState() {
    return state;
  }

  @Override
  public void loadState(@NotNull State state) {
    this.state = state;
  }

  public static class State {
    public Integer itemLimit = 30;
    public String itemTextFormat = "{index}. {title}";
  }
}
