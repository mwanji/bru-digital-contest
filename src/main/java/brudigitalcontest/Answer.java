package brudigitalcontest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@NoArgsConstructor
@Data
public class Answer {

  @Getter(onMethod = @__(@ColumnName("photo_id")))
  private String photoId;
  @Getter(onMethod = @__(@ColumnName("answer_index")))
  private int    index;
  @Getter(onMethod = @__(@ColumnName("given_answer")))
  private String givenAnswer;
  @Getter(onMethod = @__(@ColumnName("correct_answer")))
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
