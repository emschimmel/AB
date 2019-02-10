package nl.emschimmel.ab.model

case class AbExperiment(id: Option[String],
                        name: String,
                        variants: Set[String]
                       )

case class AbExperimentResult(experiment: Option[AbExperiment],
                              validationResult: Seq[ExperimentConditionViolations]
                             )