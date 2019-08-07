// The Lagom plugin
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.3.3")

//code quality plugins
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")

addSbtPlugin("de.johoop" % "cpd4sbt" % "1.2.0")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

// Lagom conductr
addSbtPlugin("com.lightbend.conductr" % "sbt-conductr" % "2.3.4")

addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC3")