package model

import model.Enumy.SystematykaBiologiczna

import scala.collection.mutable

abstract class Organizm(_nazwa:String,
                        _systematykaBiologiczna:Option[Enumy.SystematykaBiologiczna]=None) extends ObjectPlusPlus {
 protected var dzialkiRolne = mutable.Set.empty[DzialkaRolna] //atrybut opjonalny
  def dodajDzialkaNaKtorejWystepuje(dz:DzialkaRolna)={
    if (!dzialkiRolne.contains(dz)) {
      dzialkiRolne+= dz
    }
  }
  def nazwa=_nazwa
  def systematykaBiologiczna=_systematykaBiologiczna
  def dzialkiRolna_=(x:mutable.Set[DzialkaRolna])={dzialkiRolne=x}
def zmienKlase(czyOzime:Boolean)
}

class OrganizmSzkodliwy(nazwa:String,
                        systematykaBiologiczna: Option[Enumy.SystematykaBiologiczna]) extends Organizm(nazwa,systematykaBiologiczna){


  //Konstruktor potrzebny do dziedziczenia dynamicznego. Tworzenie obiektu na podstawie starego obiektu
 def this(organizm:Organizm)=this(organizm.nazwa,organizm.systematykaBiologiczna)

  //TODO:metoda ta ma docelowo zwracac ostrzezenie o alercie przekroczenia progu szkodliwosci z systemu internetowego
  def wyswietlAlertDotyczacyProguSzkodliwosci(nazwaPatogenu:String,wojewodztwo:String):String ={
"Wynik"
  }

def zmienKlase(czyOzime:Boolean)={
  new Roslina(this.nazwa,this.systematykaBiologiczna,czyOzime)
}
}
