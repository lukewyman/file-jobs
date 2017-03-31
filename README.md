## file-jobs ##

file-jobs is a functional scala library for creating file transfer jobs. The approach is to use the Free Monad to abstact away file access so that file transfer jobs can be written as pure functions. 

Currently, the library only supports reading and writing files using `InputStreams`, but I'm not opposed to supporting  more intricate batch job writing in the future.

### Motivation ###

This library is inspired by a recent project that I worked on professionally. The need was for a basic file job application to collect third party data that was published through an XML endpoint. The design we came up with was decidedly OOP. With the exception of a handful of Options and calls to map and flatMap, It really looked like an assortment of classic DAOs, with separation of concerns dealt with using OO composition. This project is my attempt to accomplish the same task in a purely functional way. Instead of creating a stand-alone app, I'm designing it as a library. I'd like to figure out how to implement a DSL to make writing file jobs even easier.

### Approach ###

As a functional library, the core problem is how to tackle side effects. Reading, writing and deleting files is by definition side-effectful, so the question becomes how to isolate side-effects. At the same time, I wanted to present users with a simple interface for defining and submitting file jobs. Thus, the key abstraction for the file-jobs engine is the [Free Monad](http://typelevel.org/cats/datatypes/freemonad.html). Users create file jobs using the interface, and then submit them with a call to `FoldMap`, using the `Interpreter` or `Compiler`. 

### Future Enhancements ###

Since the result of a Monadic flow is itself a Monad, I'd like to be able to leverage other Monadic combinators for a more powerful interface. For example, since many file jobs are designed to copy batches of files, it would be nice for users to be able to create a job description with the DSL, then create a `Foldable` of such jobs for all the files to be transfered, and then call `sequence`, to make it happen. I'd also like to refine the interface in general to abstract away design choices and result in something that allows the user to focus solely on defining and running jobs. It goes without saying that error handling and logging need to happen, too.

### Examples ###

This is a bare-bones example for creating a simple job that reads a file from a source, writes it to a destination, and then deletes the original from the source. 

```scala
import filejobs.Compiler._
import filejobs.FileOps._
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
