package brudigitalcontest;

import org.jdbi.v3.core.Jdbi;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Db {

  private final ConcurrentMap<Class<?>, List<?>> memDb = new ConcurrentHashMap<>();
  private final CopyOnWriteArrayList<Contest> contests = new CopyOnWriteArrayList<>();
  private final Jdbi jdbi = null;

  public Long save(Contest entity) {
    Long id = jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO contests(name, createdAt) VALUES(:name, :createdAt)")
        .bindBean(entity)
        .executeAndReturnGeneratedKeys()
        .mapTo(Long.class)
        .findOnly()
    );
    contests.add(entity);

    return (long) contests.size();
  }

  public Contest getContest(Long id) {
    return contests.get(id.intValue() - 1);
  }

  public List<Contest> getAllContests() {
    return contests;
  }

  public List<Contest> getLeaderboard()
  {
    return getAllContests().stream()
            .filter(Contest::hasEnded)
            .sorted(Comparator.comparing(Contest::getScore).reversed().thenComparing(Contest::getDuration))
            .collect(Collectors.toList());
  }
}
