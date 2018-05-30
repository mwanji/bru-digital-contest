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
    return MessageFormat.format(resourceBundle.getString("intro.hi"), name);
  }

  public String introExplanation() {
    return resourceBundle.getString("intro.explanation");
  }

  public String introSubmitLabel() {
    return resourceBundle.getString("intro.submit.label");
  }

  public String answerDigital() {
    return resourceBundle.getString("answer.digital");
  }


  public String answerPhotograph() {
    return resourceBundle.getString("answer.photograph");
  }

  public String reviewIntro(String name)
  {
    return  MessageFormat.format(resourceBundle.getString("review.intro"), name);
  }

  public String reviewAppreciation(long score)
  {
    if (score < 5)
    {
      return resourceBundle.getString("review.appreciation.lt5");
    }
    if (score == 5)
    {
      return resourceBundle.getString("review.appreciation.eq5");
    }

    return resourceBundle.getString("review.appreciation.gt5");
  }

  public String reviewNextLabel()
  {
    return resourceBundle.getString("review.next.label");
  }

  public String reviewScore(long score, int max)
  {
    return MessageFormat.format(resourceBundle.getString("review.score"), score, max);
  }

  public String leaderboardTitle() {
    return resourceBundle.getString("leaderboard.title");
  }


  public String leaderboardNewLabel() {
    return resourceBundle.getString("leaderboard.new.label");
  }
}
