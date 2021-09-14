package org.adalia.path

abstract class LinearLine(a: Point, b: Point) {
  val m = (a.y - b.y) / (a.x - b.x)
  val p = b.y - m * a.x
}
