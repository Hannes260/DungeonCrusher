plugins {
    id("java")
}

group = "net.dbsgameplay.dungeoncrusher"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
    implementation("com.zaxxer:HikariCP:5.1.0")
    compileOnly("com.mojang:authlib:5.0.47")

    // Add the DBsGameplayPaperCore project as a dependency
    implementation(project(":DBsGameplayPaperCore"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))

}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    systemProperty("file.encoding", "UTF-8")
}

tasks.withType<Javadoc>{
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}