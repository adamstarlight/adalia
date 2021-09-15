package org.adalia.math.helper

import math._

case class Tolerance[T](value: T)

object Tolerance {
  implicit val tolerance = Tolerance(0.0000001)

  implicit class DoubleToleranceOp(self: Double) {
    def +-(other: Double)(implicit tolerance: Tolerance[Double]): Boolean =
      abs(self - other) <= tolerance.value

    def +/-(other: Double)(implicit tolerance: Tolerance[Double]): Boolean =
      !(self +- other)
  }
}
