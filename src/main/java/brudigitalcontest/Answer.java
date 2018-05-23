package brudigitalcontest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class Answer {

  private String photoId;
  private int index;
  @Setter
  private boolean digital;

  public Answer(String photoId, int index) {
    this.photoId = photoId;
    this.index = index;
  }
}
