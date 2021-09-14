package org.adalia.path.intersections

import org.adalia.path._

object CurveIntersection {
  def intersect(l: Line, c: CubicCurve): List[Point] = ???
  def intersect(l: Line, s: QuadCurve): List[Point] = ???
  def intersect(l: LineSegment, c: CubicCurve): List[Point] = ???
  def intersect(l: LineSegment, s: QuadCurve): List[Point] = ???

  def intersect(c1: CubicCurve, c2: CubicCurve): List[Point] = ???
  def intersect(q1: QuadCurve, q2: QuadCurve): List[Point] = ???
  def intersect(c: CubicCurve, q: QuadCurve): List[Point] = ???
}
