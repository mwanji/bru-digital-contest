package brudigitalcontest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Answer {

  private String photoId;
  private int    index;
  @Setter
  private String givenAnswer;
  private String correctAnswer;

  public Answer(String photoId, int index, String correctAnswer) {
    this.photoId = photoId;
    this.index = index;
    this.correctAnswer = correctAnswer;
  }

  public boolean isCorrect()
  {
    return correctAnswer.equals(givenAnswer);
  }
}
