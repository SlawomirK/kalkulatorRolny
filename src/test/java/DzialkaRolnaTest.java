import model.*;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.Some;
import scala.collection.immutable.HashSet;
import scala.collection.immutable.Set;

import java.time.LocalDate;
import java.util.Arrays;

import static model.Enumy.*;
import static org.junit.Assert.*;

public class DzialkaRolnaTest {

    private static Organizm roslina1;
    private static Roslina roslina2;
    private static DzialkaRolna dzialkaRolna2, dzialkaRolna1;
    private static DzialkaEwidencyjna dzialkaEwidencyjna1, dzialkaEwidencyjna2;
    private static Gospodarstwo gospodarstwo;
    private static SrednieNPK srednieNPK;
    private static ZabiegPielegnacyjny zabiegPielegnacyjny1;
    private static WykonujacyZabieg wykonujacyZabieg;
    private static ZabiegPielegnacyjny zabiegPielegnayjny2;
    private static Organizm pszenica;

    @BeforeClass
    public static void setUp() throws Exception {

        roslina1 = new Roslina("rzepak", new Some(dwuliscienne()), true);
        roslina2 = new Roslina("pszenica", new Some(Enumy.jednoliscienne()), true);
        pszenica = new Roslina("rzepak", new Some(dwuliscienne()), true);//as zwykla

        dzialkaRolna1 = new DzialkaRolna(
                1,
                "A", (Roslina) roslina1);

        dzialkaEwidencyjna1 = new DzialkaEwidencyjna(
                8,
                "100/1");

        dzialkaEwidencyjna2 = new DzialkaEwidencyjna(
                9,
                "100/2");

        dzialkaRolna2 = new DzialkaRolna(
                1,
                "B",
                roslina2);

        gospodarstwo = new Gospodarstwo(
                new Adres("Mikolaja", "15", "Siedlce"),
                123);

        srednieNPK = new SrednieNPK();

        wykonujacyZabieg = new WykonujacyZabieg(
                "Big",
                "Lebowsky",
                new AdresZamieszkania("Kamianki", "15", "ul Zielna"),
                LocalDate.of(2019, 10, 11));//data waznosci upranien

        zabiegPielegnacyjny1 = new ZabiegPielegnacyjny(1.4,
                opryskNaInsekty(),//powod oprysku
                wykonujacyZabieg,//kto //kto wykonal zabiegwykonal zabieg
                new Srodek("Cyperkill",
                        new Set.Set1(Arrays.asList("skladnik1", "skladnik2")),
                        LocalDate.of(2019, 11, 22),
                        LocalDate.of(2019, 9, 9)),
                pszenica,
                dzialkaRolna1,
                LocalDate.of(2018, 7, 1));//czym

        zabiegPielegnayjny2 = new ZabiegPielegnacyjny(0.9,
                opryskNaGrzyby(),
                wykonujacyZabieg,
                new Srodek("Mocznik",
                        new Set.Set1(Arrays.asList("skladnik1")),
                        LocalDate.of(2018, 7, 12),
                        LocalDate.of(2019, 8, 19)),
                roslina1,
                dzialkaRolna2,
                LocalDate.of(2019, 7, 1));
    }


    @Test
    public void getSypnieteNPK() {

        assertEquals(dzialkaRolna1.getSypnieteNPK()._1(), 0);
        assertEquals(dzialkaRolna1.getSypnieteNPK()._2(), 0);
        assertEquals(dzialkaRolna1.getSypnieteNPK()._3(), 0);

        dzialkaRolna1.setSypnieteNPK(10, 20, 30);

        assertEquals(dzialkaRolna1.getSypnieteNPK()._1(), 10);
        assertEquals(dzialkaRolna1.getSypnieteNPK()._2(), 20);
        assertEquals(dzialkaRolna1.getSypnieteNPK()._3(), 30);
    }

