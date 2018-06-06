package brudigitalcontest;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import spark.Spark;

import java.util.Map;
import java.util.OptionalInt;

import static spark.Spark.*;

public class Application {

  private static Map<String, String> environment;


  public static void main(String[] args) {
    environment = new ProcessBuilder().environment();
    deploymentPort().ifPresent(Spark::port);
    staticFiles.location("web");
    staticFiles.expireTime(24 * 60 * 60);

    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(environment.get("BRU_JDBC_URL") + "?zeroDateTimeBehavior=convertToNull");
    config.setUsername(environment.get("BRU_JDBC_USER"));
    config.setPassword(environment.get("BRU_JDBC_PASSWORD"));
    config.setMinimumIdle(2);
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    HikariDataSource ds = new HikariDataSource(config);
    Jdbi jdbi = Jdbi.create(ds);
    jdbi.registerRowMapper(BeanMapper.factory(Contest.class));
    jdbi.registerRowMapper(BeanMapper.factory(Answer.class));
    Db db = new Db(jdbi);

    Texts texts = new Texts();
    ContestController contestController = new ContestController(texts, db);
    get("/", contestController::getIndex);
    post("contest", contestController::postStart);
    get("contest/:id", contestController::getContest);
    get("contest/:id/intro", contestController::getIntro);
    post("contest/:id/question/:questionId", contestController::postAnswer);

    LeaderboardController leaderboardController = new LeaderboardController(db, texts);
    get("leaderboard", leaderboardController::getLeaderboard);
  }

  private static OptionalInt deploymentPort() {
    if (environment.get("PORT") != null) {
      return OptionalInt.of(Integer.parseInt(environment.get("PORT")));
    }
    return OptionalInt.empty();
  }

}
