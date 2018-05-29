package brudigitalcontest;

import static j2html.TagCreator.*;

import brudigitalcontest.html.Page;
import j2html.tags.ContainerTag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContestPage {

  private final Long id;
  private final Contest contest;
  private final Texts texts;

  public String render() {
    return new Page("Contest",
      div(attrs(".row"),
        div(attrs(".contestBtnCol.col-3.text-right"),
          button(attrs("#digitalBtn.contestAnswerBtn.btn.btn-outline-secondary"), texts.answerDigital()).withValue("digital")
        ),
        div(attrs(".col-6.contestAnswerPhotoCol"),
          div(attrs(".carousel.slide"),
            div(attrs(".carousel-inner"),
              carouselItem(contest.getAnswers().get(0), true),
              each(contest.getAnswers().subList(1, 10), answer -> carouselItem(answer, false)))
          ).withData("interval", "false")
            .withData("keyboard", "false")
            .withData("wrap", "false")
        ),
        div(attrs(".contestBtnCol.col-3"),
          button(attrs("#analogBtn.contestAnswerBtn.btn.btn-outline-secondary"), texts.answerPhotograph()).withValue("analog")
        )
      ),
      script(rawHtml("const APP = { contestId: \"" + id + "\", questionNumber: 0 }"))
    ).render();
  }

  String renderIntro() {
    return new Page("Introduction",
      h1(texts.introHi(contest.getName())),
      h2(rawHtml(texts.introExplanation())),
      a(attrs(".btn.btn-primary"), texts.introSubmitLabel()).withHref("/contest/" + id)
    ).render();
  }

  public String renderReview()
  {
    return new Page("Review",
      div(attrs(".row"),
        div(attrs(".col"),
          p(texts.reviewIntro(contest.getName())),
          p(button(attrs(".contestAnswerBtn.btn.btn-outline-secondary"), texts.reviewScore(contest.getScore(), contest.getAnswers().size()))),
          p(texts.reviewAppreciation(contest.getScore())),
          p(
            a(attrs(".btn.btn-outline-secondary"), texts.reviewNextLabel()).withHref("/leaderboard")
          )
        )
      )
    ).render();
  }

  private ContainerTag carouselItem(Answer answer, boolean active) {
    return div(attrs(".contestAnswerPhoto.carousel-item" + (active ? ".active" : "")),
      img(attrs(".contestAnswerPhoto")).withSrc("/photos/" + answer.getPhotoId() + ".jpg")
    ).withData("photoId", answer.getPhotoId());
  }
}
