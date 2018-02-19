package com.solr.lawbase.search;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawbase.cases.Case;
import com.solr.lawbase.search.cases.CaseEntityConverter;
import com.solr.lawbase.search.cases.CaseSolrRepository;


@RunWith(SpringRunner.class)
@SpringBootTest( classes = com.lawbase.LawbaseApplication.class )
@TestPropertySource( locations = "classpath:application-muktadir.properties" )
public class CaseSolrRepositoryTest {

    @Autowired
    CaseSolrRepository caseSolrRepository;
    @Autowired
    CaseEntityConverter entityConverter;

    @Test
    public void testAdd() {

        Case casebook = new Case( "My first case", null );
        
        casebook.setId( new ObjectId() );
        
        System.out.println( caseSolrRepository.save( entityConverter.convert( casebook ) ) );

    }
    @Test
    public void testAddBn() {

        Case casebook = new Case( "আমার গোধূলিলগন এল বুঝি কাছে গোধূলিলগন রে", null );
        
        casebook.setId( new ObjectId() );
        
        casebook.setTranscript( "আমার গোধূলিলগন এল বুঝি কাছে গোধূলি লগন রে।"
           + "বিবাহের রঙে রাঙা হয়ে আসে সোনার গগন রে।"
           + "শেষ ক’রে দিল পাখি গান-গাওয়া, নদীর উপরে পড়ে এল হাওয়া ;"
           + "ও পারের তীর, ভাঙা মন্দির আঁধারে মগন রে"
           + "আসিছে মধুর ঝিল্লিনূপুরে গোধূলিলগন রে"
           + "বুঝি দেরি নাই, আসে বুঝি আসে, আলোকের আভা লেগেছে আকাশে–"
           + "বেলাশেষে মোরে কে সাজাবে, ওরে, নবমিলনের সাজে !"
            + "সারা হল কাজ, মিছে কেন আজ ডাক মোরে আর কাজে"
            + "আমি জানি যে আমার হয়ে গেছে গণা গোধূলিলগন রে।"
               + "ধূসর আলোকে মুদিবে নয়ন অস্তগগন রে।"
               + " তখন এ ঘরে কে খুলিবে দ্বার, কে লইবে টানি বাহু আমার,"
               + "আমায় কে জানে কী মন্ত্রে গানে করিবে মগন রে–"
               + "সব গান সেরে আসিবে যখন গোধূলিলগন রে।।" );
        
        System.out.println( caseSolrRepository.save(  entityConverter.convert( casebook )  ) );
          
        casebook = new Case( "কালাংড়া-ভৈরবী", null );
  
        casebook.setId( new ObjectId() );
  
        casebook.setTranscript( "আমার   যে দিন ভেসে গেছে চোখের জলে"
                + "তারি   ছায়া পড়েছে শ্রাবণগগনতলে॥"
                + "সে দিন যে রাগিণী গেছে থেমে,    অতল বিরহে নেমে   গেছে থেমে,"
                + "আজি   পুবের হাওয়ায় হাওয়ায়   হায় হায় হায় রে"
                + " কাঁপন ভেসে চলে॥"
                + "নিবিড় সুখে মধুর দুখে জড়িত ছিল সেই দিন--"
                + "দুই তারে জীবনের বাঁধা ছিল বীন।"
                );

        List<String> keywords = new ArrayList<String>();

        keywords.add( "আমার" );
        keywords.add( "জীবনের" );
        keywords.add( "মধুর" );
        
        
        List<String> sources = new ArrayList<String>();
        
        sources.add( "জড়িত" );
        
        casebook.setKeywords( keywords );
        casebook.setSources( sources );
        System.out.println( caseSolrRepository.save( entityConverter.convert( casebook ) ) );

    }
    

}
