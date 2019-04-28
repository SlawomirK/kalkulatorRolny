package model

import scala.collection.mutable.HashMap


abstract class Dzialka(
                        powierzchnia: Double,
                        oznaczenie: String,
                        gospodarstwo: Gospodarstwo = new Gospodarstwo(new Adres("domyslna Ulica", "domyslny NrDomu", "domyslna Miejscowosc"), 102))
  extends ObjectPlus {


  protected var dzialkiWTejDzialce: HashMap[Dzialka, Double] = null

  def getPowierzchnia = powierzchnia

  def getOznaczenie = oznaczenie

  gospodarstwo.dodajDzialke(this)

  def usunDzialke(klucz: Dzialka): Unit = {
    if (dzialkiWTejDzialce.nonEmpty && dzialkiWTejDzialce.contains(klucz)) {
      dzialkiWTejDzialce -= (klucz)
      klucz.usunDzialke(this) //automatyczne usuniecie dzialki rolnej z dzialek ewidencyjnych
    } else {
      throw new NoSuchElementException("Nie odnaleziono dzialki o tym oznaczeniu")
    }
  }

  def dodajDzialkiWchodzaceWSkladTejDzialki(dzialkaNaKtorejLezy: Dzialka,
                                            powierzchniaSkladowej: Double): Unit = {

    // require(powierzchniaSkladowej < dzialkaNaKtorejLezy.getPowierzchnia,throw new Exception("Czesc dzialki ewidencyjnej nie moze byc wieksza od calosci!"))

    if (dzialkiWTejDzialce == null)
      dzialkiWTejDzialce = HashMap[Dzialka, Double]()
    else (!this.dzialkiWTejDzialce.contains(this))
    this.dzialkiWTejDzialce += (dzialkaNaKtorejLezy -> powierzchniaSkladowej)
    if (dzialkaNaKtorejLezy.dzialkiWTejDzialce == null || (!dzialkaNaKtorejLezy.dzialkiWTejDzialce.contains(this)))
      dzialkaNaKtorejLezy.dodajDzialkiWchodzaceWSkladTejDzialki(this, powierzchniaSkladowej)
  }

  def getDzialki: HashMap[Dzialka, Double] = {
    dzialkiWTejDzialce
  }

  def wypiszDzialkiNaTejDzialce: String = {
    var dzE: String = " "

    val dz = Option(dzialkiWTejDzialce)
    dz match {
      case Some(dz) => {
        dzialkiWTejDzialce.foreach(f => dzE + "dzialaka " + f._1.getOznaczenie + " powierzchnia " + f._2)
      }
      case None => {
        dzE = "Brak zdefiowanych dzialek na tej dzialce rolnej "
      }
    }
    dzE
  }

  def powierzchniaDzialkiW_Dzialce(klucz: Dzialka): Double = {
    if (!dzialkiWTejDzialce.contains(klucz)) throw new NoSuchElementException("Nie odnaleziono dzialki o tym oznaczeniu")
    dzialkiWTejDzialce.getOrElse(klucz, 0)
  }

  override def toString: String = {
    this.getClass.getName + " " + this.oznaczenie + " ma powierzchnie " + this.powierzchnia
  }
}