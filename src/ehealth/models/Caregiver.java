package ehealth.models;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the "caregiver" database table.
 * 
 */
@Entity
@Table(name="caregiver")
@NamedQuery(name="Caregiver.findAll", query="SELECT c FROM Caregiver c")
public class Caregiver implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int CGid;
	
	@Column(name="Care_UPid")
	private int care_UPid;

	/*@Column(name="CGid")
	private int CGid;*/

	//bi-directional many-to-one association to Healthmeasurehistory
	/*@ManyToOne
	@JoinColumn(name="Care_UPid", nullable=false)
	private Healthmeasurehistory healthmeasurehistory;*/

	//bi-directional many-to-one association to Userprofile
	@ManyToOne
	@JoinColumn(name="UPid", nullable=false)
	private Userprofile userprofile;

	public Caregiver() {
	}

	public int getCare_UPid() {
		return this.care_UPid;
	}

	public void setCare_UPid(int care_UPid) {
		this.care_UPid = care_UPid;
	}

	public int getCGid() {
		return this.CGid;
	}

	public void setCGid(int CGid) {
		this.CGid = CGid;
	}

	/*public Healthmeasurehistory getHealthmeasurehistory() {
		return this.healthmeasurehistory;
	}

	public void setHealthmeasurehistory(Healthmeasurehistory healthmeasurehistory) {
		this.healthmeasurehistory = healthmeasurehistory;
	}*/

	public Userprofile getUserprofile() {
		return this.userprofile;
	}

	public void setUserprofile(Userprofile userprofile) {
		this.userprofile = userprofile;
	}

}