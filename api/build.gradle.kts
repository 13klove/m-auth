dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":external"))
    implementation(project(":message"))


    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    //cloud
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

springBoot.buildInfo { properties { } }

configurations {
    val archivesBaseName = "com.m.one"
}



//implementation("org.springframework.boot:spring-boot-starter-webflux")
//implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
//implementation("org.jetbrains.kotlin:kotlin-reflect")
//implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
//developmentOnly("org.springframework.boot:spring-boot-devtools")
//annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
//testImplementation("org.springframework.boot:spring-boot-starter-test")
//testImplementation("io.projectreactor:reactor-test")