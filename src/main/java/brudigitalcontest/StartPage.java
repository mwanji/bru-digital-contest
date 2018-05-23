package brudigitalcontest;

import brudigitalcontest.html.Page;
import lombok.AllArgsConstructor;

import static brudigitalcontest.html.Bootstrap.*;
import static j2html.TagCreator.*;

@AllArgsConstructor
public class StartPage {

  private final Texts texts;

  public String render() {
    return new Page("Start",
      h1(texts.welcome()),
      h2(texts.startExplanation1()),
      h2(texts.startExplanation2()),
      h2(texts.startNameLabel()),
      form(
        formGroup(label(texts.startNameLabel()), input()),
        submit(texts.startSubmitLabel())
      ).withAction("/contest").withMethod("post")
    ).render();
  }
}
