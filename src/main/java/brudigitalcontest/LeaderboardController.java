package brudigitalcontest;

import lombok.AllArgsConstructor;
import spark.Request;
import spark.Response;

import java.util.List;

@AllArgsConstructor
public class LeaderboardController {
  private final Db db;
  private final Texts texts;

  public String getLeaderboard(Request req, Response res) {
    List<Contest> contests = db.getLeaderboard();

    return new LeaderboardPage(contests, texts).render();
  }
}
