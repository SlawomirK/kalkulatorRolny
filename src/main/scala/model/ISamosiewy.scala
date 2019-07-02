package model

trait ISamosiewy {


  //metoda zwracajaca ceny rynkowe w danym wojewodztwie
def aktualneCenyRynkowe(roslinaNaSprzedaz:Roslina,wojewodztwo:String):Double
  //metoda wylicza cene ktora pokrywa koszty
  def szacunkowyDochodZaTajRosline(uzyskanyPlon_t:Double):Double
  def toString:String

}
