plugins {
    id("java-library")
    id("maven-publish")
}

group = "net.astraleth"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // annotations
    implementation("org.jetbrains:annotations:24.0.0")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.astraleth"
            artifactId = "AstraMod"
            version = "1.0-SNAPSHOT"


            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://repo.noloy.services/repository/astraleth/")
            credentials {
                username = extra["repoUser"].toString()
                password = extra["repoPassword"].toString()
            }
        }
    }
}