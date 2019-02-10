package nl.emschimmel.ab.service

import nl.emschimmel.ab.model.{AbExperiment, ExperimentConditionViolations}
import nl.emschimmel.ab.repositories.AbRepository


class AbService(abRepository: AbRepository,
                abExperimentValidator: AbExperimentValidator
               ) {

    import nl.emschimmel.ab.model.SeverityCheck._

//    def getExperimentForCondition(name: String): Option[AbExperiment] = {
//         val result: Option[AbExperiment] = abRepository.getExperiment(name).onComplete {
//             case data => return data
//             case _ => return None
//         }
//
//    }

    def storeExperiment(abExperiment: AbExperiment): Seq[ExperimentConditionViolations] = {
        val validations = abExperimentValidator.validate(abExperiment)
        val error: Boolean = SearchSeverityError.seqContains(validations)
        if (! error) {
            abRepository.storeExperiment(abExperiment)
        }
        validations
    }

}
