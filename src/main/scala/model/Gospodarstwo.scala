package model

import scala.collection.mutable

case class Adres(
                  private val ulica: String,
                  private val nrDomu: String,
                  private val miejscowosc: String) extends ObjectPlus

object Gospodarstwo {
  var dzialkiWGospodarstwie: mutable.HashSet[Dzialka] = mutable.HashSet[Dzialka]()

  //klasowa, wyliczalna
  def getPowierzchniaNieuzytkowanaRolniczo: Double = {
    var powierzchniaEwidencyjnych: Double = 0
    var powierzchniaRolnych: Double = 0
    dzialkiWGospodarstwie.filter(s => s.isInstanceOf[DzialkaEwidencyjna]).foreach(s => {
      powierzchniaEwidencyjnych += s.getPowierzchnia
    })
    dzialkiWGospodarstwie.filter(s => s.isInstanceOf[DzialkaRolna]).foreach(s => {
      powierzchniaRolnych += s.getPowierzchnia
    })
    (powierzchniaEwidencyjnych - powierzchniaRolnych)
  }

  def getDzialkiWGospodarstwie = {
    dzialkiWGospodarstwie
  }
}

class Gospodarstwo(
                    private val adres: Adres,
                    private val numerGospodarstwa: Int) extends ObjectPlus {

  private var dzialki: Vector[_ <: Dzialka] = Vector()

  def dodajDzialke(dz: Dzialka) = {
    //metoda dodajaca czesc do calosi
    if (!dzialki.contains(dz)) {
      if (Gospodarstwo.dzialkiWGospodarstwie.contains(dz)) throw new Exception("Ta dzialka juz jest powiazana z gospodarstwem")
      dzialki = dzialki :+ dz
      Gospodarstwo.dzialkiWGospodarstwie.+=(dz)
    }
  }

  override def toString: String = "Gospodarstwo " + adres.toString + " nr " + numerGospodarstwa
}
