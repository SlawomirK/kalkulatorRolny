package model

/**
  *
  * ATRYBUT ZLOZONY --- ROSLINA
  * ATRYBUT OPCJONALNY jeĹĽeli nie poda siÄ™ obiektu rosliny zostanie przyjÄ™ty argument domyĹ›lny.
  */
class DzialkaRolna(
                    powierzchnia: Double,
                    oznaczenie: String,
                    roslina: Roslina) //--ascjacja1--1
  extends Dzialka(powierzchnia, oznaczenie) {

  //def this(powierzchnia:Double,oznaczenie:String,roslina: Roslina)=this(powierzchnia,oznaczenie,roslina,Option(dzialkaRolna_Zabieg))

  roslina.dodajDzialke(this)

  //klucz oznaczenie,(dzialkaRolna,powierzchnia w ramach dzialki ewidencyjnej
  //ATRYBUT WIELOWARTOSCIOWY
  //Atrybut powtarzalny set[Tuple[dzialkaEwidencyjna,powierzchniawGraniach]]
  private val powierzchniaDzialkiEwidencyjejWGranicachDzialkiRolnej: Double = 0
  private var ileNPKzastosowano = Tuple3(0, 0, 0) //zmienna przechowujaca ile do tej pory zastosowano nawozow

  def setSypnieteNPK(N: Int, P: Int, K: Int): Unit = {
    val ile = Tuple3(ileNPKzastosowano._1 + N, ileNPKzastosowano._2 + P, ileNPKzastosowano._3 + K)
    ileNPKzastosowano = ile
  }

  def getSypnieteNPK(): Tuple3[Int, Int, Int] = ileNPKzastosowano

  def getRoslina: Roslina = roslina

  def getPowierzchniaDzRolnej: Double = powierzchnia


  override def toString: String = super.toString + " " + roslina.toString + " dzialki ewidencyjne na tej dzialce rolnej " + wypiszDzialkiNaTejDzialce
}