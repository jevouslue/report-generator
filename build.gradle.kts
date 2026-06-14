
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral() // 1. buildscript用のリポジトリ（倉庫）を指定
    }
    dependencies {
        // 2. OracleのJDBCドライバ本体
        classpath("com.oracle.database.jdbc:ojdbc11:23.26.2.0.0")
        // 3. Flyway 10以降で必須になった、Flyway公式のOracle用拡張プラグイン
        classpath("org.flywaydb:flyway-database-oracle:12.8.1")
    }
}

plugins {
    application
    id("com.google.devtools.ksp") version "2.3.9"
    kotlin("jvm") version "2.4.0"

    id("org.flywaydb.flyway") version "12.8.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.example.MainKt")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
dependencies {
    val komapperVersion = "7.0.0"
    platform("org.komapper:komapper-platform:$komapperVersion").let {
        implementation(it)
        ksp(it)
    }
    implementation("org.komapper:komapper-template:${komapperVersion}")
    implementation("org.komapper:komapper-starter-jdbc")
    implementation("org.komapper:komapper-dialect-oracle-jdbc")
    ksp("org.komapper:komapper-processor")

    // 1. Spring JDBC 本体
    implementation("org.springframework:spring-jdbc:7.0.8")
    // 2. Oracle JDBC ドライバー
    implementation("com.oracle.database.jdbc:ojdbc11:23.26.2.0.0")
    // 3. Jxls
    implementation("org.jxls:jxls-poi:3.1.0")
    implementation("org.apache.logging.log4j:log4j-core:2.26.0")
}

repositories {
    mavenCentral()
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }

    withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}