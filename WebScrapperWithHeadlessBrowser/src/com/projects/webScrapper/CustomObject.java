package com.projects.webScrapper;

import org.openqa.selenium.WebElement;

/**
 * Created by rbhushan on 11/1/2016.
 */
public class CustomObject {
    private String areaLevel1;
    private String areaLevel2;
    private WebElement element;
    private String panelId;
    public CustomObject(){

    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public CustomObject(String areaLevel1, String areaLevel2, WebElement element, String panelId) {
        this.areaLevel1 = areaLevel1;
        this.areaLevel2 = areaLevel2;
        this.element = element;
        this.panelId = panelId;
    }

    public String getAreaLevel1() {
        return areaLevel1;
    }

    public void setAreaLevel1(String areaLevel1) {
        this.areaLevel1 = areaLevel1;
    }

    public String getAreaLevel2() {
        return areaLevel2;
    }

    public void setAreaLevel2(String areaLevel2) {
        this.areaLevel2 = areaLevel2;
    }

    public WebElement getElement() {
        return element;
    }

    public void setElement(WebElement element) {
        this.element = element;
    }
}
