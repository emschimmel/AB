package nl.emschimmel.ab.repositories.databaseModel

import java.util.UUID

import com.outworkers.phantom.dsl._

case class ExperimentTable(
                              id: UUID,
                              name: String,
                              variants: Set[String],
                              timestamp: Int
                          )

abstract class experimentRecord() extends Table[experimentRecord, ExperimentTable] {

    object id extends UUIDColumn with PartitionKey {
        // You can override the name of your key to whatever you like.
        // The default will be the name used for the object, in this case "id".
        override lazy val name = "the_primary_key"
    }
    object name extends StringColumn
    object variants extends SetColumn[String]
    object timestamp extends DateTimeColumn with ClusteringOrder with Ascending

}

class ExperimentsDatabase(override val connector: CassandraConnection) extends Database[ExperimentsDatabase](connector) {
    object experimentstable extends experimentRecord with Connector {
        override def tableName: String = "experiments"
    }
}

