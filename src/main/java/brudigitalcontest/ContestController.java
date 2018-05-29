package brudigitalcontest;

import static java.util.Arrays.asList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@AllArgsConstructor
public class ContestController {

  private static final Random RANDOM = new Random();
  private static final Type TYPE = new TypeToken<Map<String, String>>(){}.getType();

  private final Texts texts;
  private final List<String> photoIds = asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
  private final Set<String> digitalPhotoIds = new HashSet<>(asList("1", "4", "5", "9", "10"));
  private final Db db;

  public String getIndex(Request req, Response res) {
    return new IndexPage(texts).render();
  }

  public Object postStart(Request req, Response res) {
    String name = req.queryParams("name");
    List<String> availablePhotoIds = new ArrayList<>(photoIds);
    List<Answer> contestPhotos = new ArrayList<>();
    while (contestPhotos.size() < 10) {
      int i = RANDOM.nextInt(availablePhotoIds.size());
      String correctAnswer = digitalPhotoIds.contains(availablePhotoIds.get(i)) ? "digital" : "analog";
      contestPhotos.add(new Answer(availablePhotoIds.get(i), 10 - availablePhotoIds.size(), correctAnswer));
      availablePhotoIds.remove(i);
    }
    Contest contest = new Contest(name, contestPhotos);
    db.save(contest);
    res.redirect("/contest/" + (db.getAll(Contest.class).size() - 1) + "/intro");

    return null;
  }

  public String getIntro(Request req, Response res) {
    Long id = Long.parseLong(req.params("id"));
    Contest contest = db.get(Contest.class, id);

    return new ContestPage(id, contest, texts).renderIntro();
  }

  public String getContest(Request req, Response res) {
    Long id = Long.parseLong(req.params("id"));
    Contest contest = db.get(Contest.class, id);
    System.out.println(contest);

    ContestPage contestPage = new ContestPage(id, contest, texts);

    return contest.hasEnded() ? contestPage.renderReview() : contestPage.render();
  }

  public Object postAnswer(Request req, Response res) {
    Long id = Long.valueOf(req.params("id"));
    int questionId = Integer.parseInt(req.params("questionId"));
    Map<String, String> json = new Gson().fromJson(req.body(), TYPE);
    System.out.println("ContestController.postAnswer " + req.body());

    Contest contest = db.get(Contest.class, id);
    contest.getAnswers().get(questionId).setGivenAnswer(json.get("value"));
    System.out.println(contest);

    res.status(200);
    res.type("application/json");
    String status = "hasNext";
    if (questionId == contest.getAnswers().size() - 1)
    {
      status = "ended";
      contest.setEndedAt(Instant.now());
    }
    JsonObject body = new JsonObject();
    body.addProperty("status", status);
    res.body(body.toString());

    System.out.println(res.body());

    return body.toString();
  }
}
