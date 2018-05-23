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
  private int index;
  @Setter
  private String answer;

  public Answer(String photoId, int index) {
    this.photoId = photoId;
    this.index = index;
  }
}
