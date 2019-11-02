import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import widok.TabelaDzialek;

import java.util.Optional;


public class Main extends Application {
    Stage window;
    BorderPane layout;
    Scene scenaMenu;

    public static void main(String[] args) {
        launch(args);
    }

   
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Podręczny agronom");
window.setMaximized(true);

        Menu menu_zabiegiWykonane = new Menu("WYKONANE ZABIEGI");
        MenuItem newFile = new MenuItem("... ochrony");//TODO: okno w którym istnieje możliwość określenia sposobu filtrowania zabiegów np wg dat, gatunków itd..
        menu_zabiegiWykonane.getItems().add(newFile);
        menu_zabiegiWykonane.getItems().add(new MenuItem("...nawożenia"));//TODO: okno w którym będą wyświetlone informacjie o przeprowadzonych do tej pory nawożeniu, ile brakuje do założonego limitu
        menu_zabiegiWykonane.getItems().add(new SeparatorMenuItem());
        menu_zabiegiWykonane.getItems().add(new MenuItem("Bilans finansowy"));//TODO: wyswietla się okno z informacją o poniesionych kosztach z podziałem na poszczególne uprawy

        Menu menu_zabiegiPlanowane = new Menu("ZAPLANUJ ZABIEG");
        menu_zabiegiPlanowane.getItems().add(new MenuItem("..nawożenia"));
        menu_zabiegiPlanowane.getItems().add(new MenuItem("..ochrony"));//TODO:po wybraniu zabiegów z listy pojawi sie sugerowany harmonogram wraz z sugerowanymi środkami ochrony brane pod uwagę będzie także alert o progu szkodliwości

        Menu menu_ustawienia = new Menu("USTAWIENIA");
        menu_ustawienia.getItems().add(new MenuItem("Powiadomienia"));//TODO: opcja umożliwiająca ustawienie powiadomień automatycznych. Ktoś uprawia pszenicę. Wyświetli mu się inormacja o pszenicy alert o konieczności przeprowadzenia zabiegu itd...
        menu_ustawienia.getItems().add(new SeparatorMenuItem());
        menu_ustawienia.getItems().add(new MenuItem("Informacje o Gospodarstwie"));//TOdO: wyświetla okno na którym są zebrane infomracje o gospodarstwie nr id, areał działki, adres

        menu_ustawienia.getItems().add(new SeparatorMenuItem());
        menu_ustawienia.getItems().add(new MenuItem("Zapisz zmiany"));
        MenuItem zakoncz=new MenuItem("Zakończ");
        zakoncz.setOnAction(s->zakoczenieProgrmu());
        menu_ustawienia.getItems().add(zakoncz);//TODO:Przypomnienie o możliwości zapisu dokonanych zmian(tak zapisze, nie wyjdzie bez zapisu)

        Menu menu_edycja = new Menu("EDYCJA");
        menu_edycja.getItems().add(new MenuItem("Działki ewidencyjne"));//TODO:umożliwia zmiane infomracji o działkach ewidencyjnych
        MenuItem dzRolne=new MenuItem("Działki rolne");
        dzRolne.setOnAction(s->window.setScene(
                new TabelaDzialek(primaryStage).pobierzWidokTabelaDzialek(scenaMenu)
        ));
        menu_edycja.getItems().add(dzRolne);//element demo


        menu_edycja.getItems().add(new SeparatorMenuItem());
        menu_edycja.getItems().add(new MenuItem("Informacje dodatkowe"));//TODO: edycja danych dodatkowych takich jak np uprawnienia chemizacyjne, termin badań technicznych sprzętu

        Menu menu_oProgramie = new Menu("POMOC");
        menu_oProgramie.getItems().add(new MenuItem("Instrukcja"));
        menu_oProgramie.getItems().add(new SeparatorMenuItem());
        menu_oProgramie.getItems().add(new MenuItem(("Autor")));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu_ustawienia,menu_edycja, menu_zabiegiWykonane, menu_zabiegiPlanowane,
                 menu_oProgramie);
        menuBar.setPadding(new Insets(20, 20, 30, 20));
        menuBar.autosize();

        layout = new BorderPane();
        layout.setTop(menuBar);

        Text text= new Text("Tutaj wyświetlane będą informacje o brakach oraz artykuły \n"+
                " pobrane z internetu o tematyce dobranej wg zdefiniowanych przez użytkownika upraw");
        text.setTextOrigin(VPos.CENTER);
        layout.setCenter(text);
        scenaMenu = new Scene(layout, menuBar.getMinWidth(), 300);

        window.setScene(scenaMenu);
        window.show();


    }

    private void zakoczenieProgrmu() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Czy napewno chcesz zakończyć pracę?");
        alert.setHeaderText("Potwierdzasz?");

        Optional<ButtonType> decyzja = alert.showAndWait();
        if (decyzja.get() == ButtonType.OK) {
           System.exit(0);
        }
    }

    public Scene pobierzWidokMenu(){
        return scenaMenu;
    }
}
