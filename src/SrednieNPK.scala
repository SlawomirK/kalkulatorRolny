import java.util.Calendar

object SrednieNPK {

  private val dawkiNPK = Map(
    "pszenica" -> (150, 70, 80),
    "rzepak" -> (180, 100, 150)) //*ATRYBUTU KLASOWEGO

  @transient
  def czyMogestosowacAzot: Boolean = {
    //METODA STATYCZNA, WYLICZALNA
    val obecnyMiesiac = Calendar.MONTH
    obecnyMiesiac < 12 && obecnyMiesiac > 2
  }

  def getDawkiWzorcowe(i: String): Option[(Int, Int, Int)] = {
    dawkiNPK.get(i)
  }
}

class SrednieNPK extends ObjectPlus {
  private var czyMoznaStosowacN: Boolean = SrednieNPK.czyMogestosowacAzot //ATRYBUT WYLICZALNY odwalanie do klasowej metody

  //azotu nie moĹĽna stosowaÄ‡ od 1 grudnia do 1 marca
  //-1-PRZECIAZENIE ile trzeba dac na konkretna dzialke
  def ileNPKpotrzebuje(dzialkaRolna: DzialkaRolna): (Int, Int, Int) = {

    val danaDawkaNPK: (Int, Int, Int) = dzialkaRolna.getSypnieteNPK()
    val wzorcowaDawkaNPK: (Int, Int, Int) = SrednieNPK.dawkiNPK(dzialkaRolna.getRoslina.getNazwa)
    val powierzchnia = dzialkaRolna.getPowierzchniaDzRolnej

    val wy = Tuple3(
      (wzorcowaDawkaNPK._1 * powierzchnia - danaDawkaNPK._1).asInstanceOf[Int],
      (wzorcowaDawkaNPK._2 * powierzchnia - danaDawkaNPK._2).asInstanceOf[Int],
      (wzorcowaDawkaNPK._3 * powierzchnia - danaDawkaNPK._3).asInstanceOf[Int])
    wy
  }

  /** -2- PRZECIAZENIE ta sama nazwa inny argument ile potrzeba jeszcze pod wszystkie uprawy w sumie. */
  def ileNPKpotrzebuje(wszystkieDzialki: DzialkiEkstensje): (Int, Int, Int) = {
    var wszystkieDzialkiRolneObsiane: Vector[DzialkaRolna] = {
      for (
        dzialka <- wszystkieDzialki.getWszystkieDzialki if (dzialka.isInstanceOf[DzialkaRolna])
      ) yield dzialka.asInstanceOf[DzialkaRolna]
    }
    val wynik: (Int, Int, Int) = {
      wszystkieDzialkiRolneObsiane = wszystkieDzialkiRolneObsiane.filter(_.getRoslina.getNazwa != "ugor")

      var n = 0
      var p = 0
      var k = 0
      wszystkieDzialkiRolneObsiane.foreach(i => n += this.ileNPKpotrzebuje(i)._1.asInstanceOf[Int])
      wszystkieDzialkiRolneObsiane.foreach(i => p += this.ileNPKpotrzebuje(i)._2.asInstanceOf[Int])
      wszystkieDzialkiRolneObsiane.foreach(i => k += this.ileNPKpotrzebuje(i)._3.asInstanceOf[Int])
      val w: (Int, Int, Int) = Tuple3(n, p, k)
      w
    }
    wynik
  }

  def applay(nazwa: String) = SrednieNPK.dawkiNPK.get(nazwa)
}
