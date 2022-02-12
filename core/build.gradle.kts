dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":message"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}