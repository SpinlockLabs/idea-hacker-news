package sh.spinlock.idea.hackernews.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class HackerNewsItem {
  public int indexInList;

  @JsonProperty public String by;

  @JsonProperty public int descendants;

  @JsonProperty public int id;

  @JsonProperty public List<Integer> kids;

  @JsonProperty public int score;

  @JsonProperty public long time;

  @JsonProperty public String title;

  @JsonProperty public String text;

  @JsonProperty public String type;

  @JsonProperty public String url;
}
