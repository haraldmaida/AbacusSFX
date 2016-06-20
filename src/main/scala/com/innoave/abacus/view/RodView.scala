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
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Rectangle
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.Insets

class RodView[T <: Bead](
    val initialBeadRod: BeadRod[T],
    val orientation: Orientation
    )(
    implicit val params: Parameter
    ) extends Pane {

  def rodLength: Int = 2 * initialBeadRod.clearedBeads.size * params.beadDiameter

  val rod = new Rectangle {
    width = params.rodDiameter
    height = rodLength
    x = params.beadRadius - (params.rodRadius / 2) - 2
    y = 1
  }

  children += rod

  val beadRod: ObjectProperty[BeadRod[T]] = ObjectProperty(initialBeadRod)

  val beads: Seq[BeadView] =
    for {
      bead <- initialBeadRod.clearedBeads
    } yield {
      val beadView = new BeadView(bead) {
        val index = initialBeadRod.clearedBeads.indexOf(bead)
        radius = params.beadRadius - 1
        val offset = 1 + params.beadRadius + (index * params.beadDiameter)
        centerX = orientation match {
          case LeftToRight => offset
          case RightToLeft => rodLength - offset
          case _ => params.beadRadius
        }
        centerY = orientation match {
          case TopToBottom => offset
          case BottomToTop => rodLength - offset
          case _ => params.beadRadius
        }
      }
      children += beadView
      beadView
    }

}
