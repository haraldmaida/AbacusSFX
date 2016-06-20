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
package com.innoave.abacus.model

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class BeadRodSpec extends FlatSpec with Matchers {

  "BeadRod with all beads on cleared position" should
      "move all beads to the counted position, when first bead is touched" in {

    val beadRod = DigitBeadRod(
        0,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq(
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('0')))

    movedBeads shouldBe DigitBeadRod(
        0,
        Seq(
        ),
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )
  }

  it should
      "move one bead to the counted position, when last bead is touched" in {

    val beadRod = DigitBeadRod(
        1,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq(
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('1')))

    movedBeads shouldBe DigitBeadRod(
        1,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2'))
        ),
        Seq(
            DigitBead(Digit('1'))
        )
      )
  }

  it should
      "move two beads to counted position, when second last bead is touched" in {

    val beadRod = DigitBeadRod(
        999999,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq(
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('2')))

    movedBeads shouldBe DigitBeadRod(
        999999,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3'))
        ),
        Seq(
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )
  }

  it should
      "move all but one bead to the counted position, when second bead is touched" in {

    val beadRod = DigitBeadRod(
        3,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq(
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('9')))

    movedBeads shouldBe DigitBeadRod(
        3,
        Seq(
            DigitBead(Digit('0'))
        ),
        Seq(
            DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )
  }

  "BeadRod with all beads on counted position" should
      "move all beads to the cleared position, when last bead is touched" in {

    val beadRod = DigitBeadRod(
        4,
        Seq(
        ),
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('1')))

    movedBeads shouldBe DigitBeadRod(
        4,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq(
        )
      )
  }

  it should
      "move one bead to the cleared position, when first bead is touched" in {

    val beadRod = DigitBeadRod(
        5,
        Seq(
        ),
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('0')))

    movedBeads shouldBe DigitBeadRod(
        5,
        Seq(
            DigitBead(Digit('0'))
        ),
        Seq(
            DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )
  }

  it should
      "move two beads to the cleared position, when second bead is touched" in {

    val beadRod = DigitBeadRod(
        6,
        Seq(
        ),
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('9')))

    movedBeads shouldBe DigitBeadRod(
        6,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9'))
        ),
        Seq(
            DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )
  }

  it should
      "move all but one bead to the cleared position, when second last bead is touched" in {

    val beadRod = DigitBeadRod(
        7,
        Seq(
        ),
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('2')))

    movedBeads shouldBe DigitBeadRod(
        7,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2'))
        ),
        Seq(
            DigitBead(Digit('1'))
        )
      )
  }

  "BeadRod with one bead on the counted position" should
      "move all cleared beads to the counted position, when first bead is touched" in {

    val beadRod = DigitBeadRod(
        8,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2'))
            ),
        Seq(
            DigitBead(Digit('1'))
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('0')))

    movedBeads shouldBe DigitBeadRod(
        8,
        Seq(
        ),
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )
  }

  it should
      "move one bead from cleared to counted position, when last cleared bead is touched" in {

    val beadRod = DigitBeadRod(
        9,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2'))
            ),
        Seq(
            DigitBead(Digit('1'))
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('2')))

    movedBeads shouldBe DigitBeadRod(
        9,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3'))
        ),
        Seq(
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )
  }

  it should
      "move that one bead to the cleared position, when it is touched" in {

    val beadRod = DigitBeadRod(
        9,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2'))
            ),
        Seq(
            DigitBead(Digit('1'))
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('1')))

    movedBeads shouldBe DigitBeadRod(
        9,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq(
        )
      )
  }

  "BeadRod with one bead on the cleared position" should
      "move all counted beads to the cleared position, when last bead is touched" in {

    val beadRod = DigitBeadRod(
        10,
        Seq(
            DigitBead(Digit('0'))
        ),
        Seq(
            DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('1')))

    movedBeads shouldBe DigitBeadRod(
        10,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq(
        )
      )
  }

  it should
      "move one bead from counted to the cleared position, when first counted bead is touched" in {

    val beadRod = DigitBeadRod(
        11,
        Seq(
            DigitBead(Digit('0'))
        ),
        Seq(
            DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )

    val movedBeads = beadRod.moveBeads(DigitBead(Digit('9')))

    movedBeads shouldBe DigitBeadRod(
        11,
        Seq(
            DigitBead(Digit('0')), DigitBead(Digit('9'))
        ),
        Seq(
            DigitBead(Digit('8')), DigitBead(Digit('7')),
            DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
            DigitBead(Digit('2')), DigitBead(Digit('1'))
        )
      )
  }

}
