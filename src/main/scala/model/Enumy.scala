package model

object Enumy extends Enumeration {

  //przyklad ENUM
  type SystematykaBiologiczna = Value
  val jednoliscienne=Value("jednosliscienne");
  val dwuliscienne= Value("dwuliścienne");
  val wieloletnie=Value("wieloletnie");
  val owady,grzyby=Value

  type powodZabiegu=Value
  val poprawaWarunkowWzrostu,zwalczenieSzkodnikow=Value

  type ZabiegiNaRosliny = Value
  val
  nawozenieAzotem,
  nawozenieNPK,
  opryskNaChwastyJednoliscienne,
  opryskNaChwastyDwuliscienne,
  opryskNaInsekty,
  opryskNaGrzyby = Value

}
