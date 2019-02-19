package nl.emschimmel.ab

import com.datastax.driver.core.SocketOptions
import com.outworkers.phantom.dsl._
import nl.emschimmel.ab.repositories.AbRepository
import nl.emschimmel.ab.router.HttpRouter
import nl.emschimmel.ab.service.{AbExperimentValidator, AbService}

import scala.language.reflectiveCalls

class Main {

    val cassandraConnection: CassandraConnection = ContactPoint.local
        .withClusterBuilder(_.withSocketOptions(
            new SocketOptions()
                .setConnectTimeoutMillis(20000)
                .setReadTimeoutMillis(20000)
        )
        ).noHeartbeat().keySpace(
        KeySpace("experiments").ifNotExists().`with`(
            replication eqs SimpleStrategy.replication_factor(1)
        )

    )
//    ConsistencyLevel = "LOCAL_QUORUM"

    val experimentRepository = new AbRepository(cassandraConnection)
    val experimentValidator = new AbExperimentValidator()
    val experimentService = new AbService(experimentRepository, experimentValidator)
    val router = new HttpRouter(experimentService)

}
