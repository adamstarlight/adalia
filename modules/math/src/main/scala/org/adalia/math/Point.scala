package org.adalia.math

import org.adalia.math.helper.Tolerance

case class Point2D(x: Double, y: Double) {
  def distance(other: Point2D): Double = {
    val dx = other.x - x
    val dy = other.y - y

    math.sqrt(dx * dx + dy * dy)
  }

  def move(offset: Point2D): Point2D =
    Point2D(x + offset.x, y + offset.y)
}

object PointOrientation extends Enumeration {
  type PointOrientation = Value
  val Clockwise, CounterClockwise, Collinear = Value
}

object Point2D {
  import Tolerance._
  import PointOrientation._

  def orientation(p: Point2D, q: Point2D, r: Point2D): PointOrientation = {
    (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y) match {
      case v if v +- 0 => Collinear
      case v if v > 0  => Clockwise
      case _           => CounterClockwise
    }
  }
}
