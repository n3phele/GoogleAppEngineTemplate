package io.mahakala.model.core;

import java.net.URI;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

public class ResourceEntity {
	@Id	protected Long id;
	@Unindex protected String uri;

	public ResourceEntity() {
		super();
	}
	

	/**
	 * @param name
	 * @param uri
	 */
	public ResourceEntity(URI uri) {
		this.uri = (uri == null)? null : uri.toString();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @param uri the uri to set
	 */
	public void setUri(URI uri) {
		this.uri = uri==null?null:uri.toString();
	}
	
	/**
	 * @return the uri
	 */
	public URI getUri() {
		return (this.uri == null)? null : URI.create(this.uri);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceEntity other = (ResourceEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResourceEntity [id=" + id + ", uri=" + uri + "]";
	}
	
	

}
