object Enumy extends Enumeration {
  //ATRYBUT KLASOWY Powtarzalny
  //przyklad ENUM
  type SzkodnikiIPatogeny = Value
  val
  grzyby,
  owady,
  chwasty,
  chwastyJednoliscienne,
  chwastyDwuliscienne = Value

  type SystematykaUpraw = Value
  val
  JEDNOLISCIENNE,
  DWULISCIENNE = Value

  type ZabiegiNaRosliny = Value
  val
  nawozenieAzotem,
  nawozenieNPK,
  opryskNaChwastyJednoliscienne,
  opryskNaChwastyDwuliscienne,
  opryskNaInsekty,
  opryskNaGrzyby = Value

}
