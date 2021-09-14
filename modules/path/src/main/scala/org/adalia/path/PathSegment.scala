package org.adalia.path

import intersections.LineIntersection
import intersections.CurveIntersection

sealed trait PathSegment {
  def intersect(other: PathSegment): List[Point]
}

case class Line(a: Point, b: Point) extends LinearLine(a, b) with PathSegment {
  def intersect(other: PathSegment): List[Point] = other match {
    case l: Line        => LineIntersection.intersect(this, l).toList
    case l: LineSegment => LineIntersection.intersect(this, l).toList
    case c: CubicCurve  => CurveIntersection.intersect(this, c)
    case q: QuadCurve   => CurveIntersection.intersect(this, q)
  }
}

case class LineSegment(a: Point, b: Point)
    extends LinearLine(a, b)
    with PathSegment {
  def intersect(other: PathSegment): List[Point] = other match {
    case l: Line        => LineIntersection.intersect(l, this).toList
    case l: LineSegment => LineIntersection.intersect(this, l).toList
    case c: CubicCurve  => CurveIntersection.intersect(this, c)
    case q: QuadCurve   => CurveIntersection.intersect(this, q)
  }
}

case class CubicCurve(p0: Point, p1: Point, p2: Point) extends PathSegment {
  def intersect(other: PathSegment): List[Point] = other match {
    case l: Line        => CurveIntersection.intersect(l, this)
    case l: LineSegment => CurveIntersection.intersect(l, this)
    case c: CubicCurve  => CurveIntersection.intersect(this, c)
    case q: QuadCurve   => CurveIntersection.intersect(this, q)
  }
}

case class QuadCurve(p0: Point, p1: Point, p2: Point, p3: Point)
    extends PathSegment {
  def intersect(other: PathSegment): List[Point] = other match {
    case l: Line        => CurveIntersection.intersect(l, this)
    case l: LineSegment => CurveIntersection.intersect(l, this)
    case c: CubicCurve  => CurveIntersection.intersect(c, this)
    case q: QuadCurve   => CurveIntersection.intersect(this, q)
  }
}
