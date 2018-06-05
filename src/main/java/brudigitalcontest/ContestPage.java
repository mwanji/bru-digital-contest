package brudigitalcontest;

import brudigitalcontest.html.GridPage;
import brudigitalcontest.html.Page;
import j2html.tags.ContainerTag;
import j2html.tags.DomContent;
import lombok.AllArgsConstructor;

import static brudigitalcontest.html.Bootstrap.row;
import static j2html.TagCreator.*;

@AllArgsConstructor
public class ContestPage {

  private final Contest contest;
  private final Texts texts;

  public String render() {
    return new GridPage().render(
      div(attrs(".contestGrid"),
        button(attrs("#analogBtn.contestBtn.btn.bru-bg-light.text-white.bru-semi-bold"), h2(texts.answerPhotograph())).withValue("photo"),
        div(
          div(attrs(".carousel.slide"),
            div(attrs(".carousel-inner"),
              carouselItem(contest.getAnswers().get(0), true),
              each(contest.getAnswers().subList(1, 10), answer -> carouselItem(answer, false)))
          ).withData("interval", "false")
            .withData("keyboard", "false")
            .withData("wrap", "false")
        ),
        div(attrs(".w-100"),
          button(attrs("#digitalBtn.contestBtn.btn.bru-bg-light.text-white.bru-semi-bold"), h2(texts.answerDigital())).withValue("digital"),
          div(attrs(".bru-bg-dark.text-white.bru-semi-bold.w-50.p-3.text-center"), h2(attrs("#contestQuestionCount"), "1/10"))
        )
      ),
      script(rawHtml("const APP = { contestId: \"" + contest.getId() + "\", questionNumber: 0 }"))
    );
  }

  String renderIntro() {
    return new Page("Introduction",
      div(attrs(".row"),
        div(attrs(".bru-bg-dark.col-2.offset-2.text-white.text-center.p-3"),
          h1(attrs(".bru-semi-bold"), texts.introWelcome())
        )
      ),
      div(attrs(".row"),
        div(attrs(".bru-bg-light.col-4.offset-4.text-white.text-center.p-3"),
          h2(attrs(".bru-semi-bold"), texts.introHi(contest.getName()))
        )
      ),
      row(div(attrs(".bru-bg-dark.col-2.offset-2.text-white.text-center.p-3"), h2(texts.introExplanation1()))),
      row(div(attrs(".bru-bg-light.col-4.offset-4.text-white.text-center.p-3"), h2(rawHtml(texts.introExplanation())))),
      row(div(attrs(".bru-bg-dark.col-2.offset-8.text-white.text-center.p-3"), h2(a(attrs(".bru-bold.text-white"), texts.introSubmitLabel()).withHref("/contest/" + contest.getId()))))
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
