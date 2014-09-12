name := "SparkTwitterDemo"

version := "1.0"

scalaVersion := "2.10.4"

organization := "datastax.com"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "0.9.1" % "provided"

libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "0.9.1" % "provided"

libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "1.0.0-rc4" withSources() withJavadoc()

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "1.4.0"

libraryDependencies ++= Seq(
  ("org.apache.spark" % "spark-streaming_2.10" % "0.9.1").
    exclude("org.mortbay.jetty", "servlet-api").
    exclude("commons-beanutils", "commons-beanutils-core").
    exclude("commons-collections", "commons-collections").
    exclude("commons-collections", "commons-collections").
    exclude("com.esotericsoftware.minlog", "minlog")
)

run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))

assemblySettings