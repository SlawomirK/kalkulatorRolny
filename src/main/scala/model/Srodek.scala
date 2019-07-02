package model
import java.time.LocalDate

import scala.collection.immutable.Map
/**Klasa reprezentujaca srodek chemiczny użyty do zabiegu.Asocjacja
  * asocjacja kwalifikowana*/
case class Srodek(_nazwaSrodka:String,
             sklad:Set[String],
             dataZakupu:LocalDate,
             dataWaznosci:LocalDate,
            )extends ObjectPlusPlus {

  val nazwaSrodka=_nazwaSrodka

}
