package model

import java.time.LocalDate

class Osoba(
             imie: String,
             nazwisko: String,
             adres: AdresZamieszkania) extends ObjectPlusPlus {


  //---------------------------------------------------------------
}

class WykonujacyZabieg(imie: String,
                       nazwisko: String,
                       adres: AdresZamieszkania,
                       uprawnieniaChemizacyjne: LocalDate) extends Osoba(imie, nazwisko, adres) {
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
