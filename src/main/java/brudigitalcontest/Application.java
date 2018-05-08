package brudigitalcontest;

import java.util.Map;
import java.util.OptionalInt;

import static spark.Spark.*;

public class Application {

  private static Map<String, String> environment;


  public static void main(String[] args) {
    environment = new ProcessBuilder().environment();
    staticFiles.location("web");

    ContestController contestController = new ContestController();
    get("contest/:id", contestController::getContest);
  }

  private static OptionalInt deploymentPort() {
    if (environment.get("PORT") != null) {
      return OptionalInt.of(Integer.parseInt(environment.get("PORT")));
    }
    return OptionalInt.empty();
  }

}
