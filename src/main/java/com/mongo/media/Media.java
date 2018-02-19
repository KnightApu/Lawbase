package com.mongo.media;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;


import com.adhocmaster.IdentifiableEntity;

//@Entity
//@Table(name = "Media")
@Document( collection = "media" )
public class Media implements IdentifiableEntity {

	public static final long maxFileNumber = 30000;
	public static final int maxSlugLength = 100;
	
	//i added
	public static final String SEQ_KEY = "media";
	//////

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String path;

	private String url;

	protected String slug = "";

	protected String extension = "";

	private long forId = 0;

	private String forType;

	private String status = "available";

	private String mimeType;

	public String getMimeType() {

		return mimeType;
	}

	public void setMimeType(String mIMEtype) {

		this.mimeType = mIMEtype;
	}

	public long getForId() {

		return forId;
	}

	public void setForId(long forId) {

		this.forId = forId;
	}

	public String getForType() {

		return forType;
	}

	public void setForType(String forType) {

		this.forType = forType;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public String getPath() {

		return path;
	}

	public void setPath(String path) {

		this.path = path;
	}

	public Media() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public Media(long id) { this.id = id; }
	 * 
	 * public long getId() { return id; }
	 */

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {

		this.name = name;
		genSlug();

	}

	public String getSlug() {

		return slug;
	}

	public void setSlug(String slug) {

		this.slug = slug;
	}

	public void genSlug() {

		int length = name.length();

		String slug = name.toLowerCase().replace(' ', '-').replace('.', '-');

		if (length > maxSlugLength) {

			slug = slug.substring(0, maxSlugLength - 1);

		}

		setSlug(slug);

	}

	public String getExtension() {

		return extension;
	}

	public void setExtension(String extension) {

		this.extension = extension;
	}

	@Override
	public String toString() {

		return "Media [id=" + id + ", name=" + name + ", path=" + path + ", url=" + url + ", slug=" + slug
				+ ", extension=" + extension + ", forId=" + forId + ", forType=" + forType + ", status=" + status
				+ ", mimeType=" + mimeType + "]";
	}

}