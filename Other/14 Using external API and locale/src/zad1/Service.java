/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Service {

    //Input
    public String country;
    public String targetCurrency;
    public String city;

    //Output
    public String weatherString; //1
    public String finalRatio; //2
    public String npbRatio; //3



    //Helper


    public Locale locale;
    public Locale PLN;


    public HashMap<String, Double> ratio;
    public Service(String country) {
        this.country=country;
        //this.currency = Currency.getInstance(l);

        locale= Arrays.stream(Locale.getAvailableLocales()).filter(locale1 -> locale1.getDisplayCountry(Locale.ENGLISH).equals(country)).findFirst().get();
        PLN = Arrays.stream(Locale.getAvailableLocales()).filter(locale1 -> locale1.getDisplayCountry(Locale.ENGLISH).equals("Poland")).findFirst().get();
        ratio=new HashMap<>();
        ratio.put("PLN",1.0);
        calculateNBPRate("a");
        calculateNBPRate("b");
    }

    // Weather key
    String weatherApiKey="07577442c97f58d3dcc235dab2e33753";

    public String getWeather(String city) {
        this.city=city;
        /*
        System.out.println(locale.getCountry());
        for( String country : Locale.getISOCountries()) {
            System.out.println(country);
        }

         */
        //Locale.
        String request = "http://api.openweathermap.org/data/2.5/weather?q="+city+","+locale.getCountry()+"&appid="+weatherApiKey;
        URL url = null;
        try {
            url = new URL(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String s = "";

        assert url != null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            String line;
            while((line = in.readLine()) != null)
                s += line;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(s);
        this.weatherString=s;
        return s;
    }


    public Double getRateFor(String targetCurrency) {
        this.targetCurrency=targetCurrency;
        Currency c = Currency.getInstance(locale);
        String extraData = "";
        if (targetCurrency.equals("EUR") || c.getCurrencyCode().equals("EUR")){
            extraData=";base=USD";
        }
        if(targetCurrency.equals("PLN")) {
            return 1.0;
        }
        //System.out.println(c);
        String request = "https://api.exchangeratesapi.io/latest?symbols="+c.getCurrencyCode()+","+targetCurrency+extraData;
        URL url = null;
        try {
            url = new URL(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String json = "";

        assert url != null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            String line;
            while((line = in.readLine()) != null)
                json += line;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(json);
        Pattern p = Pattern.compile("\\d+\\.\\d+");
        Matcher m = p.matcher(json);
        Double rates[] = new Double[2];
        int i =0;
        while(m.find()){
            rates[i]=Double.parseDouble(m.group());
            i++;
        }
        Double result = rates[1]/rates[0];
        //System.out.println(result);
        this.finalRatio=result.toString();
        return result;
    }

    private void calculateNBPRate(String page){

        //System.out.println(c);
        String request = "http://www.nbp.pl/kursy/kursy"+page+".html";
        URL url = null;
        try {
            url = new URL(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String json = "";

        assert url != null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            String line;
            while((line = in.readLine()) != null)
                json += line;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern p = Pattern.compile("<tr>.+?</tr>");
        Pattern p2 = Pattern.compile("<td class=\"bgt.+?</td>");
        Pattern p3 = Pattern.compile(">(.+?)</");
        Matcher m = p.matcher(json);
        int fixer=0;
        //int debug=0;
        while(m.find()){
            if(fixer<2){
                fixer++;
            }
            else {
                String row = m.group();

                Matcher m2 = p2.matcher(row);
                int i = 0;
                String currname = "";
                int currency_amount = 0;
                while (m2.find()) {
                    String row2 = m2.group();
                    Matcher m3 = p3.matcher(row2);
                    //System.out.println(debug);
                    //debug++;
                    //System.out.println(row2);

                    while (m3.find()) {
                        if (i == 1) {
                            String currnamestring = m3.group(1);
                            String splitted[] = currnamestring.split(" ");
                            currency_amount = Integer.parseInt(splitted[0]);
                            currname = splitted[1];
                        }
                        if (i == 2) {
                            String numb = m3.group(1).replace(",",".");
                            Double local_rate = Double.parseDouble(numb);
                            Double calculated = currency_amount / local_rate;
                            this.ratio.put(currname,calculated);
                        }

                    }
                    i++;
                }
            }

        }
    }

    public Double getNBPRate() {
        Currency c = Currency.getInstance(locale);
        Double r = this.ratio.get(c.getCurrencyCode());
        //System.out.println(r);
        this.npbRatio=r.toString();
        return r;
    }
}
