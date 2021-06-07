//package utils
//
//import org.microshed.testing.testcontainers.spi.ServerAdapter
//import org.microshed.testing.testcontainers.spi.ServerAdapter.PRIORITY_RUNTIME_MODULE
//import org.testcontainers.images.builder.ImageFromDockerfile
//import java.io.File
//import java.util.*
//
//
//class PayaraMicroAdapter : ServerAdapter {
//
//    override fun getPriority(): Int {
//        return PRIORITY_RUNTIME_MODULE
//    }
//
//    override fun getDefaultHttpPort(): Int {
//        return 8080
//    }
//
//    override fun getDefaultHttpsPort(): Int {
//        return 8181
//    }
//
//    override fun getDefaultImage(appFile: File): ImageFromDockerfile {
//        val appName = appFile.name
//        // Compose a docker image equivalent to doing:
//        // FROM payara/micro:5.193
//        // CMD ["--deploymentDir", "/opt/payara/deployments", "--noCluster"]
//        // ADD target/myservice.war /opt/payara/deployments/
//        return ImageFromDockerfile()
//            .withDockerfileFromBuilder { builder ->
//                builder.from("payara/micro:jdk11")
//                    .cmd("--deploymentDir", "/opt/payara/deployments/", "--noCluster")
//                    .add(appName, "/opt/payara/deployments/")
//                    .build()
//            }
//            .withFileFromFile(appName, appFile)
//    }
//
//    val readinessPath: Optional<String>
//        get() = Optional.of("/health")
//}