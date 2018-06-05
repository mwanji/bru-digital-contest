package brudigitalcontest;

import brudigitalcontest.html.GridPage;
import lombok.AllArgsConstructor;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class IndexPage {

  private final Texts texts;

  public String render() {
    return new GridPage().render(
      div(attrs(".bru-index-bg.h-100"),
        div(attrs(".container-fluid.h-100"),
          div(attrs(".row.h-100"),
            div(attrs(".d-inline-block.col-8.m-auto.align-middle"),
              div(attrs(".splashTitleBox.text-center.w-100.p-5.mb-5"),
                h1(attrs(".display-3.bru-text-light.p-4.bru-bold"), texts.welcome())
              ),
              p(attrs(".m-5"), rawHtml("&nbsp;")),
              label(attrs(".text-white.mt-5"), h3(attrs(".bru-semi-bold"), texts.startNameLabel())),
              form(attrs(".form-inline"),
                input(attrs(".bru-bg-light.bru-height-high.bru-square.form-control.form-control-lg.col-8")).withType("text").withName("name").isRequired(),
                button(attrs(".btn.bg-white.bru-height-high.bru-text-light.bru-square.col-4"), h2(attrs(".bru-bold"), texts.startSubmitLabel())).withType("submit")
              ).withAction("/contest").withMethod("post")
            )
          )
        )
      )
    );
  }
}
