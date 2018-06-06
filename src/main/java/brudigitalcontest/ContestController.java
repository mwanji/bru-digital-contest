package brudigitalcontest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;
import java.util.*;

import static java.util.Arrays.asList;

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
      String correctAnswer = digitalPhotoIds.contains(availablePhotoIds.get(i)) ? "digital" : "photo";
      contestPhotos.add(new Answer(availablePhotoIds.get(i), 10 - availablePhotoIds.size(), correctAnswer));
      availablePhotoIds.remove(i);
    }

    Long contestId = db.insert(new Contest(name, contestPhotos));
    res.redirect("/contest/" + contestId + "/intro");

    return null;
  }

  public String getIntro(Request req, Response res) {
    Long id = Long.parseLong(req.params("id"));
    Contest contest = db.getContest(id);

    return new ContestPage(contest, texts).renderIntro();
  }

  public String getContest(Request req, Response res) {
    Long id = Long.parseLong(req.params("id"));
    Contest contest = db.getContest(id);

    ContestPage contestPage = new ContestPage(contest, texts);

    return contest.hasEnded() ? contestPage.renderReview() : contestPage.render();
  }

  public Object postAnswer(Request req, Response res) {
    Long id = Long.valueOf(req.params("id"));
    int questionIndex = Integer.parseInt(req.params("questionId"));
    Map<String, String> json = new Gson().fromJson(req.body(), TYPE);

    db.updateAnswer(id, questionIndex, json.get("value"));

    res.status(200);
    res.type("application/json");
    String status = "hasNext";
    if (questionIndex == 9)
    {
      status = "ended";
      db.endContest(id);
    }
    JsonObject body = new JsonObject();
    body.addProperty("status", status);
    res.body(body.toString());

    return body.toString();
  }
}
