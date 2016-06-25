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
import scalafx.scene.layout.GridPane
import scalafx.scene.Node

class DeckView[T <: Bead](
    val abacusService: AbacusService[T],
    val beadRods: Seq[BeadRod[T]],
    val orientation: Orientation
    )(
    implicit val params: Parameter
    ) extends GridPane {

  styleClass += "deck-view"

  val rodViews: Seq[RodView[T]] = {
    for {
      beadRod <- beadRods
    } yield {
      val rodView = new RodView(abacusService, beadRod, orientation)
      if (beadRod.clearedBeads.size > 2 && beadRod.position % 3 == 2) {
        rodView.beads.last.styleClass += "group-marker-bead"
        rodView.beads.take(rodView.beads.size - 1).foreach { bv => bv.styleClass += "bead" }
      } else {
        rodView.beads.foreach { bv => bv.styleClass += "bead" }
      }
      rodView
    }
  }

  def setBeadRod(value: BeadRod[T]) {
    rodViewFor(value.position, value.beadValue).foreach { rodView =>
        rodView.beadRod() = value
      }
  }

  def beadRodFor(position: Int, beadValue: Int): Option[BeadRod[T]] =
    rodViewFor(position, beadValue).map { x => x.beadRod() }

  def rodViewFor(position: Int, beadValue: Int): Option[RodView[T]] =
    rodViews.find { x =>
      x.beadRod().position == position &&
      x.beadRod().beadValue == beadValue
    }

  orientation match {
    case TopToBottom =>
      addRow(0, rodViews.map { v => Node.sfxNode2jfx(v) }: _*)
    case BottomToTop =>
      addRow(0, rodViews.map { v => Node.sfxNode2jfx(v) }: _*)
    case LeftToRight =>
      addColumn(0, rodViews.map { v => Node.sfxNode2jfx(v) }: _*)
    case RightToLeft =>
      addColumn(0, rodViews.map { v => Node.sfxNode2jfx(v) }: _*)
  }

  EventBus.of(abacusService).register(classOf[BeadsMoved[T]], { (ev: BeadsMoved[T]) =>
      setBeadRod(ev.newBeadRod)
    })

}
