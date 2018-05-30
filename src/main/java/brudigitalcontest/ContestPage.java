package brudigitalcontest;

import brudigitalcontest.html.Page;
import j2html.tags.ContainerTag;
import j2html.tags.DomContent;
import lombok.AllArgsConstructor;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class ContestPage {

  private final Contest contest;
  private final Texts texts;

  public String render() {
    return new Page("Contest",
      div(attrs(".row"),
        div(attrs(".contestBtnCol.col-3.text-right"),
          button(attrs("#digitalBtn.contestAnswerBtn.circularBtn.btn.btn-outline-secondary"), texts.answerDigital()).withValue("digital")
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
          button(attrs("#analogBtn.contestAnswerBtn.circularBtn.btn.btn-outline-secondary"), texts.answerPhotograph()).withValue("photo")
        )
      ),
      script(rawHtml("const APP = { contestId: \"" + contest.getId() + "\", questionNumber: 0 }"))
    ).render();
  }

  String renderIntro() {
    return new Page("Introduction",
      h1(texts.introHi(contest.getName())),
      h2(rawHtml(texts.introExplanation())),
      a(attrs(".btn.btn-outline-primary.btn-lg.col-4"), texts.introSubmitLabel()).withHref("/contest/" + contest.getId())
    ).render();
  }

  public String renderReview()
  {
    return new Page("Review",
      div(attrs(".row"),
        div(attrs(".col-3.text-center"),
          p(texts.reviewIntro(contest.getName())),
          p(button(attrs(".circularBtn.btn.btn-outline-secondary"), texts.reviewScore(contest.getScore(), contest.getAnswers().size()))),
          p(texts.reviewAppreciation(contest.getScore())),
          p(
            a(attrs(".btn.btn-outline-secondary.btn-block"), texts.reviewNextLabel()).withHref("/leaderboard")
          ),
          p(
            a(attrs(".btn.btn-outline-primary.btn-block"), texts.leaderboardNewLabel()).withHref("/")
          )
        ),
        div(attrs(".col"),
          div(attrs(".container-fluid"),
            div(attrs(".row"), each(contest.getAnswers().subList(0, 5), this::reviewPhoto)),
            div(attrs(".row.mt-2"), each(contest.getAnswers().subList(5, 10), this::reviewPhoto))
          )
        )
      )
    ).render();
  }

  private ContainerTag carouselItem(Answer answer, boolean active) {
    return div(attrs(".contestAnswerPhoto.carousel-item" + (active ? ".active" : "")),
      img(attrs(".contestAnswerPhoto.img-fluid")).withSrc("/photos/" + answer.getPhotoId() + ".jpg")
    ).withData("photoId", answer.getPhotoId());
  }

  private DomContent reviewPhoto(Answer answer)
  {
    return div(attrs(".col-2"),
      div(attrs(".reviewPhotoContainer"),
        img(attrs(".img-fluid")).withSrc("/photos/" + answer.getPhotoId() + ".jpg"),
        p(answer.getCorrectAnswer()).withCondClass(!answer.isCorrect(), "reviewWrongAnswer")
      )
    );
  }
}
