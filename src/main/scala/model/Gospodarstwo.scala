package model

import scala.collection.mutable

case class Adres(
                  ulica: String,
                  nrDomu: String,
                  miejscowosc: String) extends ObjectPlusPlus

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
                     adres: Adres,
                     numerGospodarstwa: Int) extends ObjectPlusPlus() {


  private var dzialki = Vector[ Dzialka]()
//TODO: przerobic to wykorzystujac danej z ObjectPlusPLus
  def dodajDzialke(dz: Dzialka) = {
    //metoda dodajaca czesc do calosi
    this.dodajCzesc(dz.getClass.getSimpleName+"_czesc",this.getClass.getSimpleName+"_calosc",dz)

    if (!dzialki.contains(dz)) {
      if (Gospodarstwo.dzialkiWGospodarstwie.contains(dz)) throw new Exception("Ta dzialka juz jest powiazana z gospodarstwem")
      dzialki = dzialki :+ dz
      Gospodarstwo.dzialkiWGospodarstwie.+=(dz)
    }
  }

  override def toString: String = "Gospodarstwo " + adres.toString + " nr " + numerGospodarstwa
}
