package org.adalia.path.intersections

import scala.math.abs

import org.adalia.math._
import org.adalia.path._
import org.adalia.math.helper.Tolerance

object LineIntersection {
  import Tolerance._

  def intersect(l1: Line, l2: Line): Option[Point2D] = {
    if (l1.m +- l2.m) None
    else {
      val ox = (l2.p - l1.p) / (l1.m - l2.m)
      val oy = l1.m * ox + l1.p

      Some(Point2D(ox, oy))
    }
  }

  def intersect(s1: LineSegment, s2: LineSegment): Option[Point2D] = ???
  def intersect(l: Line, s: LineSegment): Option[Point2D] = ???
}