    @Test
    public void dodajDzialkeEwidencyjna() {
        DzialkaRolna dzialkaRolna3 = new DzialkaRolna(8, "C", new Roslina("kukurydza", new Some(Enumy.jednoliscienne()), false));
        dzialkaRolna3.dodajDzialkiWchodzaceWSkladTejDzialki(dzialkaEwidencyjna2, 3);
        assertFalse(dzialkaRolna3.dzialkiWTejDzialce().isEmpty() || dzialkaRolna3.dzialkiWTejDzialce() == null);
        assertTrue(dzialkaRolna3.dzialkiWTejDzialce().contains(dzialkaEwidencyjna2));
        assertTrue(dzialkaEwidencyjna2.dzialkiWTejDzialce().contains(dzialkaRolna3));
        System.out.println("PO DODANIU ");
        ObjectPlus.pokazEkstensje(dzialkaRolna3.getClass());

    }

    @Test
    public void zapiszEkstensje() {
        ObjectPlus.zapiszEkstensje("Ekstensja.bin");
    }

    @Test
    public void wczytajEkstensje() {
        String s = "Ekstensja.bin";
        ObjectPlus.odczytajEkstensje(s);
    }

    @Test
    public void powierzchniaNieuzytkowanaRolniczo() {
        assertEquals(15.0, Gospodarstwo.getPowierzchniaNieuzytkowanaRolniczo(), 0.01);
    }

    @Test
    public void SrednieNPK_ileNPKPotrzebuje() {
        assertEquals(330, srednieNPK.ileNPKpotrzebuje(Gospodarstwo.dzialkiWGospodarstwie())._1());
        assertEquals(170, srednieNPK.ileNPKpotrzebuje(Gospodarstwo.dzialkiWGospodarstwie())._2());
        assertEquals(230, srednieNPK.ileNPKpotrzebuje(Gospodarstwo.dzialkiWGospodarstwie())._3());
    }

