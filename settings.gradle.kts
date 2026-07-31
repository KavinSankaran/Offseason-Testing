pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
		maven("https://repo.dairy.foundation/releases")
	}
}

/*includeBuild("../NextFTCSuite-Test") {
	dependencySubstitution {
		substitute(module("dev.nextftc.v2:robot"))
			.using(project(":robot"))

		substitute(module("dev.nextftc.v2:hardware"))
			.using(project(":hardware"))

		substitute(module("dev.nextftc.v2:control"))
			.using(project(":control"))

		substitute(module("dev.nextftc.v2:units"))
			.using(project(":units"))

		substitute(module("dev.nextftc.v2:linalg"))
			.using(project(":linalg"))
	}
}*/