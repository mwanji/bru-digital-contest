package brudigitalcontest;

import lombok.*;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contest {

  private Long id;
  @NotBlank
  @Getter(onMethod = @__(@ColumnName("contestant_name")))
  private String name;
  @Size(min = 10, max = 10)
  private List<Answer> answers = new ArrayList<>();
  @Getter(onMethod = @__(@ColumnName("created_at")))
  private Instant createdAt = Instant.now();
  @Getter(onMethod = @__(@ColumnName("ended_at")))
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
