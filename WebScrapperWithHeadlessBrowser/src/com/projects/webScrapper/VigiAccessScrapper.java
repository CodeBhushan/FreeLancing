package com.projects.webScrapper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by rbhushan on 11/1/2016.
 */
public class VigiAccessScrapper {
  private String url = "http://www.vigiaccess.org/";
  private String inputDoc = "enter_drug_list_vigiaccess.txt";
  private String outputDoc = "output_vigiaccess.csv";
  private List<String> searchKeyWords = new ArrayList();
  private long runNumber = 0;
  private String blankEntry = appendDQ("nc");

//
//  public void callAPIsForScrapping() throws IOException {
//    //https://apigee.com/console/others?req=%7B%22resource%22%3A%22https%3A%2F%2Fapi.who-umc.org%2Fvigibase%2Ficsrstatistics%2Fdistributions%3FagegroupFilter%3D%26continentFilter%3D%26reactionFilter%3D%26sexFilter%3D%26substanceFilter%3DLNSfR0%25252FWdZHjEN%25252BSs3OoKw%25253D%25253D%22%2C%22params%22%3A%7B%22query%22%3A%7B%22parameters_name_0%22%3A%22agegroupFilter%22%2C%22parameters_name_1%22%3A%22continentFilter%22%2C%22parameters_name_2%22%3A%22reactionFilter%22%2C%22parameters_name_3%22%3A%22sexFilter%22%2C%22parameters_name_4%22%3A%22substanceFilter%22%2C%22parameters_value_4%22%3A%22LNSfR0%25252FWdZHjEN%25252BSs3OoKw%25253D%25253D%22%7D%2C%22template%22%3A%7B%7D%2C%22headers%22%3A%7B%22headers_name_0%22%3A%22umc-client-key%22%2C%22headers_value_0%22%3A%226d851d41-f558-4805-a9a6-08df0e0e414b%22%7D%2C%22body%22%3A%7B%22undefined%22%3A%22%5Cu0001%5Cu0001%22%2C%22attachmentFormat%22%3A%22mime%22%2C%22attachmentContentDisposition%22%3A%22form-data%22%7D%7D%2C%22verb%22%3A%22get%22%7D
//    WebClient webClient = new WebClient();
//    webClient.getOptions().setCssEnabled(true);
//    webClient.getOptions().setJavaScriptEnabled(true);
//    URL url = new URL("https://api.who-umc.org/vigibase/icsrstatistics/dimensions/drug?tradename=velcade");
//
//    WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);
//
//    //requestSettings.setAdditionalHeader("Accept", "*/*");
//    //requestSettings.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//    //requestSettings.setAdditionalHeader("Referer", "REFURLHERE");
//    //requestSettings.setAdditionalHeader("Accept-Language", "en-US,en;q=0.8");
//    //requestSettings.setAdditionalHeader("Accept-Encoding", "gzip,deflate,sdch");
//    //requestSettings.setAdditionalHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
//    //requestSettings.setAdditionalHeader("X-Requested-With", "XMLHttpRequest");
//    //requestSettings.setAdditionalHeader("Cache-Control", "no-cache");
//    //requestSettings.setAdditionalHeader("Pragma", "no-cache");
//    //requestSettings.setAdditionalHeader("Origin", "https://YOURHOST");
//    requestSettings.setAdditionalHeader("umc-client-key", "6d851d41-f558-4805-a9a6-08df0e0e414b");
//
//    //requestSettings.setRequestBody("REQUESTBODY");
//
//    Page page = webClient.getPage(requestSettings);
//    WebResponse response = page.getWebResponse();
//    if (response.getContentType().equals("application/json")) {
//      String json = response.getContentAsString();
//      JsonParser parser = new JsonParser();
//      JsonObject o = parser.parse(json).getAsJsonObject();
//      // Map<String, String> map = new Gson().fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
//      //System.out.println(map.get("displayName")); // Benju
//    }
//  }


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
      out.write("Date_extraction,Status,Name_of_data_flow,Type_of_request,value_of_the_parameter,Run_number,Area_Level1,Area_Level2,Area_Level3,Area_Level4,Value_extracted1,Value_extracted2,Value_extracted3" + "\n");
      out.flush();
    } catch (IOException e) {
      System.out.println("File Read Error");
      e.printStackTrace();
    } finally {

    }

  }


  public void scrapForWords() {
    writeColumnHeadingsToCSV();
    WebDriver driver = CustomWebDriver.getDriver();
    getFirstPageUsingPhantom(driver);
    for (String str : getSearchKeyWords()) {
      scrapAndOutputForSingleWord(str, driver);
    }
    driver.quit();
  }

  private void getFirstPageUsingPhantom(WebDriver driver) {
    driver.get(getUrl());
    driver.findElement(By.id("acceptTermsCheckBox")).click();
    String xpath = "//*[@id=\"acceptTerms\"]/div/button";
    CustomWebDriver.waitForEnablingButton(driver, xpath);
    driver.findElement(By.xpath(xpath)).click();
    xpath = "//*[@id=\"search\"]/div/div[1]/form/input";
    CustomWebDriver.waitForGettingSearchBoxVisible(driver, xpath);
  }

  public void scrapAndOutputForSingleWord(String str, WebDriver driver) {
    String xpath = "//*[@id=\"search\"]/div/div[1]/form/input";//search text box xpath
    System.out.println("Scrapping and putting data into csv for word " + str);
    String absolutePath = getCurrentProductionDirectory();
    try (BufferedWriter out = new BufferedWriter(new FileWriter(absolutePath + File.separator + getOutputDoc(), true))) {

      WebElement element = driver.findElement(By.xpath(xpath));
      element.sendKeys(str);
      xpath = "//*[@id=\"search\"]/div/div[1]/form/button";
      CustomWebDriver.waitForEnablingButton(driver, xpath);
      //element.submit();
      WebElement element1 = driver.findElement(By.xpath(xpath));
      element1.click();
      CustomWebDriver.waitForSearchResult(driver, element1);
      if (driver.findElement(By.xpath("//*[@id=\"notFound\"]")).isDisplayed()) {
        System.out.println("The drug " + str + " was not found");
        out.write(
          getCurrentTimeStamp() + "," +
            2 + "," +//status
            "VigiAccess" + "," +
            "Drug" + "," +
            appendDQ(str) + "," +
            (++runNumber) + "," +
            "Total number of records retrieved" + "," +
            "Main Menu" + "," +
            blankEntry + "," +
            blankEntry + "," +
            blankEntry + "," +//value1
            blankEntry + "," +//value2
            blankEntry + ","//value3
            + "\n");
      } else {
        System.out.println("The drug " + str + " was found");
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"singleInfo\"]/p/strong"));
        out.write(
          getCurrentTimeStamp() + "," +
            0 + "," +//status
            "VigiAccess" + "," +
            "Drug" + "," +
            appendDQ(str) + "," +
            (++runNumber) + "," +
            "Total number of records retrieved" + "," +
            "Main Menu" + "," +
            blankEntry + "," +//area level 3 - always blank
            blankEntry + "," +//area level 4 - always blank
            appendDQ(elements.get(1).getText()) + "," +//value1
            appendDQ(elements.get(2).getText()) + "," +//value2
            blankEntry + ","//value3 - always blank entry for adverse drug reaction only
            + "\n");
        List<CustomObject> topList = new ArrayList<>();
        List<WebElement> linksUnderDistribution = driver.findElements(By.xpath("//*[@class=\"panel panel-default ng-isolate-scope\"]/div[1]/h4/a"));
        List<WebElement> linksPanel = driver.findElements(By.xpath("//*[@class=\"panel panel-default ng-isolate-scope\"]"));
        List<WebElement> distributionLinksText = driver.findElements(By.xpath("//*[@class=\"panel panel-default ng-isolate-scope\"]/div[1]/h4/a/span[2]"));
        if (linksUnderDistribution.size() != distributionLinksText.size() || linksPanel.size() != distributionLinksText.size()) {
          System.out.println("some fatal error in parsing.");
        } else {
          for (int i = 0; i < linksUnderDistribution.size(); i++) {
            topList.add(new CustomObject("Distribution", distributionLinksText.get(i).getText(), linksUnderDistribution.get(i), linksPanel.get(i).getAttribute("id")));
          }
        }
        for (int i = 0; i < topList.size(); i++) {
          if (i == 0) {
            processAdverseDrug(str, driver, topList.get(i), out);
          } else {
            processOthers(str, driver, topList.get(i), out);
          }
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

  private void processAdverseDrug(String drugName, WebDriver driver, CustomObject object, BufferedWriter out) throws IOException {
    Queue<CustomObject> queue = new LinkedList<>();
    queue.add(object);
    while (!queue.isEmpty()) {
      CustomObject object1 = queue.remove();
      WebElement element1 = object1.getElement();
      element1.click();
      CustomWebDriver.waitForSearchResult(driver, element1);
      List<WebElement> elements = new ArrayList<>();
      if (object1.getPanelId() != null) {
        elements = driver.findElements(By.xpath("//*[@id=\"" + object1.getPanelId() + "\"]/div[2]/div/treecontrol/ul/li"));
      } else {//TODO stale exception had come here somewhere.
        // elements = element1.findElements(By.xpath("..//treecontrol/ul/li"));
        if (elements == null || elements.size() == 0) {
          //elements = element1.findElements(By.xpath("..//treeitem/ul/li"));
          elements = element1.findElements(By.xpath("following-sibling::treeitem/ul/li"));
        }
        ;
      }

      if (elements.size() > 0) {
        int i = 0;
        for (WebElement element : elements) {
          WebElement clickableDiv = element.findElement(By.xpath("./div"));
          String clickableText = element.findElement(By.xpath("./div/span")).getText();
          String[] strings = clickableText.split(" \\([0-9]");
          String val1 = strings[0];
          String val2 = clickableText.charAt(clickableText.length() - strings[strings.length - 1].length() - 1) + strings[strings.length - 1];
          val2 = val2.substring(0, val2.length() - 1);
          out.write(
            getCurrentTimeStamp() + "," +
              0 + "," +//status
              "VigiAccess" + "," +
              "Drug" + "," +
              appendDQ(drugName) + "," +
              (++runNumber) + "," +
              appendDQ(object1.getAreaLevel1()) + "," +
              appendDQ(object1.getAreaLevel2()) + "," +
              blankEntry + "," +//area level 3 - always blank
              blankEntry + "," +//area level 4 - always blank
              appendDQ(val1) + "," +//value1
              appendDQ(val2) + "," +//value2
              blankEntry + ","//value3 - always blank entry for adverse drug reaction only
              + "\n");
          queue.add(new CustomObject(object1.getAreaLevel2(), val1, clickableDiv, null));

        }
        out.flush();
      }

    }
    out.flush();
  }

  private void processOthers(String drugName, WebDriver driver, CustomObject object, BufferedWriter out) throws IOException {
    WebElement element1 = object.getElement();
    element1.click();
    CustomWebDriver.waitForSearchResult(driver, element1);

    List<WebElement> tableRows = driver.findElements(By.xpath("//*[@id=\"" + object.getPanelId() + "\"]/div[2]/div/table/tbody/tr"));
    for (int i = 1; i < tableRows.size(); i++) {//heading of table not printed in csv
      WebElement tableRow = tableRows.get(i);
      List<WebElement> cells;
      if (i == 0) cells = tableRow.findElements(By.tagName("th"));
      else cells = tableRow.findElements(By.tagName("td"));
      out.write(
        getCurrentTimeStamp() + "," +
          0 + "," +//status
          "VigiAccess" + "," +
          "Drug" + "," +
          drugName + "," +
          (++runNumber) + "," +
          appendDQ(object.getAreaLevel1()) + "," +
          appendDQ(object.getAreaLevel2()) + "," +
          blankEntry + "," +//area level 3 - always blank
          blankEntry + "," +//area level 4 - always blank
          appendDQ(cells.get(0).getText()) + "," +//value1
          appendDQ(cells.get(1).getText()) + "," +//value2
          appendDQ(cells.get(2).getText()) + ","//value3 - always blank entry for adverse drug reaction only
          + "\n");
    }
    out.flush();

  }

  private String getCurrentClassPath() {
    //System.getProperty("user.dir");
    String s = this.getClass().getName();
    int i = s.lastIndexOf(".");
    if (i > -1) s = s.substring(i + 1);
    s = s + ".class";
    System.out.println("name " + s);
    Object testPath = this.getClass().getResource(s);
    System.out.println(testPath);
    return testPath.toString();
  }

  public static String getCurrentProductionDirectory() {
    final File f = new File(VigiAccessScrapper.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    String parentDir = f.getParent();
    System.out.println("production path is: " + parentDir);
    return parentDir;

  }

  public String appendDQ(String str) {
    return "\"" + str + "\"";
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
      substringFromLast(day, 0, 2) +
      substringFromLast(hour, 0, 2) +
      substringFromLast(min, 0, 2) +
      substringFromLast(sec, 0, 2)
      ;

                /*(new java.sql.Timestamp(utilDate.getTime())).toString()*/
  }
  //at org.openqa.selenium.support.ui.WebDriverWait.timeoutException
}
