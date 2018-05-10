package brudigitalcontest;

import spark.Request;
import spark.Response;

public class ContestController {

  public String getContest(Request req, Response res) {
    return new ContestPage(req.params("id")).render();
  }

  public Object postAnswer(Request req, Response res) {
    System.out.println("ContestController.postAnswer " + req.body());
    res.status(200);

    return "";
  }
}
