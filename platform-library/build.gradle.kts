plugins { `java-library` }

repositories { mavenCentral() }

dependencies {
  api("io.micrometer:micrometer-core:1.9.2")
  api("org.springframework.boot:spring-boot-starter-web:2.7.1")
  api("com.typesafe:config:1.4.2")
  implementation("org.mongodb:mongodb-driver-sync:4.6.1")
  compileOnly("org.projectlombok:lombok:1.18.24")
  annotationProcessor("org.projectlombok:lombok:1.18.24")

  testImplementation("org.testcontainers:mongodb:1.17.3")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
  testCompileOnly("org.projectlombok:lombok:1.18.24")
  testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
  testImplementation("org.slf4j:slf4j-log4j12:1.7.36")
}

tasks.getByName<Test>("test") { useJUnitPlatform() }
