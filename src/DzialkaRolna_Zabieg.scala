import java.time.LocalDate

class DzialkaRolna_Zabieg(
                           dataZabiegu: LocalDate,
                           powodZabiegu: Enumy.ZabiegiNaRosliny,
                           dzialkiRolne: DzialkaRolna,
                           zabiegiPielegnacyjne: ZabiegPielegnacyjny) extends ObjectPlus {
  def getDataZabiegu = dataZabiegu

  def getPowodZabiegu = powodZabiegu

  def getDzialkiNaKtorychWykonanoZabieg = dzialkiRolne

  def getZabiegiWykonane = zabiegiPielegnacyjne

  override def toString: String = {
    "Dnia " + dataZabiegu + " wykonano zabieg " + zabiegiPielegnacyjne + " " +
      " na " + powodZabiegu + " na dzialkach " + dzialkiRolne
  }
}
