package model

import java.io.PrintStream

import scala.collection.JavaConverters._
import scala.collection.mutable

object ObjectPlusPlus {
  /** przechowuje informacje o wszystkich czesicach powiazanych z ktorymkolwiek z obiektow */
  private val wszystkieCzesci: mutable.HashSet[ObjectPlusPlus] = mutable.HashSet()
}

class ObjectPlusPlus extends ObjectPlus with Serializable {
  /** Przechowuje informacje o wszystkich powiazaniach tego obiektu */
  private val powiazania = mutable.HashMap[String, mutable.HashMap[Any, ObjectPlusPlus]]()

  def dodajPowiazanie(nazwaRoli: String,
                      odwrotnaNazwaRoli: String,
                      obiektDocelowy: ObjectPlusPlus,
                      kwalifikator: Any,
                      licznik: Integer) {
    var powiazaniaObiektu: mutable.HashMap[Any, ObjectPlusPlus] = null
    if (licznik < 1) return
    if (powiazania.contains(nazwaRoli)) {
      //pobierz te powiazania
      powiazaniaObiektu = powiazania(nazwaRoli)
    } else {
      //brak powiazan dla tej roli=>utworz
      powiazaniaObiektu = mutable.HashMap[Any, ObjectPlusPlus]()
      powiazania.put(nazwaRoli, powiazaniaObiektu)
    }
    if (!powiazaniaObiektu.contains(kwalifikator)) {
      //Dodaj powiazanie dla tego obiektu
      powiazaniaObiektu.put(kwalifikator, obiektDocelowy)
      //dodaj powiwzanie zwrotne
      obiektDocelowy.dodajPowiazanie(odwrotnaNazwaRoli, nazwaRoli, this, this, licznik - 1)
    }
  }

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
  }

  @throws(classOf[Exception])
  def dodajCzesc(
                  nazwaRoli: String,
                  odwrotnaNazwaRoli: String,
                  obiektCzesc: ObjectPlusPlus): Unit = {
    //sprawdz czy ta czesc juz gdzies nie wystepuje
    if (ObjectPlusPlus.wszystkieCzesci.contains(obiektCzesc)) throw new Exception("Ta czesc jest juz powiazana z jakąs caloscia")
    dodajPowiazanie(nazwaRoli, odwrotnaNazwaRoli, obiektCzesc)
    //Zapamietaj dodanie obiektu jako czesci
    ObjectPlusPlus.wszystkieCzesci += (obiektCzesc)
  }

  @throws(classOf[Exception])
  def dajPowiazania(nazwaRoli: String): Array[ObjectPlusPlus] = {
    var powiazaniaObiektu: mutable.HashMap[Any, ObjectPlusPlus] = mutable.HashMap().empty
    if (!powiazania.contains(nazwaRoli)) {
      //brak powiazan dla tej roli
      throw new Exception("Brak powiazan dla roli: " + nazwaRoli)
    }
    powiazaniaObiektu = powiazania(nazwaRoli)

    powiazaniaObiektu.asJava.values.toArray(new Array[ObjectPlusPlus](0))    //- mas06 str 26
  }

  @throws(classOf[Exception])
  def wyswietlpowiazania(nazwaRoli: String, stream: PrintStream): Unit = {
    var powiazaniaObiektu: mutable.HashMap[Any, ObjectPlusPlus] = null
    if (!powiazania.contains(nazwaRoli)) {
      //brak powiazan dla tej roli
      throw new Exception("Brak powiazan dla roli: " + nazwaRoli)
    }
    powiazaniaObiektu = powiazania(nazwaRoli)
    val col: Iterable[ObjectPlusPlus] = powiazaniaObiektu.values
    stream.println(this.getClass.getSimpleName + " powiazania w roli " + nazwaRoli + ": ")
    stream.println(" " + col)

  } //mas06-str26

  @throws(classOf[Exception])
  def wyswietlPowiazania(
                          nazwaRoli: String,
                          stream: PrintStream): Unit = {
    var powiazaniaObiektu: mutable.HashMap[Any, ObjectPlusPlus] = mutable.HashMap.empty
    if (!powiazania.contains(nazwaRoli)) {
      //brak powiazan dla tej roli
      throw new Exception("Brak powiazan dla roli: " + nazwaRoli)
    }
    powiazaniaObiektu = powiazania(nazwaRoli)
    val col: Iterable[ObjectPlusPlus] = powiazaniaObiektu.values
    stream.println(this.getClass.getSimpleName + " powiazania w roli " + nazwaRoli + ":")
    stream.println(" " + col)
  }

  @throws(classOf[Exception])
  def dajPowiazanyObiekt(
                          nazwaRoli: String,
                          kwalifikator: Any): ObjectPlusPlus = {

    var powiazaniaObiektu: mutable.HashMap[Any, ObjectPlusPlus] = null
    if (!powiazania.contains(nazwaRoli)) {
      //brak powiazan dla tej roli
      throw new Exception("Brak powiazan dla roli: " + nazwaRoli)
    }
    powiazaniaObiektu = powiazania(nazwaRoli)
    if (!powiazaniaObiektu.contains(kwalifikator)) {
      //brak powiazan dla tej roli
      throw new Exception("Brak powiazania dla kwalifikatora " + kwalifikator)
    }
    powiazaniaObiektu.get(kwalifikator).asInstanceOf[ObjectPlusPlus]
  }

  /** Wywołanie super jest automatyczne. */
  private def dodajPowiazanie(
                               nazwaRoli: String,
                               odwrotnaNazwaRoli: String,
                               obiektDocelowy: ObjectPlusPlus,
                               kwalifikator: Any,
                               licznik: Int): Unit = {
    var powiazaniaObiektu: mutable.HashMap[Any, ObjectPlusPlus] = mutable.HashMap.empty

    if (licznik < 1) return
    if (powiazania.contains(nazwaRoli)) {
      //pobierz te powiazania
      powiazaniaObiektu = powiazania(nazwaRoli)
    } else {
      //brak powiazan dla takiej roli=>utworz
      powiazaniaObiektu = mutable.HashMap()
      powiazania.put(nazwaRoli, powiazaniaObiektu)
    }

    if (powiazaniaObiektu.contains(kwalifikator)) {
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