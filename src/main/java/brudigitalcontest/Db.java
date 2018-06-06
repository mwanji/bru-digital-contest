package brudigitalcontest;

import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.JoinRowMapper;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Db {

  private final Jdbi jdbi;

  public Long insert(Contest entity) {
    Long id = jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO contests(contestant_name, created_at) VALUES(:name, :createdAt)")
        .bindBean(entity)
        .executeAndReturnGeneratedKeys()
        .mapTo(Long.class)
        .findOnly()
    );

    jdbi.useHandle(handle -> {
      PreparedBatch batch = handle.prepareBatch("INSERT INTO answers(correct_answer, photo_id, answer_index, contest_id) VALUES(:correctAnswer, :photoId, :index, :contestId)");

      entity.getAnswers().forEach(answer -> batch.bindBean(answer)
        .bind("contestId", id)
        .add());
      batch.execute();
    });


    return id;
  }

  public Contest getContest(Long id) {
    Contest contest = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM contests c JOIN answers a ON c.id = a.contest_id WHERE c.id = :id ORDER BY c.id, a.answer_index")
      .bind("id", id)
      .map(JoinRowMapper.forTypes(Contest.class, Answer.class))
      .reduce(new Contest(), (contestResult, row) -> {
        Contest _contest = contestResult;
        if (_contest.getId() == null) {
          _contest = row.get(Contest.class);
        }
        _contest.getAnswers().add(row.get(Answer.class));

        return _contest;
      }));

    return contest;
  }

  public List<Contest> getLeaderboard()
  {
    return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM contests c JOIN answers a ON c.id = a.contest_id ORDER BY c.id, a.answer_index")
      .reduceRows((Map<Long, Contest> map, RowView row) -> {
        Contest contest = map.computeIfAbsent(row.getColumn("id", Long.class), id -> row.getRow(Contest.class));
        contest.getAnswers().add(row.getRow(Answer.class));
      })
      .filter(Contest::hasEnded)
      .sorted(Comparator.comparing(Contest::getScore).reversed().thenComparing(Contest::getDuration))
      .collect(Collectors.toList())
    );
  }

  public void endContest(Long id) {
    jdbi.withHandle(handle -> handle.createUpdate("UPDATE contests SET ended_at = :endedAt WHERE id = :id")
      .bind("endedAt", Instant.now())
      .bind("id", id)
      .execute());
  }

  public void updateAnswer(Long id, int answerIndex, String givenAnswer) {
    jdbi.useHandle(handle -> handle.createUpdate("UPDATE answers SET given_answer = :givenAnswer WHERE contest_id = :contestId AND answer_index = :answerIndex")
      .bind("givenAnswer", givenAnswer)
      .bind("contestId", id)
      .bind("answerIndex", answerIndex)
      .execute()
    );
  }
}
