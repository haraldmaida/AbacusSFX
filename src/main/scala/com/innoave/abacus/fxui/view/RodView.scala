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
package com.innoave.abacus.fxui.view

import Orientation._
import com.innoave.abacus.domain.model.Bead
import com.innoave.abacus.domain.model.BeadRod
import com.innoave.abacus.domain.model.Parameter
import com.innoave.abacus.domain.service.AbacusService
import com.innoave.abacus.domain.service.EventBus
import com.innoave.abacus.domain.service.event.BeadsMoved
import scalafx.Includes._
import scalafx.animation.TranslateTransition
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.Insets
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Rectangle

class RodView[T <: Bead](
    val abacusService: AbacusService[T],
    val initialBeadRod: BeadRod[T],
    val orientation: Orientation
    )(
    implicit val params: Parameter
    ) extends Pane {

  styleClass += "rod-view"

  private val beadRadiusX: Int = (
      orientation match {
        case TopToBottom => params.beadWidth
        case BottomToTop => params.beadWidth
        case LeftToRight => params.beadHeight
        case RightToLeft => params.beadHeight
      }
    ) / 2
  private val beadRadiusY: Int = (
      orientation match {
        case TopToBottom => params.beadHeight
        case BottomToTop => params.beadHeight
        case LeftToRight => params.beadWidth
        case RightToLeft => params.beadWidth
      }
    ) / 2

  private val moveDistance: Int = initialBeadRod.clearedBeads.size * params.beadHeight + params.rodLengthAugment

  private val moveX: Int = orientation match {
        case LeftToRight => moveDistance
        case RightToLeft => - moveDistance
        case _ => 0
      }
  private val moveY: Int = orientation match {
        case TopToBottom => moveDistance
        case BottomToTop => - moveDistance
        case _ => 0
      }

  private val offset0: Int = 1 + params.beadHeight / 2
  private val rodLength: Int = 1 + 2 * initialBeadRod.clearedBeads.size * params.beadHeight + params.rodLengthAugment

  private val rodGraphic: Rectangle =
    orientation match {
      case TopToBottom =>
        verticalRodGraphic
      case BottomToTop =>
        verticalRodGraphic
      case LeftToRight =>
        horizontalRodGraphic
      case RightToLeft =>
        horizontalRodGraphic
    }

  private def horizontalRodGraphic = new Rectangle {
      styleClass += "rod"
      width = rodLength
      height = params.rodDiameter
      x = 0
      y = params.beadWidth / 2 - params.rodDiameter / 2
    }

  private def verticalRodGraphic = new Rectangle {
      styleClass += "rod"
      width = params.rodDiameter
      height = rodLength
      x = params.beadWidth / 2 - params.rodDiameter / 2
      y = 0
    }

  children += rodGraphic

  val beadRod: ObjectProperty[BeadRod[T]] = ObjectProperty(initialBeadRod)
  beadRod.onChange { (_, oldBeadRod, newBeadRod) =>
      newBeadRod.clearedBeads.reverse.foreach { bead =>
        beadViewFor(bead).foreach { bv =>
          if (bv.translateX != 0 || bv.translateY != 0) {
            translateToCleared(bv)
          }
        }
      }
      newBeadRod.countedBeads.foreach { bead =>
        beadViewFor(bead).foreach { bv =>
          if (bv.translateX != moveX || bv.translateY != moveY) {
            translateToCounted(bv)
          }
        }
      }
    }

  val beads: Seq[BeadView[T]] =
    for {
      bead <- initialBeadRod.clearedBeads
    } yield {
      val beadView = new BeadView(bead) {
        val index = initialBeadRod.clearedBeads.indexOf(bead)
        radiusX = beadRadiusX
        radiusY = beadRadiusY
        val offsetN = offset0 + (index * params.beadHeight)
        centerX = orientation match {
          case LeftToRight => offsetN
          case RightToLeft => rodLength - offsetN
          case _ => params.beadWidth / 2
        }
        centerY = orientation match {
          case TopToBottom => offsetN
          case BottomToTop => rodLength - offsetN
          case _ => params.beadWidth / 2
        }
        onMouseClicked = { ev: MouseEvent =>
          abacusService.moveBeads(bead, beadRod())
        }
      }
      children += beadView
      beadView
    }
//
//  EventBus.of(abacusService).register(classOf[BeadsMoved[T]], { (ev: BeadsMoved[T]) =>
//      if (ev.newBeadRod.position == beadRod().position &&
//          ev.newBeadRod.beadValue == beadRod().beadValue
//          ) {
//        beadRod() = ev.newBeadRod
//      }
//    })

  def beadViewFor(bead: Bead): Option[BeadView[T]] =
    beads.find { x => x.bead == bead }

  private def translateToCleared(beadToMove: BeadView[T]) {
      new TranslateTransition {
          node = beadToMove
          toX = 0
          toY = 0
          duration = params.beadMovingDuration
        }.playFromStart
    }

  private def translateToCounted(beadToMove: BeadView[T]) {
      new TranslateTransition {
          node = beadToMove
          toX = moveX
          toY = moveY
          duration = params.beadMovingDuration
        }.playFromStart
    }

}
