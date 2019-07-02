import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import widok.TabelaDzialek;

public class testGUI {
    private TabelaDzialek tabelaDzialek;
    @Before
    public void setUp(){
        Stage primaryStage=new Stage();
        tabelaDzialek=new TabelaDzialek(primaryStage);
        tabelaDzialek.stworzComboNazwaRosliny();
    }
    @Test
    public void test_pobierzDzialki(){

System.out.println("Dzialki:"+tabelaDzialek.pobierzDzialki());
    }
    @Test
    public void test_KlasyfikacjiRoslin(){
        System.out.println(
        tabelaDzialek.klasyfikacjaRoslinWgTEgoCZyOZime);
    }
    @Test
    public void test_klasyfikacjaRoslinWgTegCZyOZime(){

        tabelaDzialek.pobierzDzialki();
    }
}
