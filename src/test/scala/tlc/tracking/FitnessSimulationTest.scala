package tlc.tracking

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FitnessSimulationTest extends Simulation {

  val httpConf = http.baseUrl("https://idyllic-root-224415.appspot.com")

  val scn = scenario("FitnessSimulation")
    .exec(http("add")
      .post("/api/run")
      .body(StringBody("[{\"id\":9,\"lat\":48.8601,\"lon\":2.3507,\"user\":\"lea\",\"timestamp\":1543775727}]")))
    .exec(http("find")
      .get("/api/run?user=lea&timestamp=1543775726,1543775729"))
    .exec(http("delete")
      .delete("/api/run/9")
      .body(StringBody("list=9")))

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}

