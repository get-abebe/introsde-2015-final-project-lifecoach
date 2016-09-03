package ehealth.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the measuredefaultrange database table.
 * 
 */
@Embeddable
public class MeasuredefaultrangePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false)
	private int MDRid;

	@Column(unique=true, nullable=false)
	private int MDid;

	public MeasuredefaultrangePK() {
	}
	public int getMDRid() {
		return this.MDRid;
	}
	public void setMDRid(int MDRid) {
		this.MDRid = MDRid;
	}
	public int getMDid() {
		return this.MDid;
	}
	public void setMDid(int MDid) {
		this.MDid = MDid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MeasuredefaultrangePK)) {
			return false;
		}
		MeasuredefaultrangePK castOther = (MeasuredefaultrangePK)other;
		return 
			(this.MDRid == castOther.MDRid)
			&& (this.MDid == castOther.MDid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.MDRid;
		hash = hash * prime + this.MDid;
		
		return hash;
	}
}