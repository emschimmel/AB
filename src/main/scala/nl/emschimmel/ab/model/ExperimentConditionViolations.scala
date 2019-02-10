package nl.emschimmel.ab.model


sealed trait Severity {
    val text: String
}

case class Info() extends Severity() {
    override val text : String = "info"
}
case class Warning() extends Severity() {
    override val text : String = "warning"
}
case class Error() extends Severity() {
    override val text : String = "error"
}

sealed trait ExperimentConditionViolations{
    val field: String
    val message: String
    val severity: Severity
}

case class MaxLengthViolation(field: String) extends ExperimentConditionViolations() {
    override val message = "max length violated"
    override val severity = Error()
}

case class MinLengthViolation(field: String) extends ExperimentConditionViolations() {
    override val message = "min length violated"
    override val severity = Info()
}

case class CollectionEmpty(field: String) extends ExperimentConditionViolations() {
    override val message = "collection empty"
    override val severity = Info()
}

case class FieldEmptyWarning(field: String) extends ExperimentConditionViolations() {
    override val message = "field is empty"
    override val severity = Warning()
}

case class RandomWarning(field: String) extends ExperimentConditionViolations() {
    override val message = "TBD"
    override val severity = Warning()
}

abstract class TestForSeverity[S] {
    def seqContains(violations: Seq[ExperimentConditionViolations]): Boolean = {
        violations.exists(_.severity.isInstanceOf[S @unchecked])
    }
}

object SeverityCheck {

    implicit object SearchSeverityError extends TestForSeverity[Error]
    implicit object SearchSeverityWarning extends TestForSeverity[Warning]
    implicit object SearchSeverityInfo extends TestForSeverity[Info]
}

