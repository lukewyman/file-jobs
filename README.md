# file-jobs #

file-jobs is a functional scala library for creating file transfer jobs. By providing an easy-to-use DSL, users can mark up the flow of a job without putting too much effort into the plumbing. I'm still wrestling with the basic approach to this thing, but I'll probably give it a more impressive name soon. 

Currently, the library only supports reading and writing files using `InputStreams`, but I'm not opposed to supporting  more intricate batch job writing in the future.

## Motivation ##

This library is inspired by a recent project that I worked on professionally. The need was for a basic file job application to collect third party data that was published through an XML endpoint. The design we came up with was decidedly OOP. With the exception of a handful of Options and calls to map and flatMap, It really looked like an assortment of classic DAOs, with separation of concerns dealt with using OO composition. This project is my attempt to accomplish the same task in a purely functional way. Instead of creating a stand-alone app, I'm designing it as a library, complete with friendly interfaces and a DSL that others can use to quickly create file job projects.

## Architecture and Design ##

### Dependencies ###

* [cats](https://github.com/typelevel/cats) - the `cats-core` and `cats-free` modules for functional abstractions
* [ficus](https://github.com/iheartradio/ficus) - for reading config files

### Currently Supported File Types ###

As of this time, the library supports disk and s3 file types, but other IO scenarios, such as FTP and HTTP could be supported in the future.

### Approach ###

As a functional library, the core problem is how to tackle side effects. Reading, writing and deleting files is by definition side-effectful, so the question becomes how to isolate side-effects. At the same time, I wanted to present users with a simple DSL for defining and submitting file jobs. Thus, the key abstraction for the file-jobs engine is the [Free Monad](https://github.com/typelevel/cats/blob/master/docs/src/main/tut/freemonad.md). Users create file jobs using the DSL, and then submit them with a call to `FoldMap`, using the `Interpreter` or `Compiler`. 

### Future Enhancements ###

Since the result of a Monadic flow is itself a Monad, I'd like to be able to leverage other Monadic combinators for a more powerful interface. For example, since many file jobs are designed to copy batches of files, it would be nice for users to be able to create a job description with the DSL, then create a `Foldable` of such jobs for all the files to be transfered, and then call `sequence`, to make it happen. I'd also like to refine the interface in general to abstract away design choices and result in something that allows the user to focus solely on defining and running jobs. It goes without saying that error handling and logging need to happen, too.

## Examples ##

This is a bare-bones example for creating a simple job that reads a file from a source, writes it to a destination, and then deletes the original from the source. 

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


## Contributing and Participation ##

This project is still in its infancy, and as described, is based on a use-case from a project I worked on previously. While I have a basic idea of what I want this library to be able to do, 

## License ##

file-jobs is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0) (the "License"); you may not use this software except in compliance with the License.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.