package ehealth.models;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the advisor database table.
 * 
 */
@Embeddable
public class AdvisorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false)
	private int aid;

	@Column(unique=true, nullable=false)
	private int UPid;

	public AdvisorPK() {
	}
	public int getAid() {
		return this.aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public int getUPid() {
		return this.UPid;
	}
	public void setUPid(int UPid) {
		this.UPid = UPid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdvisorPK)) {
			return false;
		}
		AdvisorPK castOther = (AdvisorPK)other;
		return 
			(this.aid == castOther.aid)
			&& (this.UPid == castOther.UPid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.aid;
		hash = hash * prime + this.UPid;
		
		return hash;
	}
}