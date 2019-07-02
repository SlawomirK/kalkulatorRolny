package model

import java.io.PrintStream

import java.util
object ObjectPlusPlus {
  /** przechowuje informacje o wszystkich czesicach powiazanych z ktorymkolwiek z obiektow */
   private var wszystkieCzesci = new util.HashSet[ObjectPlusPlus]()

  def usunCzesc(czescDoUsuniecia:ObjectPlusPlus)={
    require(!wszystkieCzesci.isEmpty&&wszystkieCzesci.contains(czescDoUsuniecia),throw new Exception("Ten obiekt nie jest czescia "+this.getClass.getCanonicalName))
    wszystkieCzesci.remove(czescDoUsuniecia)
    ObjectPlus.usunEkstensje(czescDoUsuniecia)
  }
  def usunCalosc()={
    this.wszystkieCzesci.stream().forEach(s=>ObjectPlusPlus.usunCzesc(s))
  }
  //this=CaloscDotychczasowa
  def przekazCzescDoInnejCalosci(czescDoPrzekazania:ObjectPlusPlus,nowaCalosc:ObjectPlusPlus)={
    this.usunCzesc(czescDoPrzekazania)
    nowaCalosc.dodajCzesc(czescDoPrzekazania.getClass.getSimpleName+"_czesc",
      nowaCalosc.getClass.getSimpleName+"_calosc",
      czescDoPrzekazania)
  }
}

class ObjectPlusPlus extends ObjectPlus() with Serializable {
  /** Przechowuje informacje o wszystkich powiazaniach tego obiektu */

  import java.util
  private var powiazania = new util.Hashtable[String, util.HashMap[Any, ObjectPlusPlus]]

   def dodajPowiazanie(
                       nazwaRoli: String,
                       odwrotnaNazwaRoli: String,
                       obiektDocelowy: ObjectPlusPlus,
                       kwalifikator: Any): Unit = {
    dodajPowiazanie(
      nazwaRoli,
      odwrotnaNazwaRoli,
      obiektDocelowy,
      kwalifikator,
      2)
  }


  def dodajPowiazanie(
                       nazwaRoli: String,
                       odwrotnaNazwaRoli: String,
                       obiektDocelowy: ObjectPlusPlus): Unit = {
    dodajPowiazanie(
      nazwaRoli,
      odwrotnaNazwaRoli,
      obiektDocelowy,
      obiektDocelowy)
    println("powiazania z metody========================= "+powiazania)
  }

  @throws(classOf[Exception])
  def dodajCzesc(
                  nazwaRoli: String,
                  odwrotnaNazwaRoli: String,
                  obiektCzesc: ObjectPlusPlus): Unit = {
    //sprawdz czy ta czesc juz gdzies nie wystepuje
    if (ObjectPlusPlus.wszystkieCzesci.contains(obiektCzesc)) {
      throw new Exception("Ta czesc jest juz powiazana z jakąs caloscia " + obiektCzesc.toString)
    }else {
      dodajPowiazanie(nazwaRoli, odwrotnaNazwaRoli, obiektCzesc)
      //Zapamietaj dodanie obiektu jako czesci
      ObjectPlusPlus.wszystkieCzesci.add(obiektCzesc)
    }
  }

  @throws(classOf[Exception])
  def dajPowiazania(nazwaRoli: String): Array[ObjectPlusPlus] = {
    var powiazaniaObiektu: util.HashMap[Any, ObjectPlusPlus] = new util.HashMap()
    if (!powiazania.containsKey(nazwaRoli)) {
      //brak powiazan dla tej roli
      throw new Exception("Brak powiazan dla roli: " + nazwaRoli)
    }else{
    powiazaniaObiektu = powiazania.get(nazwaRoli)
    powiazaniaObiektu.values.toArray(new Array[ObjectPlusPlus](0)) //- mas06 str 26
  }}

  @throws(classOf[Exception])
  def wyswietlPowiazania(
                          nazwaRoli: String,
                          stream: PrintStream): Unit = {
    var powiazaniaObiektu: util.HashMap[Any, ObjectPlusPlus] = new util.HashMap
    if (!powiazania.containsKey(nazwaRoli)) {
      //brak powiazan dla tej roli
      throw new Exception("Brak powiazan dla roli: " + nazwaRoli)
    }
    powiazaniaObiektu = powiazania.get(nazwaRoli)
    var col: util.Collection[ObjectPlusPlus] = powiazaniaObiektu.values
    stream.println(this.getClass.getSimpleName + " powiazania w roli " + nazwaRoli + ":")
    import scala.collection.JavaConversions._
    for (obj <- col) {
      stream.println(" " + obj)
    }
  }

  @throws(classOf[Exception])
  def dajPowiazanyObiekt(
                          nazwaRoli: String,
                          kwalifikator: Any): ObjectPlusPlus = {

    var powiazaniaObiektu: util.HashMap[Any, ObjectPlusPlus] = null
    if (!powiazania.contains(nazwaRoli)) {
      //brak powiazan dla tej roli
      throw new Exception("Brak powiazan dla roli: " + nazwaRoli)
    }
    powiazaniaObiektu = powiazania.get(nazwaRoli)
    if (!powiazaniaObiektu.containsKey(kwalifikator)) {
      //brak powiazan dla tej roli
      throw new Exception("Brak powiazania dla kwalifikatora " + kwalifikator)
    }
    powiazaniaObiektu.get(kwalifikator).asInstanceOf[ObjectPlusPlus]
  }
  /**Metoda usuwajaca powiazania obiketu.
    * Przyjmujaca referencje do obiektu ktorego powiazanie chcemy usunac*/
  def usunPowiazanie(powiazanieDoUsuniecia:ObjectPlusPlus)={
    require(powiazania.containsKey(powiazanieDoUsuniecia),throw new Exception("Ten obiekt nie jest powiazany"))
    this.powiazania.remove(powiazanieDoUsuniecia)
  }
  private def dodajPowiazanie(
                               nazwaRoli: String,
                               odwrotnaNazwaRoli: String,
                               obiektDocelowy: ObjectPlusPlus,
                               kwalifikator: Any,
                               licznik: Int): Unit = {

    var powiazaniaObiektu: util.HashMap[Any,ObjectPlusPlus] = null

    if (licznik < 1) {return}
    if (powiazania.containsKey(nazwaRoli)) {
      //pobierz te powiazania
      powiazaniaObiektu = powiazania.get(nazwaRoli)
    } else {
      //brak powiazan dla takiej roli=>utworz
      powiazaniaObiektu = new util.HashMap[Any,ObjectPlusPlus]()
      powiazania.put(nazwaRoli, powiazaniaObiektu)
    }

    if (!powiazaniaObiektu.containsKey(kwalifikator)) {
      //dodaj powiazanie dla tego obiektu
      powiazaniaObiektu.put(kwalifikator, obiektDocelowy)
      //dodaj powiazanie zwrotne

      obiektDocelowy.dodajPowiazanie(
        odwrotnaNazwaRoli,
        nazwaRoli,
        this,
        this,
        licznik - 1)
    }
  }
}