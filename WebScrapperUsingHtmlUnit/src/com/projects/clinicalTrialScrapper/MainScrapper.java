package com.projects.clinicalTrialScrapper;

/**
 * Created by rbhushan on 10/31/2016.
 */
public class MainScrapper {

    public static void main(String[] args) throws Exception{
        ClinicalTrialScrapper scrapper = new ClinicalTrialScrapper();
        scrapper.readSearchKeywords();
        scrapper.scrapForWords();

    }

}
