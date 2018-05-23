package brudigitalcontest;

import lombok.AllArgsConstructor;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.*;

@AllArgsConstructor
public class ContestController {

  private static final Random RANDOM = new Random();

  private final Texts texts;
  private final List<String> photoIds = asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
  private final Db db;

  public String getStartPage(Request req, Response res) {
    return new StartPage(texts).render();
  }

  public Object postStart(Request req, Response res) {
    String name = req.queryParams("name");
    List<String> availablePhotoIds = new ArrayList<>(photoIds);
    List<Answer> contestPhotos = new ArrayList<>();
    while (!availablePhotoIds.isEmpty()) {
      int i = RANDOM.nextInt(availablePhotoIds.size());
      contestPhotos.add(new Answer(availablePhotoIds.get(i), 10 - availablePhotoIds.size()));
      availablePhotoIds.remove(i);
    }
    Contest contest = new Contest(name, contestPhotos);
    db.save(contest);
    res.redirect("/contest/" + (db.getAll(Contest.class).size() - 1));

    return null;
  }

  public String getContest(Request req, Response res) {
    Long id = Long.parseLong(req.params("id"));
    return new ContestPage(id, db.get(Contest.class, id)).render();
  }

  public Object postAnswer(Request req, Response res) {
    System.out.println("ContestController.postAnswer " + req.body());
    res.status(200);

    return "";
  }
}
