package model

import java.time.LocalDate

import scala.collection.mutable

/** Asocjacja z atrybutem. Posredniczaca */

class DzialkaRolna_Zabieg(//asocjaja z Atrybutem
                          dataZabiegu: LocalDate,
                          powierzchniaZabiegu:Double,
                          dR:DzialkaRolna,
                          zP:ZabiegPielegnacyjny
                         ) extends ObjectPlusPlus {

  this.dodajPowiazanie(dR.getClass.getSimpleName,this.getClass.getSimpleName,dR)
  this.dodajPowiazanie(zP.getClass.getSimpleName,this.getClass.getSimpleName,zP)


}
