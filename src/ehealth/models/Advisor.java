package ehealth.models;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the "advisor" database table.
 * 
 */
@Entity
@Table(name="advisor")
@NamedQuery(name="Advisor.findAll", query="SELECT a FROM Advisor a")
public class Advisor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Aid", unique=true, nullable=false)
	private int aid;
	
	@Id
	@Column(name="advice")
	private String advice;

	/*@Column(name="Aid")
	private int aid;*/

	@Column(name="tags")
	private String tags;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MDid", nullable=false)
	private Measuredefinition measuredefinition;

	//bi-directional many-to-one association to Userprofile
	@ManyToOne
	@JoinColumn(name="UPid", nullable=false)
	private Userprofile userprofile;

	public Advisor() {
	}

	public String getAdvice() {
		return this.advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public int getAid() {
		return this.aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	@XmlTransient
	public Measuredefinition getMeasuredefinition() {
		return this.measuredefinition;
	}

	public void setMeasuredefinition(Measuredefinition measuredefinition) {
		this.measuredefinition = measuredefinition;
	}

	public Userprofile getUserprofile() {
		return this.userprofile;
	}

	public void setUserprofile(Userprofile userprofile) {
		this.userprofile = userprofile;
	}

}