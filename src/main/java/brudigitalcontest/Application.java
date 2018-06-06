package brudigitalcontest;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import spark.Spark;

import java.util.Map;
import java.util.OptionalInt;

import static spark.Spark.*;

public class Application {

  private static Map<String, String> environment;


  public static void main(String[] args) throws ParseException {
    environment = new ProcessBuilder().environment();
    deploymentPort().ifPresent(Spark::port);
    staticFiles.location("web");
    staticFiles.expireTime(24 * 60 * 60);

    Options cliOptions = new Options();
    cliOptions.addOption("bruJdbcUrl", true, "The JDBC url");
    cliOptions.addOption("bruJdbcUser", true, "The JDBC user name");
    cliOptions.addOption("bruJdbcPassword", true, "The JDBC user password");
    CommandLine cli = new DefaultParser().parse(cliOptions, args);

    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(cli.getOptionValue("bruJdbcUrl"));
    config.setUsername(cli.getOptionValue("bruJdbcUser"));
    config.setPassword(cli.getOptionValue("bruJdbcPassword"));
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    config.setSchema("brudigitalcontest");

    HikariDataSource ds = new HikariDataSource(config);

    Db db = new Db();

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
