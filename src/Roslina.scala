
/** ATRYBUT ZLOZONY jego atrybutami jest enum i String nazwa jest wymagana. */
class Roslina(private val nazwa: String,
              private val systematykaBotaniczna: Enumy.SystematykaUpraw)
  extends ObjectPlus {
  require((nazwa) != null, "Nie określono nazwy rośliny")

  //w przypadku gdy drugi argument nie zostanie podany nastapi przeciazenie konstruktora
  // private val typRoslin = systematykaBotaniczna
  def this() {
    this(nazwa = "ugor", Enumy.chwasty)
  }

  def getNazwa: String = nazwa

  override def toString: String = {
    val tekst = "Na polu jest " + this.nazwa
    systematykaBotaniczna match {
      case jednoliscienne => tekst + " ktora nalezy do jednoliscienne"
      case dwuliscienne => tekst + " ktora nalezy do dwuliscienne"
      case null => tekst + " i nic nie rosnie"
    }
  }
}