package com.lawbase.article;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.book.Author;
import com.book.BookNode;
import com.book.simpleBook.SimpleBook;
import com.lawbase.journal.Journal;

@SolrDocument(solrCoreName = "")
@Document
@CompoundIndexes(

@CompoundIndex(name = "unique_title", def = "{ title: 1 }", unique = true, background = true)

)
public class Article extends SimpleBook {

	@Field
	int year;

	String volume = "";
	String issueNo = "";
	String externalAuthor = "";
	String externalAuthorDescription="";
	String description="";
	private String searchTerms = "";
	private String subject = "";
	
	
	@Field //Solr annotation
	String journalTitle = "";

	ObjectId journalId = null;
	ObjectId yearId = null;
	ObjectId volumeId = null;

	public Article() {

		super("No title", null);

	}

	public Article(String title, Author author) {

		super(title, author);

	}

	@Override
	public String getURL() {

		return "/article/" + getId();

	}

	public int getYear() {

		return year;
	}

	public void setYear(int year) {

		this.year = year;
	}

	public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getVolume() {

		return volume;
	}

	public void setVolume(String volume) {

		this.volume = volume;
	}

	public String getJournalTitle() {

		return journalTitle;
	}

	public void setJournalTitle(String journalTitle) {

		this.journalTitle = journalTitle;
	}

	public ObjectId getJournalId() {

		return journalId;
	}

	public void setJournalId(ObjectId journalId) {

		this.journalId = journalId;
	}

	public ObjectId getYearId() {

		return yearId;
	}

	public void setYearId(ObjectId yearId) {

		this.yearId = yearId;
	}

	public ObjectId getVolumeId() {

		return volumeId;
	}

	public void setVolumeId(ObjectId volumeId) {

		this.volumeId = volumeId;
	}
	
	public String getExternalAuthor() {
		return externalAuthor;
	}

	public void setExternalAuthor(String externalAuthor) {
		this.externalAuthor = externalAuthor;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getExternalAuthorDescription() {
		return externalAuthorDescription;
	}

	public void setExternalAuthorDescription(String externalAuthorDescription) {
		this.externalAuthorDescription = externalAuthorDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void populateJournalInformation(Journal journal, BookNode leafNode) {

		// TODO Auto-generated method stub
		journalId = journal.getId();
		journalTitle = journal.getTitle();

		BookNode volumeNode = journal.findNode(leafNode.getParentId());
		BookNode yearNode = journal.findNode(volumeNode.getParentId());

		try {

			year = Integer.parseInt(yearNode.getTitle());

		} catch (NumberFormatException e) {

			year = 0;

		}

		volume = volumeNode.getTitle();

		yearId = yearNode.getId();
		volumeId = volumeNode.getId();

	}

	public String getDateForInputMask(Date date) {

		if (null == date)
			return "";

		SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");

		return df.format(date);

	}

}
