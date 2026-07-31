plugins {
    id("dev.frozenmilk.teamcode") version "11.1.0-1.1.2"
    id("dev.frozenmilk.sinister.sloth.load") version "0.2.4"
}

ftc {
    sdk.TeamCode()

    kotlin()

    pedro {
        implementation(ftc("2.1.1"))
        implementation(telemetry)
    }

    dairy {
        implementation(Sloth)
        implementation(slothboard)
        implementation(ftControl.fullpanels)
    }
}

repositories {
    maven("https://maven.brott.dev/")
    maven("https://repo.dairy.foundation/releases")
    maven("https://repo.dairy.foundation/snapshots")
    maven("https://central.sonatype.com/repository/maven-snapshots/")
}

dependencies {
    implementation("dev.nextftc.v2:control:0.1.0")
    implementation("dev.nextftc.v2:hardware:0.1.0")
    implementation("dev.nextftc.v2:robot:0.1.0")
}