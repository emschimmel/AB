package nl.emschimmel.ab.router

import akka.util.Timeout
import nl.emschimmel.ab.service.AbService

class HttpRouter(abService: AbService) {

    import scala.concurrent.duration._

    private implicit val internalTimeout = Timeout(10.seconds)

//    def hello(helloService: ActorRef)(implicit ctx: ExecutionContext): Route =
//        path("api" / parameter) { parameter =>
//            get {
//                onSuccess(helloService ? abService.(number)) {
//                    case Hello(msg) => complete(msg)
//                    case _ => complete(StatusCodes.InternalServerError)
//                }
//            }
//        }

}
