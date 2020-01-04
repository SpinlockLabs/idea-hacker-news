package sh.spinlock.idea.hackernews.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.util.Pair;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class HackerNewsClient {
  public static final String BASE_URL = "https://hacker-news.firebaseio.com/v0";
  public static final String ITEM_BASE_URL = String.format("%s/item", BASE_URL);
  public static final String TOP_STORIES_URL = String.format("%s/topstories.json", BASE_URL);

  private static final ObjectMapper mapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

  @NotNull
  public static List<Integer> getTopStories() {
    try {
      List<Integer> items = new ArrayList<>();
      for (Object object : mapper.readValue(new URL(TOP_STORIES_URL), List.class)) {
        if (object instanceof Integer) {
          items.add((Integer) object);
        }
      }
      return items;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  public static List<HackerNewsItem> loadTopStories(int limit) {
    AtomicInteger count = new AtomicInteger(0);
    return HackerNewsClient.getTopStories().stream()
        .limit(limit)
        .map((id) -> new Pair<>(count.addAndGet(1), id))
        .parallel()
        .map(
            (pair) -> {
              HackerNewsItem item = HackerNewsClient.getItem(pair.getSecond());
              item.indexInList = pair.first;
              return item;
            })
        .sorted(Comparator.comparingInt((item) -> item.indexInList))
        .collect(Collectors.toList());
  }

  @NotNull
  public static HackerNewsItem getItem(int id) {
    try {
      return mapper.readValue(
          new URL(String.format("%s/%s.json", ITEM_BASE_URL, id)), HackerNewsItem.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
