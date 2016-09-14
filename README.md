# file-jobs #

file-jobs is a functional scala library for creating file transfer jobs. By providing an easy-to-use DSL, users can mark up the flow of a job without putting too much effort into the plumbing. I'm still wrestling with the basic approach to this thing, but I'll probably give it a more impressive name soon.

## Motivation ##

This library is inspired by a recent project that I worked on professionally. The need was for a basic file job application to collect third party data that was published through an XML endpoint. The design we came up with was decidedly OOP. With the exception of a handful of Options and calls to map and flatMap, It really looked like an assortment of classic DAOs, with separation of concerns dealt with using OO composition. This project is my attempt to accomplish the same task in a purely functional way. Instead of creating a stand-alone app, I'm designing it as a library, complete with friendly interfaces and a DSL that others can use to quickly create file job projects.

## Architecture and Design ##

### Dependencies ###

* [cats](https://github.com/typelevel/cats) - for functional structures
* [ficus](https://github.com/iheartradio/ficus) - for reading config files

### Approach ###

As a functional library, the core problem is how to tackle side effects. Reading, writing and deleting files is by definition side-effectful, so the question becomes how to isolate side-effects 

## Examples ##


```scala
import filejobs.dsl.Compiler._
import filejobs.dsl.FileOps._
import filejobs.io.StubBackend

object BasicReadWriteDelete extends App {  

  job.foldMap(syncCompiler)

  def job =
    for {
      is <- read("myfile1.txt", "myfiles")(StubBackend.read)
      _  <- write(is, "yourfile1.txt", "yourfiles")(StubBackend.write)
      _  <- delete("myfile1.txt", "myfiles")(StubBackend.delete)
    } yield ()

}
```

## License ##

file-jobs is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0) (the "License"); you may not use this software except in compliance with the License.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.