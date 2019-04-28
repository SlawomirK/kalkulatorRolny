package model

import java.io.{ObjectOutputStream, _}

class DzialkiEkstensje extends Serializable {
  private var ekstensja = Vector.empty[Dzialka]

  def zapiszEkstensje(stream: ObjectOutputStream) = {
    try {
      stream.writeObject(ekstensja)
      stream.flush()
      println("Zapisano ekstensje prawidlowo")
    }
    catch {
      case ioe: IOException => println(ioe)
      case e: Exception => println(e)
    }
    finally {
      stream.close()
    }
  }

  def odczytajEkstensje(stream: ObjectInputStream): Unit = {
    try {
      ekstensja = stream.readObject.asInstanceOf[Vector[Dzialka]]
      println("Wczytano dzialki")
      pokazDzialki
    }
    catch {
      case fnf: FileNotFoundException => println(fnf)
      case ioe: IOException => println(ioe)
      case e: Exception => println(e)
    }
    finally {
      stream.close()
    }
  }

  def dodajDzialke(nowaDzialka: Dzialka): Unit = {
    ekstensja = ekstensja :+ nowaDzialka
  }

  def usunDzialke(zbednaDzialka: Dzialka): Unit = {
    ekstensja = ekstensja filterNot zbednaDzialka.==
  }

  def pokazDzialki: Unit = {
    println("Ekstensja klasy Dzialka")
    ekstensja.foreach(dzialka => println(dzialka))
  }

  def getWszystkieDzialki: Vector[Dzialka] = ekstensja
}
