package nl.emschimmel.ab.service

import nl.emschimmel.ab.model._

import scala.collection.mutable.ListBuffer

class AbExperimentValidator {

    def validate(abExperiment: AbExperiment): Seq[ExperimentConditionViolations] = {
        var violations = new ListBuffer[ExperimentConditionViolations]
        if (abExperiment.name.length <= 3) violations += MinLengthViolation("name")
        if (abExperiment.name.length >= 300) violations += MaxLengthViolation("name")
        if (abExperiment.id.isEmpty) violations += FieldEmptyWarning("id")
        if (abExperiment.id.nonEmpty && abExperiment.id.get.length >= 300) violations += MaxLengthViolation("id")
        if (abExperiment.variants.isEmpty) violations += CollectionEmpty("variants")
        violations
    }

}
