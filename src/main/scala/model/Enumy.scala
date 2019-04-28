package model

object Enumy extends Enumeration {
  //ATRYBUT KLASOWY Powtarzalny
  //przyklad ENUM

  type SzkodnikiIPatogeny = Value
  type SystematykaUpraw = Value
  type ZabiegiNaRosliny = Value
  val
  grzyby,
  owady,
  chwasty,
  chwastyJednoliscienne,
  chwastyDwuliscienne = Value
  val
  JEDNOLISCIENNE = Value
  val DWULISCIENNE = Value
  val
  nawozenieAzotem,
  nawozenieNPK,
  opryskNaChwastyJednoliscienne,
  opryskNaChwastyDwuliscienne,
  opryskNaInsekty,
  opryskNaGrzyby = Value

}
