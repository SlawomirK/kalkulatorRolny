import java.time.LocalDate

/**
  * Asocjacja binarna. Dla uproszczenia dwie klasy w
  * ejdnym pliku
  * asocjacjaBinarna 1WykonujacyZabieg-1zabieg
  */

case class ZabiegPielegnacyjny(
                                /** Atrybut zlozony. */
                                dataWykonaniaZabiegu: LocalDate,

                                /** AsocjacjaDoZabiegu. */
                                wykonujacyZabieg: WykonujacyZabieg,

                                /** ---------------------------------------------------------. */
                                dzialkaRolna: DzialkaRolna) extends ObjectPlusPlus {
  def getDataZabiegu = dataWykonaniaZabiegu

  def getWykonujacyZabieg = wykonujacyZabieg

  def getDzialkaRolna = dzialkaRolna
}

/** ==========================. */
case class Osoba(
                  private val imie: String,
                  private val nazwisko: String,
                  private val adres: AdresZamieszkania) extends ObjectPlusPlus {
  def getImie = imie

  def getNazwisko = nazwisko

  def getAdres = adres

  //---------------------------------------------------------------
}

class WykonujacyZabieg(
                        imie: String,
                        nazwisko: String,

                        /** AsocjacjaDoZabiegu. */
                        zabiegWykonany: ZabiegPielegnacyjny,
                        adres: AdresZamieszkania,
                        uprawnieniaChemizacyjne: LocalDate) extends Osoba(imie, nazwisko, adres) {
  def this(imie: String, nazwisko: String, zabiegWykonany: ZabiegPielegnacyjny) = {
    this(imie, nazwisko, zabiegWykonany, null, null)
  }

  def getZabiegWykonany = zabiegWykonany
}

//--------------------------------------------
case class AdresZamieszkania(
                              miejscowosc: String,
                              nrDomu: String,
                              ulica: String) extends ObjectPlusPlus {

  def getMiescowosc = miejscowosc

  def getnrDomu = nrDomu

  def getUlica = ulica
}

//----------------------------