package brudigitalcontest;

import spark.Spark;

import java.util.Map;
import java.util.OptionalInt;

import static spark.Spark.*;

public class Application {

  private static Map<String, String> environment;


  public static void main(String[] args) {
    environment = new ProcessBuilder().environment();
    deploymentPort().ifPresent(Spark::port);
    staticFiles.location("web");
    staticFiles.expireTime(24 * 60 * 60);
    Db db = new Db();

    Texts texts = new Texts();
    ContestController contestController = new ContestController(texts, db);
    get("/", contestController::getIndex);
    post("contest", contestController::postStart);
    get("contest/:id", contestController::getContest);
    get("contest/:id/intro", contestController::getIntro);
    post("contest/:id/question/:questionId", contestController::postAnswer);

    LeaderboardController leaderboardController = new LeaderboardController(db, texts);
    get("leaderboard", leaderboardController::getLeaderboard);
  }

  private static OptionalInt deploymentPort() {
    if (environment.get("PORT") != null) {
      return OptionalInt.of(Integer.parseInt(environment.get("PORT")));
    }
    return OptionalInt.empty();
  }

}
