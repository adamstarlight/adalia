package org.adalia.path

import org.scalacheck._
import org.scalacheck.Test.Parameters

import org.adalia.math._
import org.adalia.math.helper.Tolerance

object LineSpec extends Properties("Line") {
  import Tolerance._
  import Prop._

  override def overrideParameters(p: Parameters) =
    p.withMinSuccessfulTests(10000)

  implicit lazy val arbPoint: Arbitrary[Point2D] = Arbitrary {
    for {
      x <- Gen.choose(-10.0, 10.0)
      y <- Gen.choose(-10.0, 10.0)
    } yield Point2D(x, y)
  }

  implicit lazy val arbLine: Arbitrary[Line] = Arbitrary {
    val minPointOffset = 0.001

    for {
      a <- Arbitrary.arbitrary[Point2D]
      b <- Arbitrary.arbitrary[Point2D] suchThat { p =>
        math.abs(a.x - p.x) > minPointOffset &&
        math.abs(a.y - p.y) > minPointOffset
      }
    } yield Line(a, b)
  }

  lazy val parallelLines: Gen[(Line, Line)] = for {
    l1 <- Arbitrary.arbitrary[Line]
    offset <- Arbitrary.arbitrary[Point2D]
  } yield (l1, Line(l1.a.move(offset), l1.b.move(offset)))

  property("two non-parallel lines always intersect") = forAll {
    (l1: Line, l2: Line) =>
      (l1.m +/- l2.m) ==> !l1.intersect(l2).isEmpty
  }

  property("parallel lines never intersect") = forAll(parallelLines) {
    case (p0: Line, p1: Line) => p0.intersect(p1).isEmpty
  }

  property("line intersection result is metamorphic") = forAll {
    (l1: Line, l2: Line) =>
      l1.intersect(l2).head.distance(l2.intersect(l1).head) <= tolerance.value
  }

  property("lines have no more that 1 point of intersection") = forAll {
    (l1: Line, l2: Line) => l1.intersect(l2).length <= 1
  }
}
