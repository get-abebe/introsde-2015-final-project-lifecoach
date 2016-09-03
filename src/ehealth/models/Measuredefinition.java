package ehealth.models;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;


/**
 * The persistent class for the "measuredefinition" database table.
 * 
 */
@Entity
@Table(name="measuredefinition")
@NamedQuery(name="Measuredefinition.findAll", query="SELECT m FROM Measuredefinition m")

public class Measuredefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="sqlite_measuredefinition")
	@TableGenerator(name="sqlite_measuredefinition", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="measuredefinition")
	
	@Column(name="MDid")
	private int MDid;

	@Column(name="measureName")
	private String measureName;

	@Column(name="measureType")
	private String measureType;

	//bi-directional many-to-one association to Advisor
	@OneToMany(mappedBy="measuredefinition")
	private List<Advisor> advisors;

	//bi-directional many-to-one association to Currentlifestatus
	@OneToMany(mappedBy="measuredefinition")
	private List<Currentlifestatus> currentlifestatuses;

	//bi-directional many-to-one association to Healthmeasurehistory
	@OneToMany(mappedBy="measuredefinition")
	private List<Healthmeasurehistory> healthmeasurehistories;

	//bi-directional many-to-one association to Measuredefaultrange
	@OneToMany(mappedBy="measuredefinition")
	private List<Measuredefaultrange> measuredefaultranges;

	public Measuredefinition() {
	}

	public int getMDid() {
		return this.MDid;
	}

	public void setMDid(int MDid) {
		this.MDid = MDid;
	}

	public String getMeasureName() {
		return this.measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}
	@XmlTransient
	public List<Advisor> getAdvisors() {
		return this.advisors;
	}

	public void setAdvisors(List<Advisor> advisors) {
		this.advisors = advisors;
	}

	public Advisor addAdvisor(Advisor advisor) {
		getAdvisors().add(advisor);
		advisor.setMeasuredefinition(this);

		return advisor;
	}

	public Advisor removeAdvisor(Advisor advisor) {
		getAdvisors().remove(advisor);
		advisor.setMeasuredefinition(null);

		return advisor;
	}
	@XmlTransient
	public List<Currentlifestatus> getCurrentlifestatuses() {
		return this.currentlifestatuses;
	}

	public void setCurrentlifestatuses(List<Currentlifestatus> currentlifestatuses) {
		this.currentlifestatuses = currentlifestatuses;
	}

	public Currentlifestatus addCurrentlifestatus(Currentlifestatus currentlifestatus) {
		getCurrentlifestatuses().add(currentlifestatus);
		currentlifestatus.setMeasuredefinition(this);

		return currentlifestatus;
	}

	public Currentlifestatus removeCurrentlifestatus(Currentlifestatus currentlifestatus) {
		getCurrentlifestatuses().remove(currentlifestatus);
		currentlifestatus.setMeasuredefinition(null);

		return currentlifestatus;
	}
	@XmlTransient
	public List<Healthmeasurehistory> getHealthmeasurehistories() {
		return this.healthmeasurehistories;
	}

	public void setHealthmeasurehistories(List<Healthmeasurehistory> healthmeasurehistories) {
		this.healthmeasurehistories = healthmeasurehistories;
	}

	public Healthmeasurehistory addHealthmeasurehistory(Healthmeasurehistory healthmeasurehistory) {
		getHealthmeasurehistories().add(healthmeasurehistory);
		healthmeasurehistory.setMeasuredefinition(this);

		return healthmeasurehistory;
	}

	public Healthmeasurehistory removeHealthmeasurehistory(Healthmeasurehistory healthmeasurehistory) {
		getHealthmeasurehistories().remove(healthmeasurehistory);
		healthmeasurehistory.setMeasuredefinition(null);

		return healthmeasurehistory;
	}
	@XmlTransient
	public List<Measuredefaultrange> getMeasuredefaultranges() {
		return this.measuredefaultranges;
	}

	public void setMeasuredefaultranges(List<Measuredefaultrange> measuredefaultranges) {
		this.measuredefaultranges = measuredefaultranges;
	}

	public Measuredefaultrange addMeasuredefaultrange(Measuredefaultrange measuredefaultrange) {
		getMeasuredefaultranges().add(measuredefaultrange);
		measuredefaultrange.setMeasuredefinition(this);

		return measuredefaultrange;
	}

	public Measuredefaultrange removeMeasuredefaultrange(Measuredefaultrange measuredefaultrange) {
		getMeasuredefaultranges().remove(measuredefaultrange);
		measuredefaultrange.setMeasuredefinition(null);

		return measuredefaultrange;
	}

}