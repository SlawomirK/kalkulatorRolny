package model

import model.Enumy.SystematykaBiologiczna


/** ATRYBUT ZLOZONY jego atrybutami jest enum i String nazwa jest wymagana. */
class Roslina(_nazwa: String,
              systematykaBotaniczna: Option[Enumy.SystematykaBiologiczna],
              _czyOzime: Boolean) //dzialki na ktorych jest roslina
  extends Organizm(_nazwa, systematykaBotaniczna) with ISamosiewy {


  require((_nazwa) != null, "Nie określono nazwy rośliny")

def zmienKlase(czyOzime:Boolean)={
 new OrganizmSzkodliwy(this.nazwa,this.systematykaBiologiczna)
}

  //w przypadku gdy drugi argument nie zostanie podany nastapi przeciazenie konstruktora
  // private val typRoslin = systematykaBotaniczna


  override val nazwa: String = _nazwa
  val czyOzime = _czyOzime



  override def toString: String = {
    val tekst = "Na polu jest " + this._nazwa
    systematykaBotaniczna match {
      case Some(Enumy.jednoliscienne) => tekst + " ktora nalezy do jednoliscienne"
      case Some(Enumy.dwuliscienne) => tekst + " ktora nalezy do dwuliscienne"
      case Some(Enumy.wieloletnie)=>tekst+"która jest rośliną wieloletnią"
      case None => tekst + " i nic nie rosnie"
      case _ => " Nie rozpoznaje rodzaju tej rosliny"
    }
  }

  //TODO:Metody do implementacji w pozniejszych wersjach
  override def aktualneCenyRynkowe(roslinaNaSprzedaz: Roslina, wojewodztwo: String): Double = ???

  override def szacunkowyDochodZaTajRosline(uzyskanyPlon_t: Double): Double = ???
}