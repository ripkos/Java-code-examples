package sample;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    public TextArea in;
    @FXML
    public Button go;
    @FXML
    public TextArea info;
    @FXML
    public TextArea huff;
    @FXML
    private TableView<HuffmanCode> t;
    @FXML
    private TableColumn<HuffmanCode,String> tcode;
    @FXML
    private TableColumn<HuffmanCode,Double> tchar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcode.setCellValueFactory(new PropertyValueFactory<>("code"));
        tchar.setCellValueFactory(new PropertyValueFactory<>("letter"));
        t.setItems(FXCollections.observableArrayList());
    }
    public void ProceedText() {
        t.getItems().clear();
        //Etap 1 Wczytywanie danych
        String s = in.getText();
        Map<Character, Integer> map = new HashMap<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int count = 1;
            if (map.containsKey(chars[i])) {
                count = map.get(chars[i]) + 1;
                map.remove(chars[i]);
            }
            map.put(chars[i], count);
        }
        //Etap 2 Tworze liste
        LinkedHuffmanList guardian = new LinkedHuffmanList(null);
        map.forEach((key,value) ->{
            guardian.insert(new LinkedHuffmanList(new HuffmanNode(String.valueOf(key),value)));
        });
        //Etap 3 - iteracje
        LinkedHuffmanList node1 = guardian.take();
        LinkedHuffmanList node2 = guardian.take();
        while (node2!=null) {
            guardian.insert(new LinkedHuffmanList(new HuffmanNode(node1.node, node2.node)));
            node1 = guardian.take();
            node2 = guardian.take();
        }
        //Etap 4 - tworzenie kodow
        List<HuffmanCode> codesList = new LinkedList<>();
        Map<String, String> codesMap = new HashMap<>();
        preorder(node1.node,"",codesList, codesMap);
        //Etap 5 - dodatkowe informacje
        int summaryLength=0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            String CodeForCurrentLetter = codesMap.get(String.valueOf(chars[i]));
            builder.append(CodeForCurrentLetter);
            summaryLength+=CodeForCurrentLetter.length();
        }
        int staly = staly_bytes(codesList.size());
        String huffZakodowany = builder.toString();
        String dlugosci = "Staly - " + staly*chars.length + ";\nHuffman - " + summaryLength;
        huff.setText(huffZakodowany);
        info.setText(dlugosci);
        for (HuffmanCode code:
                codesList) {
            t.getItems().add(code);
        }

    }
    //void
    void preorder(HuffmanNode node , String path, List<HuffmanCode> list, Map<String, String> codesMap) {
        if(node.isLeaf()) {
            list.add(new HuffmanCode(path,node.text));
            codesMap.put(node.text,path);
        }
        else {
            if(node.Left!=null){
                preorder(node.Left,path+"0",list,codesMap);
            }
            if(node.Right!=null){
                preorder(node.Right,path+"1",list,codesMap);
            }
        }
    }
    public int staly_bytes(int liba){
        int bity=1;
        int c=2;
        while (c<liba){
            bity++;
            c*=2;
        }
        return bity;
    }
}
