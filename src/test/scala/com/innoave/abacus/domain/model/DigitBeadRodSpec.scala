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

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class DigitBeadRodSpec extends FlatSpec with Matchers {

  "A new DigitBeadRod for the decimal system" should
    "contain all possible beads on the cleared position in specific order" in {

    DigitBeadRod(1, 1, Decimal) shouldBe DigitBeadRod(
        1, 1,
        Seq(
          DigitBead(Digit('0')), DigitBead(Digit('9')), DigitBead(Digit('8')), DigitBead(Digit('7')),
          DigitBead(Digit('6')), DigitBead(Digit('5')), DigitBead(Digit('4')), DigitBead(Digit('3')),
          DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq()
        )

  }

  "DigitBeadRod for all digits of the binary system" should
    "contain all possible beads on the cleared position in specific order" in {

    DigitBeadRod(2, 1, Binary) shouldBe DigitBeadRod(
        2, 1,
        Seq(
          DigitBead(Digit('0')), DigitBead(Digit('1'))
        ),
        Seq()
        )

  }

  "DigitBeadRod for all digits of the octal system" should
    "contain all possible beads on the cleared position in specific order" in {

    DigitBeadRod(999, 1, Octal) shouldBe DigitBeadRod(
        999, 1,
        Seq(
          DigitBead(Digit('0')), DigitBead(Digit('7')), DigitBead(Digit('6')), DigitBead(Digit('5')),
          DigitBead(Digit('4')), DigitBead(Digit('3')), DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq()
        )
  }

  "DigitBeadRod for all digits of the hexadecimal system" should
    "contain all possible beads on the cleared position in specific order" in {

    DigitBeadRod(0, 1, Hexadecimal) shouldBe DigitBeadRod(
        0, 1,
        Seq(
          DigitBead(Digit('0')), DigitBead(Digit('F')), DigitBead(Digit('E')), DigitBead(Digit('D')),
          DigitBead(Digit('C')), DigitBead(Digit('B')), DigitBead(Digit('A')), DigitBead(Digit('9')),
          DigitBead(Digit('8')), DigitBead(Digit('7')), DigitBead(Digit('6')), DigitBead(Digit('5')),
          DigitBead(Digit('4')), DigitBead(Digit('3')), DigitBead(Digit('2')), DigitBead(Digit('1'))
        ),
        Seq()
        )

  }

}
