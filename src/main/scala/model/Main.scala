package main.scala.model

import java.util.Scanner

import model._

object Main extends App {
  val in_ = new Scanner(System.in)

  val rzepak = new Roslina("rzepak", Enumy.DWULISCIENNE)
  val pszenica = new Roslina("pszenica", Enumy.JEDNOLISCIENNE)
  val gospodarstwo = new Gospodarstwo(new Adres("Mikolaja", "15", "Siedlce"), 123);

  /*
  val dzialkaRolna1 = new DzialkaRolna(10, "A", roslina1);
  val dzialkaEwidencyjna1 = new DzialkaEwidencyjna(8, "100/1");
  val dzialkaEwidencyjna2 = new DzialkaEwidencyjna(9, "100/2");
  val dzialkaRolna2 = new DzialkaRolna(3, "B", roslina2);

  //---------------------------------
  ObjectPlus.pokazEkstensje(roslina1.klasa)
  println(ObjectPlus.ekstensje + "\n")
  dzialkaEwidencyjna1.dodajDzialkiWchodzaceWSkladTejDzialki(dzialkaRolna2,2)
  // ObjectPlus.pokazEkstensje(dzialkaRolna2.klasa)
  // ObjectPlus.pokazEkstensje(roslina1.klasa)

*/
  var dzialkaRolna: DzialkaRolna = null
  var dzialkaEwidencyjna: DzialkaEwidencyjna = null
  var wybor: Int = menuGlowne()

  def menuGlowne(): Int = {

    println()
    println("****************************************************")
    println("*****************     MENU     *********************")
    println("****************************************************")
    println("*  1.    Dodaj dzialke rolna                       *")
    println("*  2.    Dodaj dziale ewidencyjna                  *")
    println("*  3.    Zapisz zmiany                             *")
    println("*  4.    Wczytaj ekstensje                         *")
    println("*  0.    Zakoncz                                   *")
    println("****************************************************")
    print("Twoj Wybor?: \t")

    val w = in_.nextInt()
    w
  }

  def dodajDzialkeRolnaMenu(): Unit = {
    println("Podaj powierzchnie")
    val powierzchnia = in_.nextInt
    println("podaj oznaczenie")
    val oznaczenie = in_.next()
    println("wybierz Rosline")
    println("1. pszenica")
    println("2. rzepak")
    var roslinaNr: Int = in_.nextInt

    roslinaNr match {
      case 1 => dzialkaRolna = new DzialkaRolna(powierzchnia, oznaczenie, pszenica)
      case 2 => dzialkaRolna = new DzialkaRolna(powierzchnia, oznaczenie, rzepak)
      case _ => dzialkaRolna = new DzialkaRolna(powierzchnia, oznaczenie, pszenica) //w przypadku blednego wyboru uzytkownika wpisane bedzie to
      // zamiast ponownego wyboru lub rzucania wyjatkiem. Na tym etapie nie ma sensu robic listy roslin uprawnych.
    }

  }

  def dodajDzialkeEwidencyjnaMenu = {
    println("podaj powierzchnie")
    val powierzchnia = in_.nextDouble()
    println("podaj oznaczenie")
    val oznaczenie = in_.next()
    dzialkaEwidencyjna = new DzialkaEwidencyjna(powierzchnia, oznaczenie)
  }

  def zapiszEkstensje = {
    println("ekstensje zostana zapisane w lokalizacji \"Ekstensja.bin\"")
    ObjectPlus.zapiszEkstensje()
  }

  while (wybor != 0) {
    wybor match {
      case 1 => dodajDzialkeRolnaMenu()
      case 2 => dodajDzialkeEwidencyjnaMenu
      case 3 => zapiszEkstensje
      case 4 => ObjectPlus.odczytajEkstensje()
      case 0 => {
        println("koniec programu")
        System.exit(0)
      }
      case _ => println("Niepoprawna Wartosc. Sprobuj jeszcze raz")
    }
    println("Wcisnij Enter aby kontynuowac")
    System.in.read()
    wybor = menuGlowne
  }

  println("   Koniec programu")
  in_.close()
}