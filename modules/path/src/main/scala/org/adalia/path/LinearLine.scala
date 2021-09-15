package org.adalia.path

import org.adalia.math._

abstract class LinearLine(a: Point2D, b: Point2D) {
  val m = (a.y - b.y) / (a.x - b.x)
  val p = b.y - m * a.x

  override def toString(): String = s"Line(a: $a, b: $b, m: $m, p: $p)"
}
