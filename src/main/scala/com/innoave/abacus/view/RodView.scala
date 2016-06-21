/*
 * Copyright (c) 2016, Innoave.com
 * All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL INNOAVE.COM OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.innoave.abacus.view

import Orientation._
import com.innoave.abacus.model.Bead
import com.innoave.abacus.model.BeadRod
import com.innoave.abacus.model.Digit
import com.innoave.abacus.model.Numeral
import com.innoave.abacus.model.Parameter
import scalafx.Includes._
import scalafx.animation.TranslateTransition
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.Insets
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Rectangle

class RodView[T <: Bead](
    val initialBeadRod: BeadRod[T],
    val orientation: Orientation
    )(
    implicit val params: Parameter
    ) extends Pane {

  styleClass += "rod-view"

  val beadWidth = orientation match {
      case TopToBottom => params.beadWidth
      case BottomToTop => params.beadWidth
      case LeftToRight => params.beadHeight
      case RightToLeft => params.beadHeight
    }
  val beadHeight = orientation match {
      case TopToBottom => params.beadHeight
      case BottomToTop => params.beadHeight
      case LeftToRight => params.beadWidth
      case RightToLeft => params.beadWidth
    }

  val moveDistance: Int = initialBeadRod.clearedBeads.size * beadHeight + params.rodLengthAugment

  val moveX: Int = orientation match {
        case LeftToRight => moveDistance
        case RightToLeft => - moveDistance
        case _ => 0
      }
  val moveY: Int = orientation match {
        case TopToBottom => moveDistance
        case BottomToTop => - moveDistance
        case _ => 0
      }

  val offset0: Int = 1 + beadHeight / 2
  val rodLength: Int = 1 + 2 * initialBeadRod.clearedBeads.size * beadHeight + params.rodLengthAugment

  val rod = new Rectangle {
    styleClass += "rod"
    orientation match {
      case TopToBottom =>
        width = params.rodDiameter
        height = rodLength
        x = beadWidth / 2 - params.rodDiameter / 2
        y = 0
      case BottomToTop => params.beadHeight
        width = params.rodDiameter
        height = rodLength
        x = beadWidth / 2 - params.rodDiameter / 2
        y = 0
      case LeftToRight => params.beadWidth
        width = rodLength
        height = params.rodDiameter
        x = 0
        y = beadWidth / 2 - params.rodDiameter / 2
      case RightToLeft => params.beadWidth
        width = rodLength
        height = params.rodDiameter
        x = 0
        y = beadWidth / 2 - params.rodDiameter / 2
    }
  }

  children += rod

  val beadRod: ObjectProperty[BeadRod[T]] = ObjectProperty(initialBeadRod)
  beadRod.onChange { (_, oldBeadRod, newBeadRod) =>
      if (newBeadRod.position == oldBeadRod.position) {
        newBeadRod.clearedBeads.reverse.foreach { bead =>
          beadViewFor(bead).foreach { bv =>
            if (bv.translateX != 0 || bv.translateY != 0) {
              moveToCleared(bv)
            }
          }
        }
        newBeadRod.countedBeads.foreach { bead =>
          beadViewFor(bead).foreach { bv =>
            if (bv.translateX != moveX || bv.translateY != moveY) {
              moveToCounted(bv)
            }
          }
        }
      }
    }

  val beads: Seq[BeadView] =
    for {
      bead <- initialBeadRod.clearedBeads
    } yield {
      val beadView = new BeadView(bead) {
        val index = initialBeadRod.clearedBeads.indexOf(bead)
        radiusX = beadWidth / 2 - 1
        radiusY = beadHeight / 2 - 1
        val offsetN = offset0 + (index * beadHeight)
        centerX = orientation match {
          case LeftToRight => offsetN
          case RightToLeft => rodLength - offsetN
          case _ => beadWidth / 2
        }
        centerY = orientation match {
          case TopToBottom => offsetN
          case BottomToTop => rodLength - offsetN
          case _ => beadHeight / 2
        }
        onMouseClicked = { ev: MouseEvent =>
          beadRod() = beadRod().moveBeads(bead)
        }
      }
      children += beadView
      beadView
    }

  def beadViewFor(bead: Bead): Option[BeadView] =
    beads.find { x => x.bead == bead }

  private def moveToCleared(beadToMove: BeadView) {
      new TranslateTransition {
          node = beadToMove
          toX = 0
          toY = 0
          duration = params.beadMovingDuration
        }.playFromStart
    }

  private def moveToCounted(beadToMove: BeadView) {
      new TranslateTransition {
          node = beadToMove
          toX = moveX
          toY = moveY
          duration = params.beadMovingDuration
        }.playFromStart
    }

}
