package model

import java.io._

import scala.collection.immutable.Vector
import scala.collection.mutable.HashMap
import scala.io.Source

/* Klasa uniwersalna zarzadzajaca ekstensajami oraz utrwalaniem-serializacja */
object ObjectPlus {

  var ekstensje = HashMap[Class[_ <: ObjectPlus], Vector[_ <: ObjectPlus]]()


  def pokazEkstensje(klasa: Class[_ <: ObjectPlus]): Unit = {
    var ekstensja: Vector[_ <: ObjectPlus] = null

    if (ekstensje.contains(klasa)) {
      //ekstensja tej klasy istnieja w klekcji ekstensji
      ekstensja = ekstensje(klasa)
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
      ekstensje = in.readObject().asInstanceOf[HashMap[Class[_ <: ObjectPlus], Vector[_ <: ObjectPlus]]]

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

  var ekstensja: Vector[_ <: ObjectPlus] = null
  val klasa = this.getClass

  if (ekstensje.contains(klasa)) {
    ekstensja = ekstensje(klasa)
  } else {
    ekstensja = Vector[ObjectPlus]()
    ekstensje += (klasa -> ekstensja)
  }
  println("dodaje " + this)
  ekstensja.:+(this)
}