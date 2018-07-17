package com.lawbase.journal;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adhocmaster.service.RepositoryService;

@Service
public class JournalService extends RepositoryService<Journal> {

	@Autowired
    private JournalRepository journalRepository;
	
    @Override
    public Journal findOne( ObjectId id ) {

        return journalRepository.findOne(id);
        
    }

    @Override
    public  Journal save( Journal book ) {

    	//logger.debug( "Save should evict cache courtBookAll, courtBooksById" + courtBook.getId() );
        return journalRepository.save( book );
        
    }

    @Override
    public void delete( Journal book ) {

    	journalRepository.delete( book );
        
    }

    @Override
    public Page<Journal> findAll( Pageable pageable ) {

        return journalRepository.findAll( pageable );

    }

}
