package com.solr.lawbase.search.cases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adhocmaster.controller.DataTableResponseEntity;
import com.adhocmaster.controller.SolrRestController;
import com.lawbase.cases.Case;
import com.solr.core.BaseSolrRepository;
import com.solr.core.IndexStats;
import com.solr.lawbase.search.framework.BookIndexService;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;
import util.restApi.RestSuccess;

@RestController
@RequestMapping( "/search-engine/case" )
public class CaseSearchRestController extends SolrRestController<Case, SolrCase> {
    
    @Autowired
    CaseSolrRepository caseSolrRepository;

    @Autowired
    CaseIndexService caseIndexService;
    

    @Override
    protected BaseSolrRepository<SolrCase, String> getRepository() {

        return caseSolrRepository;
        
    }

    @Override
    protected BookIndexService<Case, SolrCase> getIndexService() {

        return caseIndexService;
        
    }

    @Override
    protected void callRebuildIndexAsync() throws Exception {

        caseIndexService.rebuildIndexAsync();
        
    }
    
    /**
     * 
     * @param field name of the field, nonblank. fieldname "all" if you want to search everything
     * @param value name of the value, nonblank
     * @param sEcho comes from datatable "sEcho"
     * @param offSet comes from datatable "iDisplayStart"
     * @param size comes from datatable "iDisplayLength"
     * @return
     * @throws RestInternalServerException, RestBadDataException
     */
    @RequestMapping( "/findByField")
    public DataTableResponseEntity<SolrCase> findByField(

            @RequestParam( "field" ) String field,
            @RequestParam( "value" ) String value,
            @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
            @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offSet,
            @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size
            
            ) throws RestInternalServerException, RestBadDataException {
        

        return super.findByFieldForDataTable( field, value, sEcho, offSet, size );

    }
    
    /**
     * searches the "all" field in search engine
     * @param value name of the value, nonblank
     * @param sEcho comes from datatable "sEcho"
     * @param offset comes from datatable "iDisplayStart"
     * @param size comes from datatable "iDisplayLength"
     * @return
     * @throws RestInternalServerException, RestBadDataException
     */
    @RequestMapping( "/findByAnyField")
    public DataTableResponseEntity<SolrCase> findByAnyField(

            @RequestParam( "value" ) String value,
            @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
            @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offset,
            @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size
            
            ) throws RestInternalServerException, RestBadDataException {
        
        return super.findByFieldForDataTable( "all", value, sEcho, offset, size );

    }
    
    @PreAuthorize( "hasAuthority('ADMIN')" ) 
    @RequestMapping( "/rebuildIndex" )
    public RestSuccess rebuildIndex() throws RestInternalServerException {
        
        return super.rebuildIndex();
    	
    }

    @PreAuthorize( "hasAuthority('ADMIN')" ) 
    @RequestMapping( "/statsIndex" )
    public IndexStats statsIndex() throws RestInternalServerException {
        
        return super.statsIndex();
        
    }



    
    
}
