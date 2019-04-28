package model

import scala.collection.mutable


/** ATRYBUT ZLOZONY jego atrybutami jest enum i String nazwa jest wymagana. */
class Roslina(nazwa: String,
              systematykaBotaniczna: Enumy.SystematykaUpraw,
             ) //dzialki na ktorych jest roslina
  extends ObjectPlus {
  var dzialkiRolne = mutable.Set.empty[DzialkaRolna] //atrybut opjonalny

  require((nazwa) != null, "Nie określono nazwy rośliny")

  def dodajDzialke(dz: DzialkaRolna) = {

    if (!dzialkiRolne.contains(dz)) {
       dzialkiRolne+= dz
    }
  }

  //w przypadku gdy drugi argument nie zostanie podany nastapi przeciazenie konstruktora
  // private val typRoslin = systematykaBotaniczna
  def this() {
    this(nazwa = "ugor", Enumy.chwasty)
  }

  def getNazwa: String = nazwa

  override def toString: String = {
    val tekst = "Na polu jest " + this.nazwa
    systematykaBotaniczna match {
      case Enumy.JEDNOLISCIENNE => tekst + " ktora nalezy do jednoliscienne"
      case Enumy.DWULISCIENNE => tekst + " ktora nalezy do dwuliscienne"
      case null => tekst + " i nic nie rosnie"
    }
  }
}