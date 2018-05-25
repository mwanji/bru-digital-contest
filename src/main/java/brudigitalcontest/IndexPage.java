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
      h1(texts.welcome()),
      h2(texts.startExplanation1()),
      h2(texts.startExplanation2()),
      h3(texts.startNameLabel()),
      form(
        div(input().withType("text").withName("name").isRequired()),
        submit(texts.startSubmitLabel())
      ).withAction("/contest").withMethod("post")
    ).render();
  }
}
