package brudigitalcontest;

import brudigitalcontest.html.GridPage;
import brudigitalcontest.html.Page;
import j2html.tags.ContainerTag;
import j2html.tags.DomContent;
import lombok.RequiredArgsConstructor;

import static brudigitalcontest.html.Bootstrap.*;
import static j2html.TagCreator.*;

@RequiredArgsConstructor
public class ContestPage {

  private final Contest contest;
  private final Texts texts;
  private int photoReviewColIndex = 0;
  private int photoReviewRowIndex = 1;

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

  public String renderReview() {
    return new GridPage().render(
      div(attrs(".reviewGrid.p-2.text-white"),
        div(attrs("#reviewTextContainer.text-white.text-center"),
          div(attrs(".bru-bg-light"),
            h1(attrs(".mt-3.mb-3"), texts.reviewIntro(contest.getName())),
            div(attrs(".circularBtn.bg-white.bru-text-light.mx-auto.my-5.d-inline-block"), h3(texts.reviewScore(contest.getScore(), contest.getAnswers().size()))),
            p(attrs(".my-5"), texts.reviewAppreciation(contest.getScore()))
          ),
          a(attrs(".bru-bg-dark.text-white"), h2(texts.reviewNextLabel())).withHref("/leaderboard"),
          a(attrs(".bru-bg-dark.text-white"), h2(texts.leaderboardNewLabel())).withHref("/")
        ),
        each(contest.getAnswers(), this::reviewPhoto)
      )
    );
  }

  private ContainerTag carouselItem(Answer answer, boolean active) {
    return div(attrs(".contestAnswerPhoto.carousel-item" + (active ? ".active" : "")),
      img(attrs(".contestAnswerPhoto.img-fluid")).withSrc("/photos/" + answer.getPhotoId() + ".jpg")
    ).withData("photoId", answer.getPhotoId());
  }

  private DomContent reviewPhoto(Answer answer)
  {
    int colIndex = ++photoReviewColIndex;
    String colClass = "bru-review-photo-col-" + colIndex;
    String rowClass = "bru-review-photo-row-" + photoReviewRowIndex;

    if (photoReviewColIndex == 5) {
      photoReviewColIndex = 0;
      photoReviewRowIndex = photoReviewRowIndex == 1 ? 2 : 1;
    }

    return join(
      img().withSrc("/photos/" + answer.getPhotoId() + ".jpg").withClasses("img-fluid", rowClass, colClass),
      h3(answer.getCorrectAnswer()).withClasses(rowClass, colClass, "bru-review-photo-text", "mr-2", "mb-2", (answer.isCorrect() ? "reviewRightAnswer" : "reviewWrongAnswer"))
    );
  }
}
