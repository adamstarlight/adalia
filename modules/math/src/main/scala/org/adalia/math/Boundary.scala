package org.adalia.math

trait Boundary {
  def contains(p: Point2D): Boolean
  def containsX(x: Double): Boolean
  def containsY(y: Double): Boolean
}
