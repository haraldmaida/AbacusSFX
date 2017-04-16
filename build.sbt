import sbtrelease._
import com.typesafe.sbt.packager.SettingsHelper._

name := "abacus-sfx"

description := "Abacus - Implementation using ScalaFX and ScalaTestFX"

organization := "com.innoave"
homepage := Some(url("https://github.com/haraldmaida/AbacusSFX"))
startYear := Some(2016)
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
scmInfo := Some(ScmInfo(
  url("https://github.com/haraldmaida/AbacusSFX"),
    "scm:git:https://github.com/haraldmaida/AbacusSFX.git"
))

val pkgMaintainer = "Harald Maida"
val pkgSummary = "Abacus - counting and calculating"
val pkgDescription = "Abacus - easy counting and calculating tool" 

mainClass in Compile := Some("com.innoave.abacus.fxui.app.FxUiApp")

scalaVersion := "2.12.1"

// repository for scalatestfx
resolvers += Resolver.bintrayRepo("haraldmaida", "maven")

libraryDependencies ++= Seq(
  "io.scalatestfx" %% "scalatestfx" % "0.1.0" % Test,
  "org.scalafx" %% "scalafx" % "8.0.102-R11"
)

scalacOptions ++= Seq("-target:jvm-1.8", "-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-feature")
javacOptions ++= Seq("-target", "1.8", "-source", "1.8", "-Xlint:deprecation")
fork := true
parallelExecution in Test := false

enablePlugins(
  GitBranchPrompt,
  GitVersioning,
  JDKPackagerPlugin
)

packageOptions += {
  Package.ManifestAttributes(
    "Created-By" -> "Simple Build Tool",
    "Built-By" -> Option(System.getenv("BUILT_BY")).getOrElse(System.getProperty("user.name")),
    "Build-Jdk" -> System.getProperty("java.version"),
    "Specification-Title" -> name.value,
    "Specification-Version" -> version.value,
    "Specification-Vendor" -> organization.value,
    "Implementation-Title" -> name.value,
    "Implementation-Version" -> version.value,
    "Implementation-Vendor-Id" -> organization.value,
    "Implementation-Vendor" -> organization.value
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
// Native Packaging
//
jdkPackagerType := "all"
lazy val bundleExtension = sys.props("os.name").toLowerCase match {
    case os if os.contains("win") => "exe"
    case os if os.contains("mac") => "dmg"
    case os if os.contains("nix") => "rpm"
    case _ => "unknown"
  }
makeDeploymentSettings(JDKPackager, packageBin in JDKPackager, bundleExtension)
makeDeploymentSettings(Universal, packageBin in Universal, "zip")
lazy val iconExt = sys.props("os.name").toLowerCase match {
    case os if os.contains("mac") => ".icns"
    case os if os.contains("win") => ".ico"
    case _ => "-256.png"
  }

jdkAppIcon := Some(baseDirectory.value / "src" / "main" / "resources" / "images" / ("abacus" + iconExt))
jdkPackagerProperties := Map("app.name" -> name.value, "app.version" -> version.value)
jdkPackagerAppArgs := Seq(pkgMaintainer, pkgSummary, pkgDescription)

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
pomExtra := (
  <developers>
    <developer>
      <id>haraldmaida</id>
      <name>Harald Maida</name>
      <url>https://github.com/haraldmaida</url>
    </developer>
  </developers>
)
