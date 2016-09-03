package ehealth.models;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

//import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the "currentlifestatus" database table.
 * 
 */
@Entity
@Table(name="currentlifestatus")
@NamedQuery(name="Currentlifestatus.findAll", query="SELECT c FROM Currentlifestatus c")
@XmlRootElement
public class Currentlifestatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_currentlifestatus")
	@TableGenerator(name="sqlite_currentlifestatus", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="currentlifestatus")
	@Column(name = "CLSid")
	private int CLSid;
	
	/*@Column(name="CLSid")
	private int CLSid;*/
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateTime")
	private Date  dateTime;

	@Column(name="value")
	private Double value;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MDid", referencedColumnName = "MDid")
	private Measuredefinition measuredefinition;

	//bi-directional many-to-one association to Userprofile
	@ManyToOne
	@JoinColumn(name="UPid",referencedColumnName = "UPid")
	private Userprofile userprofile;

	public Currentlifestatus() {
	}

	public int getCLSid() {
		return this.CLSid;
	}

	public void setCLSid(int CLSid) {
		this.CLSid = CLSid;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

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