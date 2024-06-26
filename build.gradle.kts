import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("java")
	id("java-library")
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21" apply false
	kotlin("plugin.jpa") version "1.9.21" apply false
	id("org.springframework.boot") version "3.2.1" apply false
	id("io.spring.dependency-management") version "1.1.4"
}

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
	group = "com.pophory"
	version = "0.0.1-SNAPSHOT"


	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

allprojects {
	group = "com.pophory"
	version = "0.0.1-SNAPSHOT"


	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	repositories {
		mavenCentral()
	}

}

subprojects {
	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("kotlin-spring")
	}

	dependencies {
		implementation(kotlin("stdlib"))
		implementation("org.jetbrains.kotlin:kotlin-reflect")

		// lombok
		annotationProcessor("org.projectlombok:lombok")
		compileOnly("org.projectlombok:lombok")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.springframework.security:spring-security-test")

		// kotest
		val kotestVersion = "5.8.0"
		testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
		testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
	}

	configurations {
		"compileOnly" {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

project("pophory-api") {
	apply {
		plugin("io.spring.dependency-management")
		plugin("org.springframework.boot")
	}

	dependencies {
		api(project(":pophory-common"))
		api(project(":pophory-domain"))
		api(project(":pophory-external"))

		// swagger"
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
		implementation("org.springdoc:springdoc-openapi-starter-common:2.3.0")

		// Spring boot
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-web")

		// JWT
		implementation("io.jsonwebtoken", "jjwt-api", "0.11.5")
		implementation("io.jsonwebtoken", "jjwt-impl", "0.11.5")
		implementation("io.jsonwebtoken", "jjwt-jackson", "0.11.5")
		implementation("com.google.code.gson:gson:2.10.1")

		// FCM
		implementation("com.google.firebase:firebase-admin:9.1.1")


		// AWS S3
		implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

		// Security
		implementation("org.springframework.boot:spring-boot-starter-security")
		testImplementation("org.springframework.security:spring-security-test")
	}


	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = true
	jar.enabled = true

}

project("pophory-common") {
	apply {
		plugin("io.spring.dependency-management")
		plugin("org.springframework.boot")
	}

	dependencies {
		api(project(":pophory-external"))
		// Spring boot
		implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation ("org.springframework.boot:spring-boot-starter-web")

		// sentry
		implementation("io.sentry:sentry-spring-boot-starter:6.23.0")

		// JWT
		implementation("io.jsonwebtoken", "jjwt-api", "0.11.5")
		implementation("io.jsonwebtoken", "jjwt-impl", "0.11.5")
		implementation("io.jsonwebtoken", "jjwt-jackson", "0.11.5")
		implementation("com.google.code.gson:gson:2.10.1")

		// Security
		implementation("org.springframework.boot:spring-boot-starter-security")
		testImplementation("org.springframework.security:spring-security-test")

		//querydsl 추가
		implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
		annotationProcessor ("com.querydsl:querydsl-apt:5.0.0:jakarta")
		annotationProcessor ("jakarta.annotation:jakarta.annotation-api")
		annotationProcessor ("jakarta.persistence:jakarta.persistence-api")

		// swagger
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
		implementation("org.springdoc:springdoc-openapi-starter-common:2.3.0")
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true

}

project("pophory-domain") {
	apply {
		plugin("io.spring.dependency-management")
		plugin("org.springframework.boot")
	}

	dependencies {
		api(project(":pophory-common"))
		implementation("org.springframework.boot:spring-boot-starter-web")

		//querydsl 추가
		implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
		annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
		annotationProcessor("jakarta.annotation:jakarta.annotation-api")
		annotationProcessor("jakarta.persistence:jakarta.persistence-api")

		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")

		// Database
		runtimeOnly("com.h2database:h2")
		runtimeOnly("com.mysql:mysql-connector-j")
		runtimeOnly("org.postgresql:postgresql")
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true

}

project("pophory-external") {
	apply {
		plugin("io.spring.dependency-management")
		plugin("org.springframework.boot")
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true

}
tasks.getByName<Jar>("jar").enabled = false