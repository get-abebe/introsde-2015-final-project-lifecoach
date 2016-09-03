package ehealth.models;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the "healthmeasurehistory" database table.
 * 
 */
@Entity
@Table(name="healthmeasurehistory")
@NamedQuery(name="Healthmeasurehistory.findAll", query="SELECT h FROM Healthmeasurehistory h")
public class Healthmeasurehistory implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(generator="sqlite_healthmeasurehistory")
	@TableGenerator(name="sqlite_healthmeasurehistory", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="healthmeasurehistory")
	@Column(name="HMHid")
	private int HMHid;

	@Temporal(TemporalType.DATE)
	@Column(name="dateTime")
	private Date dateTime;

	/*@Column(name="HMHid")
	private int HMHid;
*/
	@Column(name="value")
	private Double value;

	//bi-directional many-to-one association to Caregiver
	/*@OneToMany(mappedBy="healthmeasurehistory")
	private List<Caregiver> caregivers;
*/
	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MDid",referencedColumnName = "MDid")
	private Measuredefinition measuredefinition;

	//bi-directional many-to-one association to Userprofile
	@ManyToOne
	@JoinColumn(name="UPid",referencedColumnName = "UPid")
	private Userprofile userprofile;

	public Healthmeasurehistory() {
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getHMHid() {
		return this.HMHid;
	}

	public void setHMHid(int HMHid) {
		this.HMHid = HMHid;
	}

	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	/*public List<Caregiver> getCaregivers() {
		return this.caregivers;
	}

	public void setCaregivers(List<Caregiver> caregivers) {
		this.caregivers = caregivers;
	}*/

	/*public Caregiver addCaregiver(Caregiver caregiver) {
		getCaregivers().add(caregiver);
		caregiver.setHealthmeasurehistory(this);

		return caregiver;
	}

	public Caregiver removeCaregiver(Caregiver caregiver) {
		getCaregivers().remove(caregiver);
		caregiver.setHealthmeasurehistory(null);

		return caregiver;
	}*/

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