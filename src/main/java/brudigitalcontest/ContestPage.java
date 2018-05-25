package brudigitalcontest;

import brudigitalcontest.html.Page;
import j2html.tags.ContainerTag;
import lombok.AllArgsConstructor;

import static j2html.TagCreator.*;

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

  private ContainerTag carouselItem(Answer answer, boolean active) {
    return div(attrs(".contestAnswerPhoto.carousel-item" + (active ? ".active" : "")),
      img(attrs(".contestAnswerPhoto")).withSrc("http://via.placeholder.com/300x500")
    ).withData("photoId", answer.getPhotoId());
  }
}
