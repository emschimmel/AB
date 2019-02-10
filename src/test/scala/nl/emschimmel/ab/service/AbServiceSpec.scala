package nl.emschimmel.ab.service

import nl.emschimmel.ab.model._
import nl.emschimmel.ab.repositories.AbRepository
import org.scalatest._
import org.scalatest.Matchers._
import org.scalatest.mockito.MockitoSugar

class AbServiceSpec extends FlatSpec  with MockitoSugar {

    private val abRepository = mock[AbRepository]

    private val abExperimentValidator = new AbExperimentValidator()
    private val service = new AbService(abRepository, abExperimentValidator)

    it should "just work" in {
        val validExperiment = AbExperiment(Option("1"), "name", Set("A", "b"))
        val result = service.storeExperiment(validExperiment)
        result shouldBe empty
    }

    it should "fail with warnings" in {
        val invalidExperiment = AbExperiment(None, "name", Set("A", "b"))
        val expectedWarning = FieldEmptyWarning("id")
        val result: Seq[ExperimentConditionViolations] = service.storeExperiment(invalidExperiment)
        result should be equals Seq(expectedWarning)
    }

    it should "fail with errors" in {
        val r = new scala.util.Random()
        val invalidExperiment = AbExperiment(Option(r.nextString(301)), "n", Set())
        val expectedErrors = Seq(
                                MinLengthViolation("name"),
                                MaxLengthViolation("id"),
                                CollectionEmpty("variants")
                                )
        val result: Seq[ExperimentConditionViolations] = service.storeExperiment(invalidExperiment)
        result should contain theSameElementsAs expectedErrors
    }

    it should "pass with info" in {
        val validExperiment = AbExperiment(Option(""), "name", Set())
        val expectedInfo = CollectionEmpty("variants")
        val result: Seq[ExperimentConditionViolations] = service.storeExperiment(validExperiment)
        result should be equals Seq(expectedInfo)
    }
}
