import scala.collection.mutable

case class Adres(
                  private val ulica: String,
                  private val nrDomu: String,
                  private val miejscowosc: String) extends ObjectPlus

object Gospodarstwo {
  var dzialkiWGosodarstwie: mutable.HashSet[Dzialka] = mutable.HashSet[Dzialka]()
}

class Gospodarstwo(
                    private val adres: Adres,
                    private val numerGospodarstwa: Int) extends ObjectPlus {
  private var dzialki: Vector[Dzialka] = Vector[Dzialka]()

  def dodajDzialke(dz: Dzialka) = {
    //metoda dodajaca czesc do calosi
    if (!dzialki.contains(dz)) {
      if (Gospodarstwo.dzialkiWGosodarstwie.contains(dz)) throw new Exception("Ta dzialka juz jest powiazana z gospodarstwem")
      dzialki = dzialki :+ dz
      Gospodarstwo.dzialkiWGosodarstwie.+=(dz)

    }
  }

  override def toString: String = "Gospodarstwo " + adres.toString + " nr " + numerGospodarstwa
}