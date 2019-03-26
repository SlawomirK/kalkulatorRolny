import java.io.{FileNotFoundException, IOException, ObjectInputStream, ObjectOutputStream}

import scala.collection.immutable.HashMap

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
    println(ekstensja)
  }
}

class ObjectPlus extends Serializable {

  import ObjectPlus.ekstensje

  var ekstensja: Vector[_ <: ObjectPlus] = Vector.empty
  val klasa = this.getClass

  if (ekstensje.contains(klasa)) {
    ekstensja = ekstensje(klasa)
  } else {
    ekstensja = Vector[ObjectPlus]()
    ekstensje += klasa -> ekstensja
  }
  println("dodaje " + this)
  ekstensja :+ this

  //-------------------------------------
  def zapiszEkstensje(stream: ObjectOutputStream) = {
    try {
      stream.writeObject(ekstensje)
      stream.flush()
      println("Zapisano ekstensje prawidlowo")
    } catch {
      case ioe: IOException => println(ioe)
      case e: Exception => println(e)
    } finally {
      stream.close()
    }
  }

  def odczytajEkstensje(stream: ObjectInputStream): Unit = {
    try {
      ekstensje = stream.readObject.asInstanceOf[HashMap[Class[_ <: ObjectPlus], Vector[ObjectPlus]]];
      println("Wczytano dzialki")
    } catch {
      case fnf: FileNotFoundException => println(fnf)
      case ioe: IOException => println(ioe)
      case e: Exception => println(e)
    } finally {
      stream.close()
    }
  }
}