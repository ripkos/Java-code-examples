package zad1;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class CountryTable extends JTable {
    String countriesFileName;

    public CountryTable (String countriesFileName){
        this.countriesFileName=countriesFileName;
    }

    private CountryTable(Object[][]data, String[] columnNames) {
        super(data,columnNames);
    }
    public CountryTable create() throws FileNotFoundException {
        //Changing model
        DefaultTableModel model = null;
        boolean counted= false;
        int columnnum=0;
        int rownum=0;
        String[] columnNames = new String[0];
        Object[][] data = new Object[0][0];
        try{
            BufferedReader buf = new BufferedReader(new FileReader(this.countriesFileName));
            ArrayList<String> words = new ArrayList<>();
            String lineJustFetched = null;
            String[] wordsArray;
            boolean done = false;

            //Wczytanie wierzy

            while(!done) {
                lineJustFetched = buf.readLine();
                if(lineJustFetched == null){
                    done=true;
                }else{
                    wordsArray = lineJustFetched.split("\t");
                    for(String each : wordsArray){
                        if(!"".equals(each)){
                            if(!counted){
                                columnnum++;
                            }
                            words.add(each);
                        }
                    }
                    rownum++;
                    if(!counted)
                    counted=true;

                }
            }
            //Debug
            /*
            for(String each : words){
                System.out.println(each);
            }
            */
            //System.out.println(columnnum);
            columnNames= new String[columnnum];
            data= new Object[rownum-1][columnnum];
            int populationRow=0;
            int flagRow=0;
            int starter=0;
            int currrownum=0;
            boolean columscreated=false;
            boolean modelCreated=false;
            //Wpisywanie danych w tabele

            for(String word : words) {
                if(!columscreated){
                    columnNames[starter]=word;
                    if (word.equals("Population")) {
                        populationRow=starter;
                    }
                    if(word.equals("Flag")) {
                        flagRow=starter;
                    }
                }
                else{
                    if(!modelCreated){
                        model = new DefaultTableModel(columnNames, 0) {
                            @Override
                            public Class<?> getColumnClass(int column) {
                                if (getRowCount() > 0) {
                                    Object value = getValueAt(0, column);
                                    if (value != null) {
                                        return getValueAt(0, column).getClass();
                                    }
                                }

                                return super.getColumnClass(column);
                            }
                        };
                        modelCreated=true;
                    }
                        if (starter==flagRow){
                            //Interpretuje napis jako sciezke do pliku
                            String flagPath = "data/flags/"+word;
                            //data[currrownum][starter] = flagPath;
                            ImageIcon icon = new ImageIcon(flagPath,
                                    flagPath);
                            data[currrownum][starter] = icon;
                        }
                        //Interpretuje napis jako liczbe
                        else if (starter==populationRow) {
                            data[currrownum][starter] = Integer.valueOf(word);
                        }
                        else {
                            data[currrownum][starter] = word;
                        }

                }
                starter++;
                if(starter%columnnum==0) {
                    if(!columscreated) {
                        columscreated = true;
                        currrownum--;
                    }

                    starter=0;
                    currrownum++;
                }
            }

            buf.close();
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
      
        CountryTable toReturn = new CountryTable(data,columnNames);

        for(int i=0; i<data.length;i++){
            model.addRow(data[i]);
        }
        toReturn.setModel(model);
        toReturn.setRowHeight(50);
        toReturn.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                int population = (Integer)table.getModel().getValueAt(row, 2);
                if (population > 30000) {
                    setBackground(Color.RED);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });
        return toReturn;
        //return this;
    }
}
