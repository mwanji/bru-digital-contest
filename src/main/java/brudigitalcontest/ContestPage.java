package brudigitalcontest;

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
    return document(html(
      head(
        meta().withCharset("utf-8"),
        meta().attr("name", "viewport").attr("content", "width=device-width, initial-scale=1, shrink-to-fit=no"),
        link().withRel("stylesheet").withHref("https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css").attr("integrity", "sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB").attr("crossorigin", "anonymous"),
//        link().withRel("stylesheet").withHref("https://cdnjs.cloudflare.com/ajax/libs/open-iconic/1.1.1/font/css/open-iconic-bootstrap.min.css").attr("integrity", "sha256-BJ/G+e+y7bQdrYkS2RBTyNfBHpA9IuGaPmf9htub5MQ=").attr("crossorigin", "anonymous"),
        link().withRel("stylesheet").withHref("/application.css"),
        title("Contest - bru.digital")
      ),
      body(attrs(".h-100.m-0"),
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
            button(attrs("#digitalBtn.contestBtn.btn.bru-bg-light.text-white.bru-semi-bold"), h2(texts.answerDigital())).withValue("digital")
          ),
          script(rawHtml("const APP = { contestId: \"" + contest.getId() + "\", questionNumber: 0 }")),
          script().withSrc("https://code.jquery.com/jquery-3.3.1.slim.min.js").attr("integrity", "sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo").attr("crossorigin", "anonymous"),
          script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js").attr("integrity", "sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49").attr("crossorigin", "anonymous"),
          script().withSrc("https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js").attr("integrity", "sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T").attr("crossorigin", "anonymous"),
          script().withSrc("/application.js")
        )
    ));
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
