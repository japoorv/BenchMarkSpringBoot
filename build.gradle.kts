import java.lang.String.join

plugins {
    java
    id("com.diffplug.spotless") version "5.14.2"
    jacoco
}


repositories {
    mavenCentral()
}


subprojects {
    group = "com.example"
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "jacoco")
    spotless {
        java {
            importOrder()
            removeUnusedImports()
            googleJavaFormat()
            target("src/**/*.java")
        }
        kotlinGradle {
            target("*.gradle.kts") // default target for kotlinGradle
            ktfmt() // or ktfmt() or prettier()
        }

        format("misc,") {
            target(".gitignore", "*.md")
            indentWithSpaces()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
    tasks.withType<JacocoReport> {
        reports {
            csv.required.set(true)
            xml.required.set(false)
            html.required.set(true)
        }
    }
}


tasks.register("aggregateReports"){
    description = "Generates aggregated reports"
    val projects = subprojects + project
    mustRunAfter("clean")
    for (project in projects)
    {
        dependsOn(project.tasks.findByName("test"))
        dependsOn(project.tasks.findByName("jacocoTestReport"))
    }
    doLast {
        val subPath = "reports/jacoco/test/jacocoTestReport.csv"
        val reports = mutableListOf<File>()
        for (project in projects) {
            val csvReportPath = join("/", project.buildDir.absolutePath, subPath)
            val csvReportFile = File(csvReportPath)
            if (csvReportFile.isFile) {
                reports.add(csvReportFile)
            }
        }

        val reportFile = File(join("/", project.buildDir.absolutePath, subPath))
        if (reportFile.isFile) {
            reportFile.delete()
        }
        reportFile.parentFile.mkdirs()
        reportFile.createNewFile()

        val aggregatedFileLines = mutableSetOf<String>()
        for (report in reports) {
            aggregatedFileLines.addAll(report.readLines())
        }
        reportFile.printWriter().use {
            out -> aggregatedFileLines.forEach {
                out.println(it)
        }
        }

    }
}
