package brudigitalcontest;

import lombok.AllArgsConstructor;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class IndexPage {

  private final Texts texts;

  public String render() {
    return document(html(attrs(".splash"),
      head(
        meta().withCharset("utf-8"),
        meta().attr("name", "viewport").attr("content", "width=device-width, initial-scale=1, shrink-to-fit=no"),
        link().withRel("stylesheet").withHref("https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css").attr("integrity", "sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB").attr("crossorigin", "anonymous"),
//        link().withRel("stylesheet").withHref("https://cdnjs.cloudflare.com/ajax/libs/open-iconic/1.1.1/font/css/open-iconic-bootstrap.min.css").attr("integrity", "sha256-BJ/G+e+y7bQdrYkS2RBTyNfBHpA9IuGaPmf9htub5MQ=").attr("crossorigin", "anonymous"),
        link().withRel("stylesheet").withHref("/application.css"),
        title("Contest - bru.digital")
      ),
      body(attrs("#bruDigitalContest.splash"),
        div(attrs(".splash"),
          div(attrs(".container-fluid"),
            div(attrs(".row"),
              div(attrs(".splashContainer.col-8.m-auto.align-middle"),
                div(attrs(".splashTitleBox.text-center.w-100.p-5.mb-5"),
                  h1(attrs(".display-3.bru-text-light.p-4"), texts.welcome())
                ),
                p(attrs(".m-5"), rawHtml("&nbsp;")),
                label(attrs(".text-white.mt-5"), h3(attrs(".bru-semi-bold"), texts.startNameLabel())),
                form(attrs(".form-inline"),
                  input(attrs(".bru-bg-light.bru-height-high.form-control.form-control-lg.col-8")).withType("text").withName("name").isRequired(),
                  button(attrs(".btn.bg-white.bru-height-high.bru-text-light.col-4"), h2(attrs(".bru-bold"), texts.startSubmitLabel())).withType("submit")
                )
              )
            )
          )
        )
      )
    ));
  }
}
