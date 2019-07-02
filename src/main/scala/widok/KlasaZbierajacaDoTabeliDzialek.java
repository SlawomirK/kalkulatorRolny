package widok;

import javafx.scene.control.ComboBox;

import java.util.List;

public class KlasaZbierajacaDoTabeliDzialek {

    private String nazwaRosliny;
    private String oznaczenieDzialkiRolnej;
    private double powierzchniaDzialkiRolnej;
    private ComboBox<String> dzialkiEwidencyjne;//dzialkaEwidencyjna+powierzchnia

    public KlasaZbierajacaDoTabeliDzialek(String nazwaRosliny,String oznaczenieDzialkiRolnej,double powierzchniaDzialkiRolnej,List<String> dzialkiEwidencyjne){
        this.nazwaRosliny=nazwaRosliny;
        this.oznaczenieDzialkiRolnej=oznaczenieDzialkiRolnej;
        this.powierzchniaDzialkiRolnej=powierzchniaDzialkiRolnej;
        this.dzialkiEwidencyjne=new ComboBox<>();
        this.dzialkiEwidencyjne.getItems().addAll(dzialkiEwidencyjne);
        this.dzialkiEwidencyjne.setMinWidth(100);
        this.dzialkiEwidencyjne.setPromptText("Leży na działkach");
        this.dzialkiEwidencyjne.setEditable(false);
    }

    public String getNazwaRosliny() {
        return nazwaRosliny;
    }

    public String getOznaczenieDzialkiRolnej() {
        return oznaczenieDzialkiRolnej;
    }

    public double getPowierzchniaDzialkiRolnej() {
        return powierzchniaDzialkiRolnej;
    }

    public ComboBox<String> getDzialkiEwidencyjne() {
        return dzialkiEwidencyjne;
    }
}
