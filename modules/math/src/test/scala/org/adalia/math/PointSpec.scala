package org.adalia.math

import org.scalacheck._
import org.scalacheck.Test.Parameters

import org.adalia.math.helper.Tolerance

object Point2DSpec extends Properties("Point2D") {
  import Tolerance.DoubleToleranceOp
  import Prop._

  override def overrideParameters(p: Parameters) =
    p.withMinSuccessfulTests(10000)

  case class CircleCoord(angle: Double, radius: Double) {
    def toCartesian(origin: Point2D) = Point2D(
      radius * math.sin(angle) + origin.x,
      radius * math.cos(angle) + origin.y
    )
  }

  implicit lazy val arbPoint2D: Arbitrary[Point2D] = Arbitrary {
    for {
      x <- Gen.choose(-10.0, 10.0)
      y <- Gen.choose(-10.0, 10.0)
    } yield Point2D(x, y)
  }

  def circlePoint2D(startAngle: Float, stopAngle: Float): Gen[CircleCoord] =
    for {
      angle <- Gen.choose(startAngle, stopAngle)
      radius <- Gen.choose(0.0, 10.0)
    } yield CircleCoord(angle, radius)

  def clockwisePoint2Ds: Gen[(Point2D, Point2D, Point2D)] = {
    implicit val angleTolerance = Tolerance(0.01)
    for {
      origin <- Arbitrary.arbitrary[Point2D]
      a0 <- Gen.choose(0.0, 2.0 * math.Pi)
      a1 <- Gen.choose(a0, 2.0 * math.Pi) suchThat (_ +/- a0)
      a2 <- Gen.choose(a1, 2.0 * math.Pi) suchThat (_ +/- a1)
    } yield (
      CircleCoord(a0, 1.0).toCartesian(origin),
      CircleCoord(a1, 1.0).toCartesian(origin),
      CircleCoord(a2, 1.0).toCartesian(origin)
    )
  }

  def counterClockwisePoint2Ds: Gen[(Point2D, Point2D, Point2D)] = for {
    (a, b, c) <- clockwisePoint2Ds
  } yield (c, b, a)

  def collinearPoint2Ds: Gen[(Point2D, Point2D, Point2D)] = for {
    origin <- Arbitrary.arbitrary[Point2D]
    m <- Gen.choose(1.0, 10.0)
    p <- Gen.choose(1.0, 10.0)
    step <- Gen.choose(1.0, 10.0)
  } yield (
    Point2D(origin.x, m * origin.x + p),
    Point2D(origin.x + step, m * (origin.x + step) + p),
    Point2D(origin.x + step * 2, m * (origin.x + step * 2) + p)
  )

  property("orientation clockwise points") = forAll(clockwisePoint2Ds) {
    case (p0: Point2D, p1: Point2D, p2: Point2D) =>
      Point2D.orientation(p0, p1, p2) == PointOrientation.Clockwise
  }

  property("orientation counterclockwise points") =
    forAll(counterClockwisePoint2Ds) {
      case (p0: Point2D, p1: Point2D, p2: Point2D) =>
        Point2D.orientation(p0, p1, p2) == PointOrientation.CounterClockwise
    }

  property("orientation collinear points") = forAll(collinearPoint2Ds) {
    case (p0: Point2D, p1: Point2D, p2: Point2D) =>
      Point2D.orientation(p0, p1, p2) == PointOrientation.Collinear
  }
}
