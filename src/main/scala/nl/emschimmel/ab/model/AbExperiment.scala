package nl.emschimmel.ab.model

case class AbExperiment(id: Option[String],
                        name: String,
                        variants: Set[String]
                       )
