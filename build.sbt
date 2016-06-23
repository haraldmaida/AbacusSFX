import scala.xml._
import sbtrelease._

name := "abacus-sfx"

description := "Abacus - Implementation using ScalaFX and ScalaTestFX"

organization := "com.innoave"
homepage := Some(url("https://github.com/haraldmaida/AbacusSFX"))
startYear := Some(2016)
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

mainClass in (Compile, run) := Some("com.innoave.abacus.fxui.app.FxUiApp")

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "io.scalatestfx" %% "scalatestfx" % "0.0.2-alpha" % Test,
  "org.scalafx" %% "scalafx" % "8.0.92-R10"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-feature")
javacOptions ++= Seq("-target", "1.8", "-source", "1.8", "-Xlint:deprecation")
fork := true
parallelExecution in Test := false

enablePlugins(
  GitBranchPrompt,
  GitVersioning
)

packageOptions <+= (name, version, organization) map {
  (title, version, vendor) =>
    Package.ManifestAttributes(
      "Created-By" -> "Simple Build Tool",
      "Built-By" -> Option(System.getenv("JAR_BUILT_BY")).getOrElse(System.getProperty("user.name")),
      "Build-Jdk" -> System.getProperty("java.version"),
      "Specification-Title" -> title,
      "Specification-Version" -> version,
      "Specification-Vendor" -> vendor,
      "Implementation-Title" -> title,
      "Implementation-Version" -> version,
      "Implementation-Vendor-Id" -> vendor,
      "Implementation-Vendor" -> vendor
    )
}

//
// Git Versioning
//
git.baseVersion := "0.0.0"
val VersionTagRegex = "^v([0-9]+.[0-9]+.[0-9]+)(-.*)?$".r
git.gitTagToVersionNumber := {
  case VersionTagRegex(v,"") => Some(v)
  case VersionTagRegex(v,s) => Some(s"$v$s")  
  case _ => None
}
git.useGitDescribe := true

//
// Release Process
//
releaseProcess := ReleaseProcess.steps 
// use next version instead of current developer version
releaseVersion <<= (releaseVersionBump)(bumper => {
    ver => Version(ver).map(_.withoutQualifier).map(_.bump(bumper).string).getOrElse(versionFormatError)
})

//
// Publishing
//
publishMavenStyle := true
bintrayReleaseOnPublish := false
bintrayOrganization in bintray := None

// Metadata needed by Maven Central
// See also http://maven.apache.org/pom.html#Developers
pomExtra <<= (pomExtra, name, description) {
    (pom, name, desc) => pom ++ Group(
      <scm>
        <url>https://github.com/haraldmaida/AbacusSFX</url>
        <connection>scm:git:https://github.com/haraldmaida/AbacusSFX.git</connection>
      </scm>
      <developers>
        <developer>
          <id>haraldmaida</id>
          <name>Harald Maida</name>
          <url>https://github.com/haraldmaida</url>
        </developer>
      </developers>
    )
  }
