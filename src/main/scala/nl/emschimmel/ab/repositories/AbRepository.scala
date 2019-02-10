package nl.emschimmel.ab.repositories

import java.util.UUID

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import nl.emschimmel.ab.model.AbExperiment
import nl.emschimmel.ab.repositories.databaseModel.ExperimentsDatabase

import scala.concurrent.Future
import scala.util.control.NonFatal

class AbRepository(connector: CassandraConnection) extends ExperimentsDatabase(connector) {

    def storeExperiment(abExperiment: AbExperiment): Future[AbExperiment] = {
        abExperiment.id match {
            case Some(_) =>
                insertExperiment(abExperiment)
            case None =>
                updateExperiment(abExperiment)
        }

    }

    private def insertExperiment(abExperiment: AbExperiment): Future[AbExperiment] = {
        experimentstable.update
            .where(_.id eqs UUID.fromString(abExperiment.id.get))
            .modify(_.name setTo abExperiment.name)
            .and(_.variants setTo abExperiment.variants)
            .future()
            .map(_ => abExperiment)
            .recoverWith {
                case NonFatal(e) => Future.failed(e)
            }
    }

    private def updateExperiment(abExperiment: AbExperiment): Future[AbExperiment] = {
        experimentstable.insert
            .value(_.id, UUID.randomUUID)
            .value(_.name, abExperiment.name)
            .value(_.variants, abExperiment.variants)
            .ifNotExists()
            .future()
            .map(_ => abExperiment)
            .recoverWith {
                case NonFatal(e) => Future.failed(e)
            }
    }

    def getExperiment(experimentName: String): Future[Option[AbExperiment]] = {
        experimentstable.select
//            .where(_.name eqs experimentName)
            .one()
            .map{
                case Some(exp) => Option(AbExperiment(Option(exp.id.toString), exp.name, exp.variants))
                case _ => None
            }
            .recoverWith {
                case NonFatal(e) => Future.failed(e)
            }
    }

}
