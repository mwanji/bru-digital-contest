package brudigitalcontest;

import brudigitalcontest.html.Bootstrap;
import brudigitalcontest.html.Page;
import j2html.tags.ContainerTag;
import lombok.AllArgsConstructor;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class ContestPage {

  private final Long id;
  private final Contest contest;

  public String render() {
    return new Page("Contest",
      div(attrs(".carousel.slide"),
        div(attrs(".carousel-inner"),
          carouselItem(contest.getAnswers().get(0), true),
          each(contest.getAnswers().subList(1, 10), answer -> carouselItem(answer, false)))
      ).withData("interval", "false")
        .withData("keyboard", "false")
        .withData("wrap", "false"),
      div(
        button(attrs("#prevSlideBtn.btn.btn-light.btn-lg"), Bootstrap.icon("chevron-left")),
        button(attrs("#digitalBtn.contestAnswerBtn.btn.btn-light.ml-3.w-25.btn-lg"), "Digital").withValue("digital"),
        button(attrs("#analogBtn.contestAnswerBtn.btn.btn-light.ml-3.w-25.btn-lg"), "Analog").withValue("analog")
      ),
      script(rawHtml("const APP = { contestId: \"" + id + "\", questionNumber: 0 }"))
    ).render();
  }

  private ContainerTag carouselItem(Answer answer, boolean active) {
    return div(attrs(".carousel-item" + (active ? ".active" : "")),
      img(attrs(".d-block.w-100")).withSrc("http://via.placeholder.com/900x500")
    ).withData("photoId", answer.getPhotoId());
  }
}
