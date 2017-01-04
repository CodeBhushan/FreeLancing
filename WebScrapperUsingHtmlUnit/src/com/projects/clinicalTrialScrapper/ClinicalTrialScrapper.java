package com.projects.clinicalTrialScrapper;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

/*import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;*/

import java.io.*;
import java.util.*;

/**
 * Created by rbhushan on 11/1/2016.
 */
public class ClinicalTrialScrapper {
    private String url = "https://www.clinicaltrialsregister.eu/ctr-search/search?query=";
    private String inputDoc = "enter_list_clinicaltrials.txt";
    private String outputDoc = "output_clinicaltrials_.txt";//todo change it to .txt
    private List<String> searchKeyWords = new ArrayList();
    private long runNumber = 0;
    private String separator = "|";//todo must change it to |
    private String blankEntry = appendDQ("nc");


    public String getSeparator() {
        return separator;
    }

    public void setSepartor(String separator) {
        this.separator = separator;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInputDoc() {
        return inputDoc;
    }

    public void setInputDoc(String inputDoc) {
        this.inputDoc = inputDoc;
    }

    public String getOutputDoc() {
        return outputDoc;
    }

    public void setOutputDoc(String outputDoc) {
        this.outputDoc = outputDoc;
    }

    public List<String> getSearchKeyWords() {
        return searchKeyWords;
    }

    public void setSearchKeyWords(List<String> searchKeyWords) {
        this.searchKeyWords = searchKeyWords;
    }

    public void readSearchKeywords() {
        System.out.println("Reading search keyword from txt file.");
        List searchKeyWords = this.getSearchKeyWords();
        String absolutePath = getCurrentProductionDirectory();
        File file = new File(absolutePath + File.separator + getInputDoc());
        if(!file.exists()){
            file = new File(getInputDoc());
        }
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {

            String str;
            while ((str = in.readLine()) != null) {
                String[] ar = str.split(",");
                searchKeyWords.add(ar[0]);
            }

        } catch (IOException e) {
            System.out.println("File Read Error");
            e.printStackTrace();
        } finally {

        }
        this.setSearchKeyWords(searchKeyWords);
    }

    private void writeColumnHeadingsToCSV() {
        System.out.println("Writing headings to csv.");
        String absolutePath = getCurrentProductionDirectory();
        byte[] buffer;
        try (BufferedWriter out = new BufferedWriter(new FileWriter(absolutePath + File.separator + getOutputDoc()))) {
            out.write("Date_extraction|Status|Name_of_data_flow|Type_of_request|value_of_the_parameter|Run_number|Area_Level1|Value_extracted1|Value_extracted2|Value_extracted3|Value_extracted4|EudraCT-number|Sponsor-protocol-number|Start-date|Sponsor-name|Full-tittle|Medical-condition|Disease-version|Disease-soc-term|Disease-classification-code|Disease-term|Disease-level|Population-age|Gender|Trial-protocol|Trial results" + "\n");//todo change to |
            out.newLine();
            //out.write("Date_extraction,Status,Name_of_data_flow,Type_of_request,value_of_the_parameter,Run_number,Area_Level1,Value_extracted1,Value_extracted2,Value_extracted3,Value_extracted4,EudraCT-number,Sponsor-protocol-number,Start-date,Sponsor-name,Full-tittle,Medical-condition,Disease-version,Disease-soc-term,Disease-classification-code,Disease-term,Disease-level,Population-age,Gender,Trial-protocol,Trial results" + "\n");
            out.flush();
        } catch (IOException e) {
            System.out.println("File Read Error");
            e.printStackTrace();
        } finally {

        }

    }



    /*private void getFirstPage() {
        System.out.println("Getting first page from url to agree for terms and conditions of website.");
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(true);
        client.getOptions().setJavaScriptEnabled(true);
        try {

            HtmlPage page = client.getPage(getUrl());
            System.out.println(page.asXml());
            //HtmlInput checkbox = page.getFirstByXPath("input[@type='checkbox']");
            //checkbox.setChecked(true);
            // HtmlButton button = page.get("button");


            HtmlInput searchBox = page.getFirstByXPath("//input[@type='search']");
            //HtmlInput inputLogin = inputPassword.getFirstByXPath(".//preceding::input[not(@type='hidden')]");
            searchBox.setValueAttribute("VELCADE");

            HtmlForm form = searchBox.getEnclosingForm();
            // HtmlButton button = (HtmlButton) page.getElementsByTagName("button");
            HtmlButton button = page.getFirstByXPath("//button[@class='btn btn-primary btn-default btn-sm']");
            while(!button.isDisplayed()){
                System.out.println("waiting for clickable button to be displayed.But it shouldnt happen.");
            };
            page = button.click();

            //submit the form
            // page = client.getPage(form.getWebRequest(null));
            System.out.println(page.asXml());
            //returns the cookie filled client :)
//        return client;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void scrapForWords() throws IOException {

        //WebDriver driver = CustomWebDriver.getDriver();


        try (final WebClient webClient = new WebClient()) {
            String constantOutputFileNamePrefix = getOutputDoc();
            for (String str : getSearchKeyWords()) {
                String time = getCurrentTimeStamp();
                setOutputDoc(constantOutputFileNamePrefix.split("\\.")[0] + time + "." + constantOutputFileNamePrefix.split("\\.")[1]);
                writeColumnHeadingsToCSV();
                scrapAndOutputForSingleWord(str, webClient, time);
            }
        }
        /*for (String str : getSearchKeyWords()) {

            setOutputDoc(getOutputDoc().split("\\.")[0]+time+getOutputDoc().split("\\.")[1]);
            writeColumnHeadingsToCSV();
            scrapAndOutputForSingleWord(str, driver ,time);
        }
        driver.quit();*/
    }

