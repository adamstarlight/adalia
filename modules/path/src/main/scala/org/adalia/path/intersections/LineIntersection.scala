package org.adalia.path.intersections

import org.adalia.path._

object LineIntersection {
  def intersect(l1: Line, l2: Line): Option[Point] = ???
  def intersect(s1: LineSegment, s2: LineSegment): Option[Point] = ???
  def intersect(l: Line, s: LineSegment): Option[Point] = ???
}
