package org.adalia.path.intersections

import scala.math.abs

import org.adalia.math._
import org.adalia.path._
import org.adalia.math.helper.Tolerance

object LineIntersection {
  import Tolerance._

  def intersect(l1: Line, l2: Line): Option[Point2D] = {
    if (l1.m +- l2.m) None else Some(Point2D(0, 0))
  }

  def intersect(s1: LineSegment, s2: LineSegment): Option[Point2D] = ???
  def intersect(l: Line, s: LineSegment): Option[Point2D] = ???
}
