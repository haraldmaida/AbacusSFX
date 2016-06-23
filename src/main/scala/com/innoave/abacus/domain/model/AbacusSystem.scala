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
package com.innoave.abacus.domain.model

import Position._

trait AbacusSystem {

  def numberOfOneValueBeads: Int

  def numberOfFiveValueBeads: Option[Int]

  def clearedAt: Position

  def typicalNumberOfRods: Int

  def stylesheet: String

}

object AbacusSystem {

  val listAllAvailable: Seq[AbacusSystem] = Seq(
      Schoty,
      Soroban,
      Suanpan
      )

}

/**
 * Schoty - the russian abacus
 */
sealed trait Schoty extends AbacusSystem {

  override val numberOfOneValueBeads = 10

  override val numberOfFiveValueBeads = None

  override val clearedAt = Position.Right

  override val typicalNumberOfRods = 10

  override val stylesheet = "/styles/schoty.css"

}

object Schoty extends Schoty

/**
 * Soroban - the japanese abacus
 */
sealed trait Soroban extends AbacusSystem {

  override val numberOfOneValueBeads = 4

  override val numberOfFiveValueBeads = Some(1)

  override val clearedAt = Position.Outmost

  override val typicalNumberOfRods = 13

  override val stylesheet = "/styles/soroban.css"

}

object Soroban extends Soroban

/**
 * Suanpan - the chinese abacus
 */
sealed trait Suanpan extends AbacusSystem {

  override val numberOfOneValueBeads = 5

  override val numberOfFiveValueBeads = Some(2)

  override val clearedAt = Position.Outmost

  override val typicalNumberOfRods = 13

  override val stylesheet = "/styles/suanban.css"

}

object Suanpan extends Suanpan
