package filejobs.io

import java.io.{File, ByteArrayInputStream}
import java.nio.charset.StandardCharsets

import filejobs.dsl.{FileContext, Context}
import filejobs.dsl.FileOps.FileName
import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by lukewyman on 9/16/16.
  */
class FileBackendSpec extends FlatSpec with Matchers {

  "A FileBackend " should " write a file to disc" in {
    // Prepare InputStream and test FileBackend.write
    val is = new ByteArrayInputStream("Some sample file content".getBytes(StandardCharsets.UTF_8))
    val result = FileBackend.write(is, "test.txt", FileContext("/tmp"))
    println(s"checksum: $result")

    val f = new File("/tmp/test.txt")
    f.exists() should be (true)
  }

  it should "read a file from disc" in {

  }

  def createTestFile(fn: FileName, dir: String) = {
    
  }
}
