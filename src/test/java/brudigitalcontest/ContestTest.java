package brudigitalcontest;

import org.junit.jupiter.api.Test;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContestTest {

  private final Contest contest = new Contest();

  @Test
  public void duration_under_one_minute_should_be_formatted_correctly() {
    contest.setEndedAt(contest.getCreatedAt().plusSeconds(59));

    assertEquals("00:59", contest.getDurationAsString());
  }

  @Test
  public void duration_over_one_minute_should_be_formatted_correctly() {
    contest.setEndedAt(contest.getCreatedAt().plusSeconds(100));

    assertEquals("01:40", contest.getDurationAsString());
  }

  @Test
  public void duration_over_ten_minutes_should_be_formatted_correctly() {
    contest.setEndedAt(contest.getCreatedAt().plus(11, MINUTES).plusSeconds(5));

    assertEquals("11:05", contest.getDurationAsString());
  }

  @Test
  public void duration_over_one_hour_should_be_formatted_correctly() {
    contest.setEndedAt(contest.getCreatedAt().plus(61, MINUTES).plusSeconds(25));

    assertEquals("61:25", contest.getDurationAsString());
  }
}