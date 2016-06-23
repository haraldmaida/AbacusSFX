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

import com.innoave.abacus.domain.model.Binary;
import com.innoave.abacus.domain.model.Decimal;
import com.innoave.abacus.domain.model.Hexadecimal;
import com.innoave.abacus.domain.model.Octal;

class NumeralSystemSpec extends FlatSpec with Matchers {

  "Binary system" should "allow digits 0 and 1" in {

    Binary.digits shouldBe Seq(Digit('0'), Digit('1'))

  }

  "Octal system" should "allow digits 0 to 7" in {

    Octal.digits shouldBe Seq(
        Digit('0'), Digit('1'), Digit('2'), Digit('3'), Digit('4'),
        Digit('5'), Digit('6'), Digit('7')
        )

  }

  "Decimal system" should "allow digits 0 to 9" in {

    Decimal.digits shouldBe Seq(
        Digit('0'), Digit('1'), Digit('2'), Digit('3'), Digit('4'),
        Digit('5'), Digit('6'), Digit('7'), Digit('8'), Digit('9')
        )

  }

  "Hexadecimal system" should "allow digits 0 to 9 and A to F" in {

    Hexadecimal.digits shouldBe Seq(
        Digit('0'), Digit('1'), Digit('2'), Digit('3'), Digit('4'),
        Digit('5'), Digit('6'), Digit('7'), Digit('8'), Digit('9'),
        Digit('A'), Digit('B'), Digit('C'), Digit('D'), Digit('E'), Digit('F')
        )

  }

}
