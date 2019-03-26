import scala.collection.mutable.HashMap

/**
  * Object Dzialka {
  *
  * @throws (classOf[NoSuchElementException])
  *         def pokazEstensje: Unit = ObjectPlus.pokazEkstensje(this.getClass)
  *
  *         def apply(powierzchnia: Double, //implementacja kompozycji
  *         oznaczenie: String,
  *         gospodarstwo: Gospodarstwo
  *         ): Dzialka = {
  *         if (gospodarstwo == null) throw new Exception("Gospodarstwo  nie istnieje")
  *         else {
  *         val dz: Dzialka = Dzialka(powierzchnia, oznaczenie, gospodarstwo) //dzialka jest abstr nie moze byc new
  * gospodarstwo.dodajDzialke(dz)
  *         dz
  *         }
  *         }
  *         }
  */
object Dzialka {

}

abstract class Dzialka(
                        powierzchnia: Double,
                        oznaczenie: String,
                        gospodarstwo: Gospodarstwo = new Gospodarstwo(new Adres("domyslna Ulica", "domyslny NrDomu", "domyslna Miejscowosc"), 102))
  extends ObjectPlus {

  def getPowierzchnia = powierzchnia
  def getOznaczenie = oznaczenie


  protected var dzialkiWTejDzialce:HashMap[Dzialka, Double] = HashMap.empty

  def usunDzialke(klucz: Dzialka): Unit = {
    val dzialki=Option(dzialkiWTejDzialce)
    dzialki match {
      case None => {
        throw new NullPointerException("Brak jeszcze zadeklarowanych dzialek na tej dzialce")
      }
      case Some(dzialki) => {
        if (dzialki.contains(klucz)) {
          dzialki -= (klucz)
          dzialkiWTejDzialce=dzialki
          klucz.usunDzialke(this) //automatyczne usuniecie dzialki rolnej z dzialek ewidencyjnych
        } else {
          throw new NoSuchElementException("Nie odnaleziono dzialki o tym oznaczeniu")
        }
      }
    }
  }

  def getDzialki:HashMap[Dzialka, Double] = {
    dzialkiWTejDzialce
  }

  def wypiszDzialkiNaTejDzialce: String = {
    var dzE: String =" "

    val dz=Option(dzialkiWTejDzialce)
    dz match {
      case Some(dz) => {
        dz.foreach(f => dzE.concat("dzialaka " + f._1 + " powierzchnia " + f._2))
      }
      case None => {
        dzE = "Brak zdefiowanych dzialek na tej dzialce rolnej "
      }
    }
    dzE
  }

  def powierzchniaDzialkiW_Dzialce(klucz: Dzialka):Double = {
    if (!dzialkiWTejDzialce.contains(klucz)) throw new NoSuchElementException("Nie odnaleziono dzialki o tym oznaczeniu")
    dzialkiWTejDzialce.getOrElse(klucz,0)
  }
  override def toString: String = {
    this.getClass.getName + " " + this.oznaczenie + " ma powierzchnie " + this.powierzchnia
  }
}