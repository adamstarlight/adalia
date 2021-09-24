package org.adalia.path

import org.adalia.math._

import intersections.LineIntersection
import intersections.CurveIntersection

sealed trait PathSegment {
  def intersect(other: PathSegment): List[Point2D]
}

case class Line(a: Point2D, b: Point2D)
    extends LinearLine(a, b)
    with PathSegment {
  def intersect(other: PathSegment): List[Point2D] = other match {
    case l: Line        => LineIntersection.intersect(this, l).toList
    case l: LineSegment => LineIntersection.intersect(this, l).toList
    case c: CubicCurve  => CurveIntersection.intersect(this, c)
    case q: QuadCurve   => CurveIntersection.intersect(this, q)
  }
}

case class LineSegment(a: Point2D, b: Point2D)
    extends LinearLine(a, b)
    with PathSegment
    with HasBounds {
  def intersect(other: PathSegment): List[Point2D] = other match {
    case l: Line        => LineIntersection.intersect(l, this).toList
    case l: LineSegment => LineIntersection.intersect(this, l).toList
    case c: CubicCurve  => CurveIntersection.intersect(this, c)
    case q: QuadCurve   => CurveIntersection.intersect(this, q)
  }

  val boundary = Rect.fromPoints(a, b)
}

case class CubicCurve(p0: Point2D, p1: Point2D, p2: Point2D)
    extends PathSegment {
  def intersect(other: PathSegment): List[Point2D] = other match {
    case l: Line        => CurveIntersection.intersect(l, this)
    case l: LineSegment => CurveIntersection.intersect(l, this)
    case c: CubicCurve  => CurveIntersection.intersect(this, c)
    case q: QuadCurve   => CurveIntersection.intersect(this, q)
  }
}

case class QuadCurve(p0: Point2D, p1: Point2D, p2: Point2D, p3: Point2D)
    extends PathSegment {
  def intersect(other: PathSegment): List[Point2D] = other match {
    case l: Line        => CurveIntersection.intersect(l, this)
    case l: LineSegment => CurveIntersection.intersect(l, this)
    case c: CubicCurve  => CurveIntersection.intersect(c, this)
    case q: QuadCurve   => CurveIntersection.intersect(this, q)
  }
}
