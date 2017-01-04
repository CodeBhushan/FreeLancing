package com.projects.webScrapper;

/**
 * Created by rbhushan on 10/31/2016.
 */
public class MainScrapper {

    public static void main(String[] args) throws Exception{
        VigiAccessScrapper scrapper = new VigiAccessScrapper();
        scrapper.readSearchKeywords();
        scrapper.scrapForWords();

    }

}
