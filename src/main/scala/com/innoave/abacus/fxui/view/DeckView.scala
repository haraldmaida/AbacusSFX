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
import scalafx.scene.layout.HBox
import scalafx.scene.layout.VBox
import scalafx.scene.layout.Pane

trait DeckView[T <: Bead] extends Pane {

  def abacusService: AbacusService[T]

  def rods: Seq[RodView[T]]

  protected def buildRodView(
      beadsRod: BeadRod[T], orientation: Orientation
      )(
      implicit params: Parameter
      ): RodView[T] = {
    val rodView = new RodView(abacusService, beadsRod, orientation)
    if (beadsRod.clearedBeads.size > 2 && beadsRod.position % 3 == 2) {
      rodView.beads.last.styleClass += "group-marker-bead"
      rodView.beads.take(rodView.beads.size - 1).foreach { bv => bv.styleClass += "bead" }
    } else {
      rodView.beads.foreach { bv => bv.styleClass += "bead" }
    }
    rodView
  }

  def setRod(position: Int, value: BeadRod[T]) {
    rods(rods.size - 1 - position).beadRod() = value
  }

  EventBus.of(abacusService).register(classOf[BeadsMoved[T]], { (ev: BeadsMoved[T]) =>
      setRod(ev.newBeadRod.position, ev.newBeadRod)
    })

}

class HDeckView[T <: Bead](
    val abacusService: AbacusService[T],
    val beadsRods: Seq[BeadRod[T]],
    val orientation: Orientation
    )(
    implicit val params: Parameter
    ) extends HBox with DeckView[T] {

  styleClass += "deck-view"

  override val rods: Seq[RodView[T]] = {
    for {
      beadsRod <- beadsRods
    } yield {
      buildRodView(beadsRod, orientation)
    }
  }

  children = rods

}

class VDeckView[T <: Bead](
    val abacusService: AbacusService[T],
    val beadsRods: Seq[BeadRod[T]],
    val orientation: Orientation
    )(
    implicit val params: Parameter
    ) extends VBox with DeckView[T] {

  styleClass += "deck-view"

  override val rods: Seq[RodView[T]] = {
    for {
      beadsRod <- beadsRods
    } yield {
      buildRodView(beadsRod, orientation)
    }
  }

  children = rods

}
