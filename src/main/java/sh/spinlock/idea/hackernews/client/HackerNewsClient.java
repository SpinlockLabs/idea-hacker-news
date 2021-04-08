package sh.spinlock.idea.hackernews.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.util.Pair;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class HackerNewsClient {
  public static final String BASE_URL = "https://hacker-news.firebaseio.com/v0";
  public static final String ITEM_BASE_URL = String.format("%s/item", BASE_URL);
  public static final String TOP_STORIES_URL = String.format("%s/topstories.json", BASE_URL);

  private static final @NotNull HackerNewsClient SHARED =
      new HackerNewsClient(HttpClient.newHttpClient());

  public static @NotNull HackerNewsClient shared() {
    return SHARED;
  }

  private final @NotNull HttpClient httpClient;
  private final @NotNull ObjectMapper mapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

  public HackerNewsClient(@NotNull HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  @NotNull
  public List<Integer> getTopStoriesIds() {
    try {
      String content = fetch(TOP_STORIES_URL);
      List<Integer> items = new ArrayList<>();
      for (Object object : mapper.readValue(content, List.class)) {
        if (object instanceof Integer) {
          items.add((Integer) object);
        }
      }
      return items;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public @NotNull List<@NotNull HackerNewsItem> loadTopStories(int limit) {
    List<Integer> stories = getTopStoriesIds();

    return stories.stream()
        .limit(limit)
        .map((id) -> new Pair<>(stories.indexOf(id) + 1, id))
        .parallel()
        .map(
            (pair) -> {
              HackerNewsItem item = getStoryItem(pair.getSecond());
              item.indexInList = pair.first;
              return item;
            })
        .sorted(Comparator.comparingInt((item) -> item.indexInList))
        .collect(Collectors.toList());
  }

  public @NotNull HackerNewsItem getStoryItem(int id) {
    try {
      String content = fetch(String.format("%s/%s.json", ITEM_BASE_URL, id));
      return mapper.readValue(content, HackerNewsItem.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private @NotNull String fetch(@NotNull String url) {
    try {
      HttpRequest request =
          HttpRequest.newBuilder()
              .GET()
              .uri(URI.create(url))
              .header("User-Agent", "github.com/SpinlockLabs/idea-hacker-news")
              .build();
      HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new RuntimeException(
            String.format("Failed to fetch URL %s (status code: %d)", url, response.statusCode()));
      }

      return response.body();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Failed to fetch URL " + url, e);
    }
  }
}
