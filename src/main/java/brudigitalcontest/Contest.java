package brudigitalcontest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Contest {

  @NotBlank
  private String name;
  @Size(min = 10, max = 10)
  private List<Answer> answers;
}
