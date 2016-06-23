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
package com.innoave.abacus.domain.service

import com.innoave.abacus.domain.model.AbacusSystem
import com.innoave.abacus.domain.model.Bead
import com.innoave.abacus.domain.model.BeadRod
import com.innoave.abacus.domain.model.Digit
import com.innoave.abacus.domain.model.NumeralSystem
import com.innoave.abacus.domain.service.event.BeadsMoved
import com.innoave.abacus.domain.service.event.DigitChanged

trait AbacusService[T <: Bead] {

  def abacusSystem: AbacusSystem
  def numeralSystem: NumeralSystem

  def moveBeads(touchedBead: T, beadsPosition: BeadRod[T]): Unit

  def digitFor(beadRod: BeadRod[T], upperBeadRod: Option[BeadRod[T]]): Digit

}

object AbacusService {

  def of[T <: Bead](abacusSystem: AbacusSystem, numeralSystem: NumeralSystem): AbacusService[T] =
    new AbacusServiceImpl(abacusSystem, numeralSystem)

}

private class AbacusServiceImpl[T <: Bead](
    override val abacusSystem: AbacusSystem,
    override val numeralSystem: NumeralSystem
    ) extends AbacusService[T] {

  override def moveBeads(touchedBead: T, beadRod: BeadRod[T]) {
    val movedBeadRod = beadRod.moveBeads(touchedBead)
    EventBus.of(this).send(BeadsMoved(beadRod, movedBeadRod))
  }

  override def digitFor(beadRod: BeadRod[T], upperBeadRod: Option[BeadRod[T]]): Digit =
    numeralSystem.digitFor(
      beadRod.countedBeads.size * beadRod.beadValue + (
        if (upperBeadRod.isDefined)
          upperBeadRod.get.countedBeads.size * upperBeadRod.get.beadValue
        else
          0
      )
    )

}