    @Test//DLA ULATWIENIA I UNIKNIECIA POWTARZANIA INICJOWANIA OBIEkTOW W TYM SAMYM PLIKU
    public void asocjacjaZAtrybutem() {
//DzialkaRolna-1---*-DzialkaRolna_Zabieg-*----1-ZabiegPielegnacyjny


        DzialkaRolna_Zabieg dzialkaRolna_Zabieg = new DzialkaRolna_Zabieg(LocalDate.of(2019, 04, 22), 1, dzialkaRolna2, zabiegPielegnacyjny1);

        System.out.println();
        try {
            assertTrue(dzialkaRolna1.dajPowiazania(dzialkaRolna_Zabieg.getClass().getSimpleName()).length > 0);

            dzialkaRolna_Zabieg.wyswietlPowiazania(dzialkaRolna1.getClass().getSimpleName(), System.out);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testAsocjacjiZwyklej() {
        roslina1.dodajPowiazanie(dzialkaRolna1.getClass().getSimpleName(), roslina1.getClass().getSimpleName(), dzialkaRolna1);
        roslina1.dodajPowiazanie(dzialkaRolna2.getClass().getSimpleName(), roslina1.getClass().getSimpleName(), dzialkaRolna2);
        System.out.println("Zawartosc Zwyklej//n--------------------");

        try {
            System.out.println("ilosc powieozan " + roslina1.dajPowiazania(dzialkaRolna1.getClass().getSimpleName()).length);
            roslina1.wyswietlPowiazania(dzialkaRolna1.getClass().getSimpleName(), System.out);
            assertTrue(roslina1.dajPowiazania(dzialkaEwidencyjna2.getClass().getSimpleName()).length > 0);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testAsocjacjiKwalifikowanej() {
        dzialkaRolna1.dodajDzialkiWchodzaceWSkladTejDzialki(dzialkaEwidencyjna2, 1);
        dzialkaRolna1.dodajDzialkiWchodzaceWSkladTejDzialki(dzialkaEwidencyjna1, 0.5);//wlasciwe dodanie asocjacji w metodzie dodaj... z uwagi na koniecznosc przeprowadzenia akcji towarzyszacych
        System.out.println("asocjacja z dzialkaRolna1");
        try {
            dzialkaRolna1.wyswietlPowiazania(dzialkaEwidencyjna2.getClass().getSimpleName(), System.out);
            System.out.println(dzialkaEwidencyjna1.dajPowiazanyObiekt(dzialkaEwidencyjna1.getClass().getSimpleName(), dzialkaRolna1.getOznaczenie()));
            assertTrue(dzialkaRolna1.dajPowiazania(dzialkaEwidencyjna2.getClass().getSimpleName()).length > 0);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testKompozycji() {
        Gospodarstwo gospodarstwo1 = new Gospodarstwo(
                new Adres("ul Mikolaja", "100", "Berlin"),
                102);
        try {
            System.out.println("Sprawdzzenie zawartosci----------------------");
            gospodarstwo1.dodajCzesc(dzialkaRolna1.getClass().getSimpleName() + "_czesc",
                    gospodarstwo.getClass().getSimpleName() + "_calosc",
                    dzialkaRolna1);
            gospodarstwo.dodajCzesc(dzialkaRolna1.getClass().getSimpleName() + "_czesc",
                    gospodarstwo.getClass().getSimpleName() + "_calosc",
                    dzialkaRolna1);
            assertTrue((gospodarstwo.dajPowiazania(gospodarstwo.getClass().getSimpleName() + "_calosc")).length > 0);
            gospodarstwo1.wyswietlPowiazania("Gospodarstwo_calosc", System.out);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    //=============##trzeci miniprojekt
    @Test
    public void testKlasyAbstrakcyjnej() {

        pszenica = new Roslina("pszenica", new Some(jednoliscienne()), true);
    }

    @Test
    public void testOverlaping() {
//asocjacja kwalifikowana do srodkaChemicznego
        Set sklad = new Set.Set1("N");
        Srodek srodek = new Srodek("Mocznik", sklad,//do srodka asocjacja kwalifikkowana
                LocalDate.of(2018, 11, 05),
                LocalDate.of(2019, 12, 04));
       ZabiegPielegnacyjny zabiegPielegnacyjny = new ZabiegPielegnacyjny(0.9, Enumy.nawozenieAzotem(), wykonujacyZabieg, srodek, pszenica, dzialkaRolna2, LocalDate.of(2019, 9, 12));
        DzialkaRolna_Zabieg dzialkaRolna_zabieg = new DzialkaRolna_Zabieg(LocalDate.of(2019, 06, 23), 2, dzialkaRolna1, zabiegPielegnacyjny);
        //TODO: powiazania zrobione, zrealizować overlaping- mas7 str 26
        //TODO: nawozenie powinno miec funkcje zwracajaca sklad nawozu, moze enum ktory
        //TODO: bedzie mial z gory zdefioniowane liste nawozow
//Oprysk->Zabieg pielegnacyjny <-nawożenie


    }
    //powod asocjacja do organizmu

    @Test
    public void testWielodziedziczenia() {
        //realizacja za pomocca interfejsu
        ISamosiewy samosiewy = new Roslina("Rzepak", new Some(Enumy.dwuliscienne()), true);
        OrganizmSzkodliwy samosiew2 = new Samosiewy("Żyto", new Some(jednoliscienne()));
        ISamosiewy samosiew3 = new Samosiewy("owies", new Some(jednoliscienne()));

    }

    @Test
    public void dziedziczeniaWieloaspektowego() {
//przeniesienie jednej galezi dziedziczenia do nadklasy
        ZabiegPielegnacyjny zabiegPielegnacyjny = new ZabiegPielegnacyjny(
                2.0,
                Enumy.zwalczenieSzkodnikow(),
                wykonujacyZabieg,
                new Srodek("Topsin",
                        new HashSet.Set1(Arrays.asList("skladnik1", "skladnik2")),
                        LocalDate.of(2017, 9, 12),
                        LocalDate.of(2017, 10, 12)),
                      roslina1,
                        dzialkaRolna1,
                        LocalDate.of(2019, 4, 4));
        zabiegPielegnayjny2.koszt_$eq(150);

    }

    @Test
    public void testDziedziczeniaDynamicznego() {
        System.out.println(roslina1.getClass());
        System.out.println("po zmianie====");
        roslina1.zmienKlase(true);
        System.out.println(roslina1.getClass());
    }
}
