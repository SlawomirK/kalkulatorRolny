import model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DzialkaRolnaTest {

       private static Roslina roslina1, roslina2;
       private static DzialkaRolna dzialkaRolna2, dzialkaRolna1;
        private static DzialkaEwidencyjna dzialkaEwidencyjna1, dzialkaEwidencyjna2;
       private static Gospodarstwo gospodarstwo;
       private static SrednieNPK srednieNPK;

    @BeforeClass
    public static void setUp() throws Exception {

        roslina1 = new Roslina("rzepak", Enumy.DWULISCIENNE());
        roslina2 = new Roslina("pszenica", Enumy.JEDNOLISCIENNE());
        dzialkaRolna1 = new DzialkaRolna(1, "A", roslina1);
        dzialkaEwidencyjna1 = new DzialkaEwidencyjna(8, "100/1");
        dzialkaEwidencyjna2 = new DzialkaEwidencyjna(9, "100/2");
        dzialkaRolna2 = new DzialkaRolna(1, "B", roslina2);
        gospodarstwo = new Gospodarstwo(new Adres("Mikolaja", "15", "Siedlce"), 123);
        srednieNPK = new SrednieNPK();
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
        DzialkaRolna dzialkaRolna3 = new DzialkaRolna(8, "C", new Roslina("kukurydza", Enumy.JEDNOLISCIENNE()));
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

    @Test
    public void asocjacjaZAtrybutem() {

    }
}