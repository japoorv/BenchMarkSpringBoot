plugins {
  java
  id("org.springframework.boot") version "2.7.1"
}

repositories {
  mavenCentral()
  maven { url = uri("https://repo.spring.io/plugins-release/") }
}

dependencies {
  implementation(project(":platform-library"))
  implementation(project(":hello-world"))

  compileOnly("org.projectlombok:lombok:1.18.24")
  annotationProcessor("org.projectlombok:lombok:1.18.24")

  implementation("org.springframework.boot:spring-boot-starter-web:2.7.1")
  implementation("org.springframework.boot:spring-boot-starter-actuator:2.7.1")
  implementation("io.micrometer:micrometer-registry-prometheus:1.9.2")
  implementation("com.typesafe:config:1.4.2")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") { useJUnitPlatform() }

val mainClassName = "com.example.launcher.AppLauncher"

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
  mainClass.set(mainClassName)
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
  mainClass.set(mainClassName)
}
