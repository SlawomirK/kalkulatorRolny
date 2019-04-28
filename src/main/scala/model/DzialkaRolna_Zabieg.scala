package model

import java.time.LocalDate

import scala.collection.mutable

/** Asocjacja z atrybutem. Posredniczaca */

class DzialkaRolna_Zabieg(//asocjaja z Atrybutem
                          dataZabiegu: LocalDate,
                          dzialkaRolna: DzialkaRolna,
                          zabiegPielegnacyjny: ZabiegPielegnacyjny
                         ) extends ObjectPlusPlus {

  def getDataZabiegu = dataZabiegu


  override def toString: String = {
    "Dnia " + dataZabiegu + " wykonano zabieg " + zabiegPielegnacyjny + " " +
      " na dzialkach " + dzialkaRolna
  }
}
