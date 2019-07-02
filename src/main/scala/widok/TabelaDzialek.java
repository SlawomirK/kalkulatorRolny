package widok;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import scala.Enumeration;
import scala.Option;
import scala.Some;

import java.util.*;
import java.util.stream.Collectors;

public class TabelaDzialek {
    private Stage window;
    Scene scene, scenaMenu;
    private TableView<KlasaZbierajacaDoTabeliDzialek> tabela;
    private TextField polePowierzchniRolna, polePowierzchniEwidencyjne, poleOznaczenieRolnej;
    private ListView<String> poleOznaczenieEwidencyjne;
    private ComboBox<String> poleRoslinaNazwa;
    private TableColumn<KlasaZbierajacaDoTabeliDzialek, ComboBox<String>> ewidencyjneKolumna;
    private ObjectPlus refDoEkstencji;
    private Button dodajDzialkeButton;
    public Map<String, List<String>> klasyfikacjaRoslinWgTEgoCZyOZime;
    private List<String> list;
    private ObservableList<KlasaZbierajacaDoTabeliDzialek> dzialki;
    private boolean zapisano = false;

    public TabelaDzialek(Stage primaryStage) {

        window = primaryStage;
        window.setMaximized(true);
        window.setTitle("Działki rolne");
        stworzComboNazwaRosliny();
        stworzListeDzialkiEwidencyjne();
        //kolumna oznaczenia działki
        TableColumn<KlasaZbierajacaDoTabeliDzialek, String> oznaczenieKolumna = new TableColumn<>("OZNACZENIE ROLNEJ");
        oznaczenieKolumna.setMinWidth(oznaczenieKolumna.getText().length());
        oznaczenieKolumna.setCellValueFactory(new PropertyValueFactory<>("oznaczenieDzialkiRolnej"));

        //kolumna powierzchni
        TableColumn<KlasaZbierajacaDoTabeliDzialek, Double> powierzchniaKolumna = new TableColumn<>("POWIERZCHNIA ROLNEJ");
        powierzchniaKolumna.setMinWidth(powierzchniaKolumna.getText().length());
        powierzchniaKolumna.setCellValueFactory(new PropertyValueFactory<>("powierzchniaDzialkiRolnej"));

        //roslina
        TableColumn<KlasaZbierajacaDoTabeliDzialek, String> uprawa = new TableColumn<>("UPRAWA");
        uprawa.setMinWidth(140);
        uprawa.setCellValueFactory(new PropertyValueFactory<>("nazwaRosliny"));


        stworzComboKolumnaEwidencyjne();
        //Button
        dodajDzialkeButton = new Button("Dodaj działkę");
        dodajDzialkeButton.setOnAction(e -> dodajDzialkeButton());
        dodajDzialkeButton.autosize();

        Button usunDzialkeButton = new Button("Usuń działkę");
        usunDzialkeButton.setOnAction(e -> {
            ObservableList<KlasaZbierajacaDoTabeliDzialek> wybrane, wszystkie;
            wszystkie = tabela.getItems();
            wybrane = tabela.getSelectionModel().getSelectedItems();
            wybrane.forEach(wszystkie::remove);
            dzialki = wszystkie;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("dddll " + dzialki);

        });

        Button anulujButton = new Button("Wróć do głównego");
        anulujButton.setCancelButton(true);
        anulujButton.setOnAction(e -> anulujButton());

        Button butt_zatwierdz = new Button("Zapisz zmiany");
        butt_zatwierdz.setOnAction(e -> zatwierdzZmiany());


        //  usunDzialkeButton.setOnAction(e -> usunDzialkeButton());

        poleOznaczenieRolnej = new TextField();
        poleOznaczenieRolnej.setPromptText("Oznaczenie rolnej");
        poleOznaczenieRolnej.setMinWidth(80);


        polePowierzchniRolna = new TextField();
        polePowierzchniRolna.setPromptText("powierzchnia rolnej");
        polePowierzchniRolna.setMinWidth(50);

        polePowierzchniEwidencyjne = new TextField();
        polePowierzchniEwidencyjne.setPromptText("powierzchnia ewidencyjnej");
        polePowierzchniEwidencyjne.setMinWidth(50);

        tabela = new TableView<>();
        dzialki = pobierzDzialki();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabela.setItems(dzialki);

        tabela.getColumns().addAll(
                oznaczenieKolumna,
                powierzchniaKolumna,
                uprawa
                , ewidencyjneKolumna
        );


        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(3, 3, 3, 3));
        hbox1.autosize();
        hbox1.setSpacing(5);
        Region region1 = new Region();
        Region region2 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox hBox2 = new HBox(butt_zatwierdz, region1, anulujButton);
        hbox1.getChildren().addAll(
                poleOznaczenieRolnej,
                poleRoslinaNazwa,
                polePowierzchniRolna,
                poleOznaczenieEwidencyjne,
                dodajDzialkeButton,
                usunDzialkeButton
        );

