import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.*;

public class DzialkaRolnaTest {
    Roslina roslina1, roslina2;
    DzialkaRolna dzialkaRolna2, dzialkaRolna1;
    DzialkaEwidencyjna dzialkaEwidencyjna1, dzialkaEwidencyjna2;
    Gospodarstwo gospodarstwo;

    @Before
    public void setUp() throws Exception {

        roslina1 = new Roslina("rzepak", Enumy.DWULISCIENNE());
        roslina2=new Roslina("pszenica",Enumy.JEDNOLISCIENNE());
        dzialkaRolna1 = new DzialkaRolna(10, "A", roslina1);
        dzialkaEwidencyjna1 = new DzialkaEwidencyjna(8, "100/1");
        dzialkaEwidencyjna2 = new DzialkaEwidencyjna(9, "100/2");
        dzialkaRolna2 = new DzialkaRolna(3, "B", roslina2);
      gospodarstwo = new Gospodarstwo(new Adres("Mikolaja", "15", "Siedlce"), 123);

    }


    @Test
    public void getSypnieteNPK() {

        assertEquals(dzialkaRolna1.getSypnieteNPK()._1(),0);
        assertEquals(dzialkaRolna1.getSypnieteNPK()._2(),0);
        assertEquals(dzialkaRolna1.getSypnieteNPK()._3(),0);

        dzialkaRolna1.setSypnieteNPK(10,20,30);

        assertEquals(dzialkaRolna1.getSypnieteNPK()._1(),10);
        assertEquals(dzialkaRolna1.getSypnieteNPK()._2(),20);
        assertEquals(dzialkaRolna1.getSypnieteNPK()._3(),30);
    }

    @Test
    public void dodajDzialkeEwidencyjna() {
       DzialkaRolna dzialkaRolna3=new DzialkaRolna(2,"C",new Roslina("kukurydza",Enumy.JEDNOLISCIENNE()));
       dzialkaRolna3.dodajDzialkeEwidencyjna(dzialkaEwidencyjna2,3);
       assertTrue(dzialkaRolna3)
    }
}