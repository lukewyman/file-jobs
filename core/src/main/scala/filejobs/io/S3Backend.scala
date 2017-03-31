package filejobs.io

import java.io.InputStream
import filejobs.{Context, FileOps}

import scala.collection.JavaConversions._
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{ListObjectsRequest, ObjectMetadata}
import filejobs.S3Context
import FileOps._

/**
  * Created by lukewyman on 9/15/16.
  */
object S3Backend {

  def write(is: InputStream, fn: FileName, c: Context): Option[Checksum] = {
    try {
      val (directory, bucket, region) = c match {
        case S3Context(d, b, r) => (d, b, r)
        case _ => throw new IllegalArgumentException("Invalid file context provided.")
      }
      val key = s"$directory/$fn"
      val s3 = new AmazonS3Client
      s3.setRegion(Region.getRegion(Regions.fromName(region)))
      s3.putObject(bucket, key, is, new ObjectMetadata())
      val metadata = s3.getObjectMetadata(bucket, key)

      Some(metadata.getContentMD5)
    } catch { case e: Exception => None }
  }

  def delete(fn: FileName, c: Context): Option[Unit] = {
    try {
      val (directory, bucket, region) = c match {
        case S3Context(d, b, r) => (d, b, r)
        case _ => throw new IllegalArgumentException("Invalid file context provided.")
      }
      val s3 = new AmazonS3Client
      s3.deleteObject(bucket, s"$directory/$fn")

      Some(())
    } catch { case e: Exception => None }
  }

  def read(fn: FileName, c: Context): Option[InputStream] = {
    try {
      val (directory, bucket, region) = c match {
        case S3Context(d, b, r) => (d, b, r)
        case _ => throw new IllegalArgumentException("Invalid file context provided.")
      }
      val s3 = new AmazonS3Client
      val obj = s3.getObject(bucket, s"$directory/$fn")

      Some(obj.getObjectContent)
    } catch { case e: Exception => None }
  }

  def list(c: Context): Option[Seq[String]] = {
    try {
      val (directory, bucket, regtion) = c match {
        case S3Context(d, b, r) => (d, b, r)
        case _ => throw new IllegalArgumentException("Invalid file context provided.")
      }
      val s3 = new AmazonS3Client
      val ol = s3.listObjects(new ListObjectsRequest()
        .withBucketName(bucket)
        .withPrefix(directory))
      val oll = ol.getObjectSummaries.toList

      Some(oll.map(_.getKey))
    } catch { case e: Exception => None }
  }
}