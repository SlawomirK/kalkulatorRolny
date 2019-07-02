package model

import java.time.{LocalDate, Year}

/**
  * Dziedziczenie overlaping. ZabiegPielegnacyjny-----Naawozenie i Oprysk
  * wykład. Wykład7 strona 26
  */
object ZabiegPielegnacyjny {
  private val nazwaRoliOprysk: String = "specjalizacjaOprysk"
  private val nazwaRoliNawozenie: String = "specjalizacjaNawozenie"
}

class ZabiegPielegnacyjny(powierzchnia: Double,
                          powodZabiegu: Enumy.powodZabiegu,
                          wykonujacyZabieg: WykonujacyZabieg, //
                          srodek: Srodek, //
                          organizm: Organizm, //
                          dzialkaRolna: DzialkaRolna,
                          _dataZabiegu: LocalDate) extends ObjectPlusPlus {
  private var _koszt = 0.0
  //atrybuty dodane w wyniku implementacji dziedziczenia wieloaspektowego i przeniesieniu
  //klas
  //-------------------------------------------------------
  private var _powodZabiegu = None: Option[Enumy.powodZabiegu]
  private var _listaSzkodnikow = None: Option[Set[OrganizmSzkodliwy]]
  private var _listaWarunkowDoPoprawy = None: Option[Set[String]]

  //-------------------------------------------------------
  this.dodajPowiazanie(srodek.getClass.getSimpleName, this.getClass.getSimpleName, srodek, srodek.nazwaSrodka)
  this.dodajPowiazanie(organizm.getClass.getSimpleName, this.getClass.getSimpleName, organizm)
  this.dodajPowiazanie(wykonujacyZabieg.getClass.getSimpleName, this.getClass.getSimpleName, wykonujacyZabieg)
  new DzialkaRolna_Zabieg(_dataZabiegu, powierzchnia, dzialkaRolna, this)

  //-------------------------metody przeniesione z dziedziczenia wieloaspektowego
  def usunSzkodnika(szk: OrganizmSzkodliwy) {
    _listaSzkodnikow = _listaSzkodnikow match {
      case Some(_listaSzkodnikow) => Option(_listaSzkodnikow.filterNot(s => s == szk))
      case None => throw new Exception("Brak szkodnikow")
    }

    def powodZabiegu_=(x: Option[Enumy.powodZabiegu]) {
      _powodZabiegu = x
    }

    def listaSzkodnikow_=(x: Option[Enumy.powodZabiegu]) {
      _powodZabiegu = x
    }

    def listaWarunkowDoPoprawy_=(x: Option[Set[String]]) {
      _listaWarunkowDoPoprawy = x
    }

    val powodZabiegu = _powodZabiegu
    val listaSzkodnikow = _listaSzkodnikow
    val listaWarunkowDoPoprawy = _listaWarunkowDoPoprawy
  }

  //------------------------------------------------------------------------------------

  def dataZabiegu = _dataZabiegu

  def koszt = _koszt

  def koszt_=(k: Double) = {
    require(k > 0, "Nalezy wpisac dodatnia wartosc")
    _koszt += k
  }

  def dodajOprysk(substancjeBiologicznieCzynne: Set[String]) = {
    val oprysk = new Oprysk(substancjeBiologicznieCzynne)
    this.dodajPowiazanie(ZabiegPielegnacyjny.nazwaRoliOprysk, "generalizacja", oprysk)
  }

  //dodaje informacje o ilosci skladnikow pokarmowych i tworzy nowy obiekt Nawozenie
  //(nazwa,procent)
  def dodajNawozenie(skladnikiDodane: (String, Double)): Unit = {
    val nawozenie = new Nawozenie(skladnikiDodane)
    this.dodajPowiazanie(ZabiegPielegnacyjny.nazwaRoliNawozenie, "generalizacja", nawozenie)
  }

  def dajOpryskiPrzeprowadzoneWRoku(rok: Year): Array[ZabiegPielegnacyjny] = {
    //daj zabiegi przeprowadzone w rok

    try {
      return this.dajPowiazania(ZabiegPielegnacyjny.nazwaRoliOprysk).
        asInstanceOf[Array[ZabiegPielegnacyjny]].
        filter(s => s.dataZabiegu.getYear == rok)
    } catch {
      case e: Exception => throw new Exception("Brak oprysku w zadanym roku!");
    }
  }

  def dajZastosowaneDawkiPokarmoweNaDzialce(dzRolna: DzialkaRolna, rok: Year): Unit = {
    try {
      val d = this.dajPowiazania(ZabiegPielegnacyjny.nazwaRoliNawozenie).asInstanceOf[Array[ZabiegPielegnacyjny]].
        filter(s => s.dataZabiegu.getYear == rok)
      return d.filter(s=>s.dajPowiazanyObiekt(dzRolna.klasa.getSimpleName,dzRolna.getOznaczenie)==dzRolna)
    } catch {
      case e: Exception => throw new Exception("Brak nawozenia na dzialce " + dzRolna.getOznaczenie + " w roku " + rok)
    }
  }

}
  /*///zmiana konstruktora w zwiaz:ku z overlaping
  case class Oprysk(
                     powodZabiegu: Enumy.ZabiegiNaRosliny,
                     wykonujacyZabieg: WykonujacyZabieg,
                     preparat: Srodek,
                     organizm:Organizm
                   ) extends ZabiegPielegnacyjny(powodZabiegu, wykonujacyZabieg,preparat,organizm) {

  }*/
  case class Oprysk(_sklad: Set[String]) extends ObjectPlusPlus {
    val sklad = _sklad
  }

  /*
  case class Nawozenie(
                        powodZabiegu: Enumy.ZabiegiNaRosliny,
                        wykonujacyZabieg: WykonujacyZabieg,
                        nawoz:Srodek,
                        organizm:Roslina
                      ) extends ZabiegPielegnacyjny(powodZabiegu, wykonujacyZabieg,nawoz,organizm) {

  }*/
  case class Nawozenie(_sklad: (String, Double)) extends ObjectPlusPlus {
    val sklad = _sklad
  }



/** ==========================. */
