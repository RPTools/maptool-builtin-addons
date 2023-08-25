
plugins {
    `java-library` 
}

repositories {
    mavenCentral() 
}

/*
 * Task to list all libraries
 */
tasks.register("listLibraries") {
  var libraries = fileTree("libraries") {
    include("**/library.json")
  }

  var libDirs = libraries.files.map{file: File -> file.parentFile}

  libDirs.forEach{dir: File -> println(dir)};
}


/* 
 * Task to zip all libraries
 */
tasks.register("zipAll") {
  libDirs.forEach{dir: File -> 
    var lib = dir.name
    dependsOn("zip$lib")
  }
}

tasks.jar {
  dependsOn("zipAll")
  from(layout.buildDirectory.dir("mtlib"))
}

/*
 * Create the tasks to zip each library
 */
var libraries = fileTree("libraries") {
  include("**/library.json")
}


var libDirs = libraries.files.map{file: File -> file.parentFile}

libDirs.forEach{dir: File -> 
  var lib = dir.name
  tasks.register<Zip>("zip$lib") {
    from(dir)
    include("**/*")
    destinationDirectory.set(layout.buildDirectory.dir("mtlib/net/rptools/maptool/libraries/builtin"))
    archiveFileName.set("${lib}.mtlib")
  }
}