package ehealth.models;

import java.io.Serializable;

import javax.persistence.*;

import ehealth.model.MeasuredefaultrangePK;

import java.math.BigDecimal;


/**
 * The persistent class for the "measuredefaultrange" database table.
 * 
 */
@Entity
@Table(name="measuredefaultrange")
@NamedQuery(name="Measuredefaultrange.findAll", query="SELECT m FROM Measuredefaultrange m")
public class Measuredefaultrange implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MeasuredefaultrangePK id;
	
	@Column(name="alarmLevel")
	private int alarmLevel;

	@Column(name="endValue")
	private BigDecimal endValue;

	/*@Column(name="MDRid")
	private int MDRid;*/

	@Column(name="rangeName")
	private String rangeName;

	@Column(name="startValue")
	private BigDecimal startValue;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MDid", referencedColumnName="MDid",nullable=false, insertable=false, updatable=false)
	private Measuredefinition measuredefinition;

	public Measuredefaultrange() {
	}

	public int getAlarmLevel() {
		return this.alarmLevel;
	}

	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public BigDecimal getEndValue() {
		return this.endValue;
	}

	public void setEndValue(BigDecimal endValue) {
		this.endValue = endValue;
	}
	public MeasuredefaultrangePK getId() {
		return this.id;
	}

	public void setId(MeasuredefaultrangePK id) {
		this.id = id;
	}
/*
	public int getMDRid() {
		return this.MDRid;
	}

	public void setMDRid(int MDRid) {
		this.MDRid = MDRid;
	}*/

	public String getRangeName() {
		return this.rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	public BigDecimal getStartValue() {
		return this.startValue;
	}

	public void setStartValue(BigDecimal startValue) {
		this.startValue = startValue;
	}

	public Measuredefinition getMeasuredefinition() {
		return this.measuredefinition;
	}

	public void setMeasuredefinition(Measuredefinition measuredefinition) {
		this.measuredefinition = measuredefinition;
	}

}