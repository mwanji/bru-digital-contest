package brudigitalcontest;

import brudigitalcontest.html.Page;
import lombok.AllArgsConstructor;

import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class LeaderboardPage
{
    private final List<Contest> contests;
    private final Texts texts;

  public String render() {
    return new Page(texts.leaderboardTitle(),
      h1(attrs(".display-1"), texts.leaderboardTitle()),
      table(attrs(".table"),
        thead(
          tr(
            th("Name"),
            th("Score"),
            th("time")
          ),
          each(contests, contest -> tr(
            td(a(contest.getName()).withHref("/contest/" + contest.getId())),
            td(Long.toString(contest.getScore())),
            td(contest.getDurationAsString())
          ))
        )
      ),
      p(attrs(".text-center"),
        a(attrs(".btn.btn-outline-secondary.btn-lg.col-6"), texts.leaderboardNewLabel()).withHref("/")
      ),
      script(rawHtml("setInterval(function () { window.location.reload(); }, 10000);"))
    ).render();
  }
}