        hbox1.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(tabela, hbox1, hBox2);
        window.setMinWidth(800);
        scene = new Scene(layout, window.getWidth(), window.getHeight());

        window.setScene(scene);
        window.show();
    }

    private void zatwierdzZmiany() {
        for (KlasaZbierajacaDoTabeliDzialek d : dzialki) {
            new DzialkaRolna(d.getPowierzchniaDzialkiRolnej(), d.getOznaczenieDzialkiRolnej(),
                    new Roslina(d.getNazwaRosliny(), pobierzGrupe(d.getNazwaRosliny()), czyOzime(d.getNazwaRosliny())));
        }
        ObjectPlus.zapiszEkstensje("Ekstensja.bin");//narazie miejsce zapisu domyślne, w przyszłości być może możliwość wyboru
        zapisano = true;
    }

    private void anulujButton() {
        if (zapisano == false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Nie zapisano efektu pracy");
            alert.setHeaderText("Ten przycisk umożliwia powrót do strony głównej bez zapisu dokonanych zmian");
            alert.setContentText("\"Ok\" zapisuje zmiany przed powrotem \t\t " +
                    "\"Cancel\" wraca bez zapisu");

            Optional<ButtonType> decyzja = alert.showAndWait();
            if (decyzja.get() == ButtonType.OK) {
                zatwierdzZmiany();
                zapisano = true;
            }

        }

        window.setTitle("Podręczny agronom");
        window.setMaximized(true);
        this.window.setScene(scenaMenu);
    }

    private void stworzComboKolumnaEwidencyjne() {
        ewidencyjneKolumna = new TableColumn<>("DZIAŁKI EWIDENCYJNE");
        ewidencyjneKolumna.setCellValueFactory(new PropertyValueFactory<KlasaZbierajacaDoTabeliDzialek, ComboBox<String>>("dzialkiEwidencyjne"));
        ewidencyjneKolumna.setMinWidth(ewidencyjneKolumna.getText().length());


    }

    public Scene pobierzWidokTabelaDzialek(Scene scenaMenu) {
        this.scenaMenu = scenaMenu;
        return this.scene;
    }

    private void dodajDzialkeButton() {
        if (sprawdzPoprawnoscWprowadzonychDanych()) {
            Roslina ros = new Roslina(poleRoslinaNazwa.getValue(),
                    pobierzGrupe(poleRoslinaNazwa.getValue()),//czy jednoliscienna
                    czyOzime(poleRoslinaNazwa.getValue()));

            DzialkaRolna dzialkaRolna = new DzialkaRolna(
                    Double.parseDouble(polePowierzchniRolna.getText()),
                    poleOznaczenieRolnej.getText(), ros);

            KlasaZbierajacaDoTabeliDzialek doTab = new KlasaZbierajacaDoTabeliDzialek(ros.nazwa(), dzialkaRolna.getOznaczenie(), dzialkaRolna.getPowierzchnia(), dodajDzialkeEwidencyjna(dzialkaRolna));

            tabela.getItems().add(doTab);
            zapisano = false;
            poleOznaczenieRolnej.clear();
            polePowierzchniRolna.clear();
            poleOznaczenieEwidencyjne.refresh();

        } else {
            wyswietlBlad("Proszę sprawdzić poprawność wprowadzonych danych");
        }
    }


    private boolean sprawdzPoprawnoscWprowadzonychDanych() {
        if (poleOznaczenieRolnej.getText().isEmpty()) {
            wyswietlBlad("Proszę uzupełnić pole oznaczenia działki rolne");
            poleOznaczenieRolnej.clear();
            return false;
        }
        if (poleRoslinaNazwa.getValue().isEmpty()) {
            wyswietlBlad("Nie okreslono nazwy uprawy");
            return false;
        }
        if (poleOznaczenieEwidencyjne.getItems().isEmpty()) {
            wyswietlBlad("Nie okreslono oznaczenia dzialki ewidencyjnej");
            return false;
        }
        if (polePowierzchniRolna.getText().isEmpty() ||
                sprawdzCzyDouble(polePowierzchniRolna.getText()) == false) {
            wyswietlBlad("Niewłaściwa wartość w oknie powierzchnia działek rolnych");
            polePowierzchniRolna.clear();
            return false;
        }

        return true;
    }

    private boolean sprawdzCzyDouble(String value) {

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private List<String> dodajDzialkeEwidencyjna(Dzialka dzialkaRolna) {
        List<String> dzialkiEwidencyjne = poleOznaczenieEwidencyjne.getSelectionModel().getSelectedItems();
//        double powierzchnia = Double.parseDouble(polePowierzchniEwidencyjne.getText());
        //TODO:umozliwic wprowadzenie powierzchniWGranicach jakieś okno lub coś..
        double powierzchniaWGranicach = 1;
        for (String dze : dzialkiEwidencyjne) {
            dzialkaRolna.dodajDzialkiWchodzaceWSkladTejDzialki(
                    new DzialkaEwidencyjna(5, dze),
                    powierzchniaWGranicach);
        }
        return dzialkiEwidencyjne;
    }

    private Option<Enumeration.Value> pobierzGrupe(String value) {
        Option<Enumeration.Value> wynik = null;
        switch (jakiKlucz(value)) {
            case "jednoliscienne_ozime": {
                wynik = new Some<>(Enumy.jednoliscienne());
                break;
            }
            case "jednoliscienne_jare": {
                wynik = new Some(Enumy.jednoliscienne());
                break;
            }
            case "dwuliscienne_ozime": {
                wynik = new Some(Enumy.dwuliscienne());
                break;
            }
            case "dwuliscienne_jare": {
                wynik = new Some(Enumy.dwuliscienne());
                break;
            }
            case "wieloletnie": {
                wynik = new Some(Enumy.wieloletnie());
                break;
            }
            default:
                wyswietlBlad("Błąd w metodzie pobierzGrupe. Definiowanie grupy biologicznej");
        }
        return wynik;
    }

    private void wyswietlBlad(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ostrzeżenie o błędzie");
        alert.setHeaderText(s);
        alert.showAndWait();
    }

    private boolean czyOzime(String value) {
        if (jakiKlucz(value).contains("jare")) return false;
        else return true;
    }

    private String jakiKlucz(String val) {
        String key = "";
        for (Map.Entry<String, List<String>> entry : klasyfikacjaRoslinWgTEgoCZyOZime.entrySet()) {
            key = entry.getKey();
            List value = entry.getValue();
            if (value.contains(val))
                break;
        }
        return key;
    }

    /*Tutaj zdefiniowane są działki rolne wyswietlane w oknie tabeli*/
    public ObservableList<KlasaZbierajacaDoTabeliDzialek> pobierzDzialki() {
        dzialki = FXCollections.observableArrayList();
        dzialki.add(new KlasaZbierajacaDoTabeliDzialek("Pszenica Ozima", "A", 2.3, Arrays.asList("1/1", "12A")));
        dzialki.add(new KlasaZbierajacaDoTabeliDzialek("Pszenżyto", "B", 2.6, Arrays.asList("2/m")));
        return dzialki;
    }

    //TODO: Do pełnego zdefiniowania potrzebne bedzie zaprogramowanie funkcjonalności defiowania działek eewidencyjnych gospodarstwa. Tutaj uproszczenie
    private List<DzialkaEwidencyjna> dzialkiEwidencyjneZdefiniowane() {
        List<DzialkaEwidencyjna> dzialkiWGospodarstwie = Arrays.asList(
                new DzialkaEwidencyjna(2.1, "12A"),
                new DzialkaEwidencyjna(2.0, "1/1"),
                new DzialkaEwidencyjna(3.3, "12/1"),
                new DzialkaEwidencyjna(4.2, "2/m"));
        return dzialkiWGospodarstwie;
    }

    private void stworzListeDzialkiEwidencyjne() {
        ObservableList<String> oznaczenia = FXCollections.observableArrayList(pobierzDzialki().stream().map(s -> s.getOznaczenieDzialkiRolnej()).collect(Collectors.toSet()));
        poleOznaczenieEwidencyjne = new ListView<String>(oznaczenia);
        poleOznaczenieEwidencyjne.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        poleOznaczenieEwidencyjne.setOrientation(Orientation.VERTICAL);

        poleOznaczenieEwidencyjne.setMinSize(50, 50);
        // poleOznaczenieEwidencyjne.setMinHeight(40);
        poleOznaczenieEwidencyjne.setPrefSize(70, 60);
        poleOznaczenieEwidencyjne.getItems().addAll(dzialkiEwidencyjneZdefiniowane().stream().map(s -> s.getOznaczenie()).collect(Collectors.toList()));
    }

    public void stworzComboNazwaRosliny() {

        poleRoslinaNazwa = new ComboBox<>();
        poleRoslinaNazwa.setPromptText("Roślina");
        poleRoslinaNazwa.setMinWidth(100);
        List<String> jednoliscienne_ozime = Arrays.asList("Pszenica Ozima", "Jęczmień ozimy", "Żyto", "Pszenżyto");
        List<String> jednoliscienne_Jare = Arrays.asList("Jęczmien", "owies", "pszenica", "kukurydza");
        List<String> dwuliscienne_ozime = Arrays.asList("Rzepak");
        List<String> dwuliscienne_jare = Arrays.asList("Rzepik", "Buraki cukrowe", "warzywa");
        List<String> wieloletnie = Arrays.asList("Jabłonie", "Porzeczki", "Truskawki", "Wiśnie", "Porzeczki");
        klasyfikacjaRoslinWgTEgoCZyOZime = new HashMap<>();

        klasyfikacjaRoslinWgTEgoCZyOZime.put("jednoliscienne_ozime", jednoliscienne_ozime);
        klasyfikacjaRoslinWgTEgoCZyOZime.put("jednoliscienne_jare", jednoliscienne_Jare);
        klasyfikacjaRoslinWgTEgoCZyOZime.put("dwuliscienne_ozime", dwuliscienne_ozime);
        klasyfikacjaRoslinWgTEgoCZyOZime.put("dwuliscienne_jare", dwuliscienne_jare);
        klasyfikacjaRoslinWgTEgoCZyOZime.put("wieloletnie", wieloletnie);
        list = new ArrayList<>();
        //TODO:w przyszłości należy pamiętać o dodaniu funkji tworzenia nowych obiektów typu Roslina
        poleRoslinaNazwa.setMinWidth(100);

        for (Map.Entry<String, List<String>> entry : klasyfikacjaRoslinWgTEgoCZyOZime.entrySet()) {
            list.addAll(entry.getValue());
        }
        System.out.println(list);
        poleRoslinaNazwa.getItems().setAll(list);

    }


}

