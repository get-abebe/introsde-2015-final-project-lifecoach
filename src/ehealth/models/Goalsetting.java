package ehealth.models;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Calendar;
import java.util.Date;


/**
 * The persistent class for the "goalsettings" database table.
 * 
 */
@Entity
@Table(name="goalsettings")
@NamedQuery(name="Goalsetting.findAll", query="SELECT g FROM Goalsetting g")
public class Goalsetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int PRid;
	
	@Column(name="byCareGiver")
	private int byCareGiver;

	@Column(name="byUserProfile")
	private int byUserProfile;

	/*@Column(name="PRid")
	private int PRid;*/

	@Column(name="query")
	private String query;
	
	@Temporal(TemporalType.DATE)
	@Column(name="scheduleDateTime")
	private Date scheduleDateTime;

	@Column(name="service")
	private String service;

	@Column(name="workProgress")
	private String workProgress;

	//bi-directional many-to-one association to Userprofile
	@ManyToOne
	@JoinColumn(name="UPid",referencedColumnName = "UPid")
	private Userprofile userprofile;

	public Goalsetting() {
	}

	public int getByCareGiver() {
		return this.byCareGiver;
	}

	public void setByCareGiver(int byCareGiver) {
		this.byCareGiver = byCareGiver;
	}

	public int getByUserProfile() {
		return this.byUserProfile;
	}

	public void setByUserProfile(int byUserProfile) {
		this.byUserProfile = byUserProfile;
	}

	public int getPRid() {
		return this.PRid;
	}

	public void setPRid(int PRid) {
		this.PRid = PRid;
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Date getScheduleDateTime() {
		return this.scheduleDateTime;
	}

	public void setScheduleDateTime(Date scheduleDateTime) {
		this.scheduleDateTime = scheduleDateTime;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getWorkProgress() {
		return this.workProgress;
	}

	public void setWorkProgress(String workProgress) {
		this.workProgress = workProgress;
	}

	public Userprofile getUserprofile() {
		return this.userprofile;
	}

	public void setUserprofile(Userprofile userprofile) {
		this.userprofile = userprofile;
	}

}