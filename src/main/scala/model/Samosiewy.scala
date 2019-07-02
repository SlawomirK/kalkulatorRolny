package model

import model.Enumy.SystematykaBiologiczna

//przyklad klasy dziedziczacej jednoczesnie po roslinach uprawnych jak i po organizmach szkodliwych
class Samosiewy(nazwa:String,systematykaBilogizna:Option[Enumy.SystematykaBiologiczna]) extends OrganizmSzkodliwy(nazwa,systematykaBilogizna) with ISamosiewy {

  private var _czyOzime: Boolean = true

  val roslina:Roslina=new Roslina(nazwa,systematykaBilogizna,_czyOzime)
  def czyOzime_= (czy:Boolean){_czyOzime=czy}
  //TODO:Metoda do uzupelnienia w pozniejszej wersji
  override def szacunkowyDochodZaTajRosline(uzyskanyPlon_t: Double): Double={
    12.9
  }
//TODO:funkcja zwracajaca srednia cene okreslonej roliny w wojewodztwie uzytkownika
  override def aktualneCenyRynkowe(roslinaNaSprzedaz: Roslina, wojewodztwo: String): Double = 122.0




}
