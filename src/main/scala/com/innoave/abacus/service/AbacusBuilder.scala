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
package com.innoave.abacus.service

import com.innoave.abacus.model.AbacusSystem
import com.innoave.abacus.model.NumeralSystem
import com.innoave.abacus.model.Bead
import com.innoave.abacus.model.BeadRod
import com.innoave.abacus.model.Digit
import com.innoave.abacus.model.DigitBead
import com.innoave.abacus.model.DigitBeadRod

class AbacusBuilder(
    val abacusSystem: AbacusSystem,
    val numeralSystem: NumeralSystem
    ) {

  def buildBeadFor(value: Int): DigitBead =
    DigitBead(numeralSystem.digitFor(value))

  def buildBeadRodFor(position: Int, start: Int, end: Int): DigitBeadRod =
    DigitBeadRod(position,
      for (num <- start to end) yield { buildBeadFor(num) },
      Seq()
    )

  def buildEarthDeck(numberOfRods: Int = abacusSystem.typicalNumberOfRods): Seq[DigitBeadRod] =
    for (
      position <- 0 to (numberOfRods - 1)
    ) yield {
      buildBeadRodFor(position, 1, abacusSystem.numberOfOneValueBeads)
    }

  def buildHeavenDeck(numberOfRods: Int = abacusSystem.typicalNumberOfRods): Option[Seq[DigitBeadRod]] =
    abacusSystem.numberOfFiveValueBeads.flatMap { numberOfBeads => Some(
      for (
        position <- 0 to (numberOfRods - 1)
      ) yield {
        buildBeadRodFor(position, 1, numberOfBeads)
      })
    }

}
