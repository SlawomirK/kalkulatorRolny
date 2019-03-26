

class DzialkaEwidencyjna(powierzchniaDzialki: Double,
                         oznaczenie: String)
  extends Dzialka(powierzchniaDzialki, oznaczenie, null) {
  /**
    * ATRYBUT WIELOWARTOSCIOWY
    * Atrybut powtarzalny set[Tuple[dzialkaEwidencyjna,powierzchniawGraniach]].
    */
  private val powierzchniaDzialkiRolnejWGranicachDzialkiEwidencyjnej: Double = 0


  def dodajDzialkeRolna(dzialkaRolna: DzialkaRolna, powierzchniaWGranicach: Double): Unit = {
    require(!dzialkiWTejDzialce.contains(dzialkaRolna), "Ta dzialka Rolna juz jest wprowadzona")
    val powierzchniaWGranicach_ = Option(powierzchniaWGranicach)
    if (powierzchniaWGranicach > dzialkaRolna.getPowierzchnia) throw new Exception("Czesc dzialki ewidencyjnej nie moze byc wieksza od calosci!")
    else {
      powierzchniaWGranicach_ match {
        case Some(powierzchniaWGranicach_) => {
          dzialkiWTejDzialce.+=(dzialkaRolna -> powierzchniaWGranicach_)
          dzialkaRolna.dodajDzialkeEwidencyjna(this, powierzchniaWGranicach_)
        }
        case None => {
          println("Nieprawidlowa powierzchnia dzialki rolnej na dzialce ewidencyjnej " + this.getOznaczenie)
        }
      }
    }
  }

  // def getPowierzchnia = powierzchniaDzialki

  override def toString: String = super.toString + " Dzialki rolne na tej dzialce ewidencyjnej " + wypiszDzialkiNaTejDzialce
}
