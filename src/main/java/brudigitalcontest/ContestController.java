package brudigitalcontest;

import spark.Request;
import spark.Response;

public class ContestController {

  public String getContest(Request req, Response res) {
    return new ContestPage(req.params("id")).render();
  }
}