    public void scrapAndOutputForSingleWord(String str, WebClient webClient, String time) throws IOException {
        int status = 0;
        str = str.trim();
        if (str.length() == 0 || str == null) return;
        HtmlPage page = webClient.getPage(getUrl() + str);
        HtmlSpan span1 = page.getHtmlElementById("total");
        HtmlSpan span2 = page.getHtmlElementById("under18");
        HtmlSpan span3 = page.getHtmlElementById("article45Count");
        HtmlElement totalCountElement = page.getHtmlElementById("ui-id-1");
        String totalCount = totalCountElement.getTextContent().trim();
        String[] strings = totalCount.split(" \\([0-9]");
        //String val1 = strings[0];
        String val2 = totalCount.charAt(totalCount.length() - strings[strings.length - 1].length() - 1) + strings[strings.length - 1];
        val2 = val2.substring(0, val2.length() - 1);

        String firstOutputLine = time + getSeparator() + status + getSeparator() + "Clinicaltrials" + getSeparator() + blankEntry + getSeparator() +
                str + getSeparator() +
                ++runNumber + getSeparator() +
                "Total number of trials" + getSeparator() +
                span1.getTextContent().trim() + getSeparator() +
                span2.getTextContent().trim() + getSeparator() +
                span3.getTextContent().trim() + getSeparator() +
                val2 + getSeparator() +
                getRepeatedBlankEnty(15) +
                "\n";
        String xpath = "//*[@id=\"tabs-1\"]/div[1]";
        HtmlDivision totalResultDiv = (HtmlDivision) page.getByXPath(xpath).get(0);
        String temp = totalResultDiv.getTextContent().trim().split(" of ")[1].split("\\.")[0];
        int totalResultPages = Integer.parseInt(temp);


        System.out.println("Scrapping and putting data into csv for word " + str);
        String absolutePath = getCurrentProductionDirectory();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(absolutePath + File.separator + getOutputDoc(), true))) {
            out.write(firstOutputLine);
                out.newLine();

            for (int i = 1; i <= totalResultPages; i++) {

                if (i != 1)
                    page = webClient.getPage(getUrl() + str + "&page=" + i);//load second result page onward only
                HtmlElement firstTab = page.getHtmlElementById("tabs-1");

                xpath = "./div[3]/table";
                List<?> tables = firstTab.getByXPath(xpath);
                for (int k = 0; k < tables.size(); k++) {
                    boolean isMultiDisease = false;
                    boolean isZeroDisease = false;
                    List<String> diseaseRowsDataStrings = new ArrayList<>();
                    HtmlTable diseaseListTable;
                    HtmlTable table = (HtmlTable) tables.get(k);
                    HtmlTableCell diseaseCell = table.getCellAt(4, 0);
                    HtmlElement diseaseCellElement = (HtmlElement) diseaseCell;

                    if (diseaseCellElement.getElementsByTagName("table").size()!=0) {
//                        HtmlElement element = diseaseCellElement.getElementsByTagName("table").get(0);
                        diseaseListTable = diseaseCellElement.getOneHtmlElementByAttribute("table", "class", "meddra");
                        if (diseaseListTable.getRowCount() > 2) isMultiDisease = true;
                        for (int r = 1; r < diseaseListTable.getRowCount(); r++) {
                            String s = "";
                            HtmlTableRow row = diseaseListTable.getRows().get(r);
                            for (final HtmlTableCell cell : row.getCells()) {
                                s += appendDQ(getCellContent(cell)) + getSeparator();
                            }
                            diseaseRowsDataStrings.add(s);
                        }

                    } else {
                        isZeroDisease = true;
                    }
                    String output = time + getSeparator() + (isMultiDisease?4:status) + getSeparator() + "Clinicaltrials" + getSeparator() + blankEntry + getSeparator() +
                            str + getSeparator() +
                            ++runNumber + getSeparator() +
                            "Total number of trials" + getSeparator() +
                            getRepeatedBlankEnty(4) +
                            appendDQ(getCellContent(table.getCellAt(0, 0))) + getSeparator() +
                            appendDQ(getCellContent(table.getCellAt(0, 1))) + getSeparator() +
                            appendDQ(getCellContent(table.getCellAt(0, 2))) + getSeparator() +
                            appendDQ(getCellContent(table.getCellAt(1, 0))) + getSeparator() +
                            appendDQ(getCellContent(table.getCellAt(2, 0))) + getSeparator() +
                            appendDQ(getCellContent(table.getCellAt(3, 0))) + getSeparator();

                    if (isZeroDisease) {
                        output += " " + getSeparator() + " " + getSeparator() + " " + getSeparator() + " " + getSeparator() + " " + getSeparator();
                        output += appendDQ(getCellContent(table.getCellAt(5, 0))) + getSeparator() +
                                appendDQ(getCellContent(table.getCellAt(5,2))) + getSeparator();
                        output += getTrialProtocols(table);
                        //output +=appendDQ ((table.getCellAt(7, 0).getElementsByTagName("a").size()!=0?table.getCellAt(7, 0).getElementsByTagName("a"):table.getCellAt(7, 0).getElementsByTagName("i")).get(0).getTextContent()) + "\n";
                        output+=appendDQ(getCellContent(table.getCellAt(7,0)))+"\n";
                        out.write(output);
                        out.newLine();
                    } else {
                        String outputPrefix = output;
                        for (String s : diseaseRowsDataStrings) {
                            output=outputPrefix;
                            output += s;
                            output += appendDQ(getCellContent(table.getCellAt(5, 0))) + getSeparator() +
                                    appendDQ(getCellContent(table.getCellAt(5,2))) + getSeparator();
                            output += getTrialProtocols(table);
                            //output +=appendDQ ((table.getCellAt(7, 0).getElementsByTagName("a").size()!=0?table.getCellAt(7, 0).getElementsByTagName("a"):table.getCellAt(7, 0).getElementsByTagName("i")).get(0).getTextContent()) + "\n";
                            output+=appendDQ(getCellContent(table.getCellAt(7,0)))+"\n";
                            out.write(output);
                            out.newLine();
                        }
                    }

                }
                System.out.println(runNumber + " "+ i );
                out.flush();
            }
            out.flush();
        } catch (IOException e) {
            System.out.println("File Write Error");
            e.printStackTrace();
        } finally {

        }

    }

    private String getTrialProtocols(HtmlTable table) {
        List<HtmlElement> anchorElements = table.getCellAt(6, 0).getElementsByTagName("a");
        List<HtmlElement> bracketElements = table.getCellAt(6, 0).getElementsByAttribute("span", "class", "status");
        String s = "";
        Iterator<HtmlElement> it1 = anchorElements.iterator();
        Iterator<HtmlElement> it2 = bracketElements.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            s += it1.next().getTextContent().trim() + " " + it2.next().getTextContent().trim() + " ";
        }
        return appendDQ(s) + getSeparator();
    }

    /* public void scrapAndOutputForSingleWord(String str, WebDriver driver, String time) {
         int status = 0;
         str= str.trim();
         if(str.length()==0||str==null) return;
         driver.get(getUrl()+str);
         String xpath = "/*//*[@id=\"total\"]";
        WebElement element1 = driver.findElement(By.xpath(xpath));
        xpath = "/*//*[@id=\"under18\"]";
        WebElement element2 = driver.findElement(By.xpath(xpath));
        xpath = "/*//*[@id=\"article45Count\"]";
        WebElement element3 = driver.findElement(By.xpath(xpath));
        xpath = "/*//*[@id=\"ui-id-1\"]";
        WebElement element4 = driver.findElement(By.xpath(xpath));
        String topText = element4.getAttribute("innerHTML").trim();
        String[] strings = topText.split(" \\([0-9]");
        //String val1 = strings[0];
        String val2 = topText.charAt(topText.length() - strings[strings.length - 1].length() - 1) + strings[strings.length - 1];
        val2 = val2.substring(0, val2.length() - 1);

        String outString = time + getSeparator() + status+ getSeparator() + "Clinicaltrials" + getSeparator() + blankEntry + getSeparator()+
                str+getSeparator()+
                ++runNumber + getSeparator()+
                "Total number of trials" + getSeparator()+
                element1.getAttribute("innerHTML").trim() + getSeparator()+
                element2.getAttribute("innerHTML").trim() + getSeparator()+
                element3.getAttribute("innerHTML").trim() + getSeparator()+
                val2+ getSeparator()   +
                getRepeatedBlankEnty(15)+
                "\n";
        xpath = "/*//*[@id=\"tabs-1\"]/div[1]";
        String temp = driver.findElement(By.xpath(xpath)).getAttribute("innerHTML").trim().split(" of ")[1].split("\\.")[0];
        int totalResultPages = Integer.parseInt(temp);


                System.out.println("Scrapping and putting data into csv for word " + str);
        String absolutePath = getCurrentProductionDirectory();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(absolutePath + File.separator + getOutputDoc(), true))) {
            out.write(outString);


            for(int i =1; i<totalResultPages;i++){
                if(i!=1)    driver.get(getUrl()+str + "&page="+i);
                xpath = "/*//*[@id=\"tabs-1\"]/div[3]/table";
                List<WebElement> tables = driver.findElements(By.xpath(xpath));
                for(WebElement element: tables){

                }
            }


            //System.out.println(driver.getPageSource());
            out.flush();
        } catch (IOException e) {
            System.out.println("File Write Error");
            e.printStackTrace();
        } finally {

        }

    }
*/
    private String getRepeatedBlankEnty(int count) {
        String s = "";
        for (int i = 0; i < count; i++) {
            s += blankEntry + getSeparator();
        }
        return s;
    }

    public static String getCurrentProductionDirectory() {
        final File f = new File(ClinicalTrialScrapper.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String parentDir = f.getParent();
        System.out.println("production path is: " + parentDir);
        return parentDir;

    }

    public String appendDQ(String str) {

        if(str.length()==0) str=" ";// space added so as to have space for zero len
        //return "\"" + str + "\"";
        return str;//TODo unccomment it
    }

    private static String substringFromLast(String s, int beginIndex, int endIndex) {
        return s.substring(s.length() - endIndex, s.length() - beginIndex);
    }

    public static String getCurrentTimeStamp() {
        Date utilDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);
        //cal.set(Calendar.MILLISECOND, 0);
        String year = "0000" + cal.get(Calendar.YEAR);
        String month = "00" + (cal.get(Calendar.MONTH) + 1);
        String day = "00" + cal.get(Calendar.DATE);
        String hour = "00" + cal.get(Calendar.HOUR);
        String min = "00" + cal.get(Calendar.MINUTE);
        String sec = "00" + cal.get(Calendar.SECOND);


        return substringFromLast(year, 0, 4) +
                substringFromLast(month, 0, 2) +
                substringFromLast(day, 0, 2) + "_" +
                substringFromLast(hour, 0, 2) +
                substringFromLast(min, 0, 2) +
                substringFromLast(sec, 0, 2)
                ;

                /*(new java.sql.Timestamp(utilDate.getTime())).toString()*/
    }

    public String getCellContent(HtmlTableCell cell){
        String s = cell.getTextContent().trim().split(":").length==1?cell.getTextContent().trim().split(":")[0].trim():cell.getTextContent().trim().split(":")[1].trim();
        s= s.replaceAll("\\s+"," ");
        s= s.replaceAll("\\n"," ");

        return s;
    }
    //at org.openqa.selenium.support.ui.WebDriverWait.timeoutException
}
