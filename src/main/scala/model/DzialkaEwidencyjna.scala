package model

import scala.collection.mutable

class DzialkaEwidencyjna(powierzchniaDzialki: Double,
                         oznaczenie: String)
  extends Dzialka(powierzchniaDzialki, oznaczenie) {

  private val powierzchniaDzialkiRolnejWGranicachDzialkiEwidencyjnej: Double = 0


  // def getPowierzchnia = powierzchniaDzialki

  override def toString: String = super.toString + " Dzialki rolne na tej dzialce ewidencyjnej " + wypiszDzialkiNaTejDzialce
}
