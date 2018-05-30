package brudigitalcontest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class Db {

  private final ConcurrentMap<Class<?>, List<?>> memDb = new ConcurrentHashMap<>();

  public <T> T save(T entity) {
    List<T> objects = (List<T>) memDb.computeIfAbsent(entity.getClass(), entityClass -> new ArrayList<>());
    objects.add(entity);
    return entity;
  }

  public <T> T get(Class<T> entityClass, Long id) {
    return entityClass.cast(memDb.getOrDefault(entityClass, Collections.emptyList()).get(id.intValue()));
  }

  public <T> List<T> getAll(Class<T> entityClass) {
    return (List<T>) memDb.getOrDefault(entityClass, Collections.emptyList());
  }

  public List<Contest> getLeaderboard()
  {
    return getAll(Contest.class).stream()
            .filter(Contest::hasEnded)
            .sorted(Comparator.comparing(Contest::getScore).reversed().thenComparing(Contest::getDuration))
            .collect(Collectors.toList());
  }
}
