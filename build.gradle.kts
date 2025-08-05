plugins {
    id("java")
    id("maven-publish")
    id("io.freefair.lombok") version "8.7.1"
    id("com.gradleup.shadow") version "8.3.6"
    id("de.eldoria.plugin-yml.bukkit") version "0.7.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    compileOnly("com.github.SlimefunGuguProject:Slimefun4:2025.1")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("net.guizhanss:GuizhanLibPlugin:2.3.0")
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
}

group = "io.github.thebusybiscuit"
version = "UNOFFICIAL"

java {
    disableAutoTargetJvm()
    sourceCompatibility = JavaVersion.VERSION_17
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.shadowJar {
    fun doRelocate(from: String) {
        val last = from.split(".").last()
        relocate(from, "io.github.thebusybiscuit.extraheads.libs.$last")
    }
    doRelocate("org.bstats")
    doRelocate("javax.annotation")
    minimize()
    archiveClassifier = ""
}

bukkit {
    main = "io.github.thebusybiscuit.extraheads.ExtraHeads"
    apiVersion = "1.18"
    authors = listOf("TheBusyBiscuit", "ybw0014")
    description = "A Slimefun Addon that adds heads of mobs"
    website = "https://github.com/SlimefunGuguProject/ExtraHeads"
    depend = listOf("Slimefun")
    softDepend = listOf("PlaceholderAPI", "GuizhanLibPlugin")
}

tasks {
    runServer {
        downloadPlugins {
            // Slimefun
            url("https://builds.guizhanss.com/api/download/SlimefunGuguProject/Slimefun4/master/218")
            // GuizhanLibPlugin
            url("https://builds.guizhanss.com/api/download/ybw0014/GuizhanLibPlugin/master/latest")
            // SlimeHUD
            url("https://builds.guizhanss.com/api/download/SlimefunGuguProject/SlimeHUD/master/latest")
            // GuizhanCraft for testing convenient
            url("https://builds.guizhanss.com/api/download/ybw0014/GuizhanCraft/master/latest")
        }
        jvmArgs("-Dcom.mojang.eula.agree=true")
        minecraftVersion("1.21.6")
    }
}
