package model

import java.io._
import java.util

import scala.collection.immutable.Vector
import scala.collection.mutable.HashMap
import scala.io.Source

/* Klasa uniwersalna zarzadzajaca ekstensajami oraz utrwalaniem-serializacja */
object ObjectPlus {

  private var ekstensje = new util.Hashtable[Class[_ <: ObjectPlus],util.Vector[ObjectPlus]]()

def usunEkstensje(obiektDoUsuniecia:ObjectPlus)={
  require(ekstensje.containsKey(obiektDoUsuniecia.klasa),throw new Exception("brak klasy obiektu do usuniecia"))
  require(ekstensje.get(obiektDoUsuniecia.klasa).contains(obiektDoUsuniecia),throw new Exception("brak obiektu do usuniecia"))
  ekstensje.get(obiektDoUsuniecia.klasa).remove(obiektDoUsuniecia)
}
  def pokazEkstensje(klasa: Class[_ <: ObjectPlus]): Unit = {
    var ekstensja:util.Vector[_ <: ObjectPlus]=null

    if (ekstensje.containsKey(klasa)) {
      //ekstensja tej klasy istnieja w klekcji ekstensji
      import java.util
      ekstensja = ekstensje.get(klasa).asInstanceOf[util.Vector[_ <: ObjectPlus]]
    } else
      throw new Exception("Nieznana klasa " + klasa)

    println("Ekstensja klas: " + klasa.getSimpleName)
    println("EKSTENSJA " + ekstensja)
  }

  //-------------------------------------

  def zapiszEkstensje(plikZapisu: String = "Ekstensja.bin") = {
    var out: ObjectOutputStream = null
    try {
      out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(plikZapisu)))
      out.writeObject(ekstensje)
      println("Zapisano ekstensje prawidlowo w " + plikZapisu)
    } catch {
      case ioe: IOException => println(ioe)
      case e: Exception => println(e)
    } finally {
      out.close()
    }
  }

  def odczytajEkstensje(plikOdczytu: String = "Ekstensja.bin"): Unit = {
    import java.io.{BufferedInputStream, FileInputStream}
    var in: ObjectInputStream = null
    try {
      in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(plikOdczytu)))
      ekstensje = in.readObject().asInstanceOf[util.Hashtable[Class[_ <: ObjectPlus],util.Vector[ObjectPlus]]]

      println("Wczytano dzialki bez bledu")
    } catch {
      case fnf: FileNotFoundException => println("Blad 1" + fnf)
      case ioe: IOException => println("Blad 2" + ioe.getMessage)
      case eof: EOFException => println("Blad 3" + eof.getMessage)
      case e: Exception => println("Blad 4" + e.getLocalizedMessage)
    } finally {
      in.close()
    }
  }
}

//*****************************
class ObjectPlus extends Serializable {

  import ObjectPlus.ekstensje
  var ekstensja:util.Vector[ObjectPlus]=null

  val klasa = this.getClass

  if (ekstensje.containsKey(klasa)) {
    import java.util
    ekstensja = ekstensje.get(klasa).asInstanceOf[util.Vector[ObjectPlus]]

  } else {

    ekstensja = new util.Vector[ ObjectPlus]()
    ekstensje.put(klasa,ekstensja)
  }
  println("dodaje " + this)
  ekstensja.add(this)
}