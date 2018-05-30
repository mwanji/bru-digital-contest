package brudigitalcontest;

import brudigitalcontest.html.Page;
import lombok.AllArgsConstructor;

import static brudigitalcontest.html.Bootstrap.*;
import static j2html.TagCreator.*;

@AllArgsConstructor
public class IndexPage {

  private final Texts texts;

  public String render() {
    return new Page("Start",
      div(attrs(".text-center"),
        h1(attrs(".display-2"), texts.welcome()),
        h2(attrs(".display-3"), texts.startExplanation1()),
        h2(attrs(".mt-5"), texts.startExplanation2()),
        form(attrs(".mt-5"),
          div(attrs(".form-group"),
            label(h3(texts.startNameLabel())),
            input(attrs(".form-control.col-4.m-auto")).withType("text").withName("name").isRequired()
          ),
          submit(texts.startSubmitLabel())
        ).withAction("/contest").withMethod("post")
      )
    ).render();
  }
}
