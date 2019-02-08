package tlc.tracking

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FitnessSimulationTest extends Simulation {

  val httpConf = http.baseUrl("http://127.0.0.1:8080")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("FitnessSimulation")
    .exec(http("add")
      .post("/api/run")
      .body(StringBody("[{\"id\":9,\"lat\":48.8601,\"lon\":2.3507,\"user\":\"lea\",\"timestamp\":1543775727}]")))
    .exec(http("delete")
      .delete("/api/run/9")
      .body(StringBody("list=9")))

  setUp(
    scn.inject(atOnceUsers(10)) // LAUNCH 10 request
  ).protocols(httpConf)
}

