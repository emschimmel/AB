package nl.emschimmel.ab.service

import nl.emschimmel.ab.model.{AbExperiment, AbExperimentResult}
import nl.emschimmel.ab.repositories.AbRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success


class AbService(abRepository: AbRepository,
                abExperimentValidator: AbExperimentValidator
               ) {

    import nl.emschimmel.ab.model.SeverityCheck._

//    def getExperimentForCondition(name: String): Option[AbExperiment] = {
//         abRepository.getExperiment(name).onComplete {
//             case Success(data) => return data
//             case _ => return None
//         }
//    }

    def storeExperiment(abExperiment: AbExperiment): AbExperimentResult = {
        val validations = abExperimentValidator.validate(abExperiment)
        val error: Boolean = SearchSeverityError.seqContains(validations)
        if (! error) {
            abRepository.storeExperiment(abExperiment).onComplete {
                case Success(data) => return AbExperimentResult(Option(data), validations)
                case _ => return AbExperimentResult(None, validations)
            }
        }
        AbExperimentResult(None, validations)
    }

}
