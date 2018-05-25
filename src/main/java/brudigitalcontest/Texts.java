package brudigitalcontest;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@AllArgsConstructor
public class Texts {

  private final ResourceBundle resourceBundle = ResourceBundle.getBundle("Texts", Locale.ENGLISH);

  public String welcome() {
    return resourceBundle.getString("start.welcome");
  }

  public String startExplanation1() {
    return resourceBundle.getString("start.explanation.1");
  }

  public String startExplanation2() {
    return resourceBundle.getString("start.explanation.2");
  }

  public String startNameLabel() {
    return resourceBundle.getString("start.name.label");
  }

  public String startSubmitLabel() {
    return resourceBundle.getString("start.submit.label");
  }

  public String introHi(String name) {
    return MessageFormat.format(resourceBundle.getString("intro.hi"), new Object[]{ name });
  }

  public String introExplanation() {
    return resourceBundle.getString("intro.explanation");
  }

  public String introSubmitLabel() {
    return resourceBundle.getString("intro.submit.label");
  }
}
