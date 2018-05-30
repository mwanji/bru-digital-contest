package brudigitalcontest.html;

import j2html.tags.DomContent;

import static j2html.TagCreator.*;

public class Page {

  private final String title;
  private final DomContent[] contents;

  public Page(String title, DomContent... contents) {
    this.title = title;
    this.contents = contents;
  }

  public String render() {
    return document(html(
      head(
        meta().withCharset("utf-8"),
        meta().attr("name", "viewport").attr("content", "width=device-width, initial-scale=1, shrink-to-fit=no"),
        link().withRel("stylesheet").withHref("https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css").attr("integrity", "sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB").attr("crossorigin", "anonymous"),
        link().withRel("stylesheet").withHref("https://cdnjs.cloudflare.com/ajax/libs/open-iconic/1.1.1/font/css/open-iconic-bootstrap.min.css").attr("integrity", "sha256-BJ/G+e+y7bQdrYkS2RBTyNfBHpA9IuGaPmf9htub5MQ=").attr("crossorigin", "anonymous"),
        link().withRel("stylesheet").withHref("/application.css"),
        title(title + " - bru.digital")
      ),
      body(attrs(".pb-2"),
        div(attrs(".container-fluid.mt-3"), contents),
        script().withSrc("https://code.jquery.com/jquery-3.3.1.slim.min.js").attr("integrity", "sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo").attr("crossorigin", "anonymous"),
        script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js").attr("integrity", "sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49").attr("crossorigin", "anonymous"),
        script().withSrc("https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js").attr("integrity", "sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T").attr("crossorigin", "anonymous"),
        script().withSrc("/application.js")
      )
    ));
  }
}
