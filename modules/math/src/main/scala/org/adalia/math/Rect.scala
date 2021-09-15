package org.adalia.math

import math.{min, max, abs, sqrt}

case class Rect(origin: Point2D, size: Point2D) {
  val xmin = origin.x
  val xmax = origin.x + size.x
  val ymin = origin.y
  val ymax = origin.y + size.y

  def contains(point: Point2D): Boolean =
    containsX(point.x) && containsY(point.y)

  def containsX(x: Double): Boolean = x >= xmin && x <= xmax
  def containsY(y: Double): Boolean = y >= ymin && y <= ymax

  def intersect(other: Rect): Option[Rect] = {
    val _xmin = max(this.xmin, other.xmin)
    val _xmax = min(this.xmax, other.xmax)
    val _ymin = max(this.ymin, other.ymax)
    val _ymax = min(this.ymax, other.ymax)

    if (_xmin < _xmax || _ymin < _ymax)
      Some(
        Rect.fromPoints(
          Point2D(_xmin, _ymin),
          Point2D(_xmax, _ymax)
        )
      )
    else
      None
  }
}

object Rect {
  def fromPoints(a: Point2D, b: Point2D): Rect = {
    val origin = Point2D(min(a.x, b.x), min(a.y, b.y))
    val size = Point2D(abs(a.x - b.x), abs(a.y - b.y))

    new Rect(origin, size)
  }
}
