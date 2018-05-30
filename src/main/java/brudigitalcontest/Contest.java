package brudigitalcontest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class Contest {

  @NotBlank
  private String name;
  @Size(min = 10, max = 10)
  private List<Answer> answers;
  private Instant createdAt = Instant.now();
  @Setter
  private Instant endedAt;

  public Contest(String name, List<Answer> answers) {
    this.name = name;
    this.answers = answers;
  }

  public Duration getDuration() {
    return Duration.between(createdAt, endedAt);
  }

  public String getDurationAsString()
  {
    long s = getDuration().getSeconds();
    return String.format("%02d:%02d", (s / 60), (s % 60));
  }

  public boolean hasEnded()
  {
    return endedAt != null;
  }

  public long getScore()
  {
    return answers.stream().filter(Answer::isCorrect).count();
  }
}
