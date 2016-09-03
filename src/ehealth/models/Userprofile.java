package ehealth.models;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import ehealth.model.Caregiver;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the "userprofile" database table.
 * 
 */
@Entity
@Table(name="userprofile")
@NamedQuery(name="Userprofile.findAll", query="SELECT u FROM Userprofile u")
public class Userprofile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_userprofile")
	@TableGenerator(name="sqlite_userprofile", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="userprofile")
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="UPid")
	private int UPid;
	
	@Column(name="address")
	private String address;

	@Temporal(TemporalType.DATE)
	@Column(name="birthdate")
	private Date birthdate;

	@Column(name="CGorUP")
	private int CGorUP;

	@Column(name="city")
	private String city;

	@Column(name="country")
	private String country;

	@Column(name="email")
	private String email;

	@Column(name="fname")
	private String fname;

	@Column(name="guid")
	private String guid;

	@Column(name="lname")
	private String lname;

	@Column(name="password")
	private String password;

	/*@Column(name="UPid")
	private int UPid;
*/
	@Column(name="username")
	private String username;

	//bi-directional many-to-one association to Advisor
	@OneToMany(mappedBy="userprofile" )
	private List<Advisor> advisors;

	/*//bi-directional many-to-one association to Caregiver
	@OneToMany(mappedBy="userprofile")
	private List<Caregiver> caregivers1;
	
	//bi-directional many-to-one association to Caregiver
	@OneToMany(mappedBy="userprofile")
	private List<Caregiver> caregivers2;*/

	//bi-directional many-to-one association to Currentlifestatus
	@OneToMany(mappedBy="userprofile")
	private List<Currentlifestatus> currentlifestatuses;

	//bi-directional many-to-one association to Goalsetting
	@OneToMany(mappedBy="userprofile")
	private List<Goalsetting> goalsettings;

	//bi-directional many-to-one association to Healthmeasurehistory
	@OneToMany(mappedBy="userprofile")
	private List<Healthmeasurehistory> healthmeasurehistories;

	public Userprofile() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public int getCGorUP() {
		return this.CGorUP;
	}

	public void setCGorUP(int CGorUP) {
		this.CGorUP = CGorUP;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUPid() {
		return this.UPid;
	}

	public void setUPid(int UPid) {
		this.UPid = UPid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		advisor.setUserprofile(this);

		return advisor;
	}

	public Advisor removeAdvisor(Advisor advisor) {
		getAdvisors().remove(advisor);
		advisor.setUserprofile(null);

		return advisor;
	}
	/*@XmlTransient
	public List<Caregiver> getCaregivers1() {
		return this.caregivers1;
	}

	public void setCaregivers1(List<Caregiver> caregivers1) {
		this.caregivers1 = caregivers1;
	}

	public Caregiver addCaregivers1(Caregiver caregivers1) {
		getCaregivers1().add(caregivers1);
		caregivers1.setUserprofile1(null);

		return caregivers1;
	}

	public Caregiver removeCaregivers1(Caregiver caregivers1) {
		getCaregivers1().remove(caregivers1);
		caregivers1.setUserprofile1(null);

		return caregivers1;
	}
	@XmlTransient
	public List<Caregiver> getCaregivers2() {
		return this.caregivers2;
	}

	public void setCaregivers2(List<Caregiver> caregivers2) {
		this.caregivers2 = caregivers2;
	}

	public Caregiver addCaregivers2(Caregiver caregivers2) {
		getCaregivers2().add(caregivers2);
		caregivers2.setUserprofile2(null);

		return caregivers2;
	}

	public Caregiver removeCaregivers2(Caregiver caregivers2) {
		getCaregivers2().remove(caregivers2);
		caregivers2.setUserprofile2(null);

		return caregivers2;
	}*/
	@XmlTransient
	public List<Currentlifestatus> getCurrentlifestatuses() {
		return this.currentlifestatuses;
	}

	public void setCurrentlifestatuses(List<Currentlifestatus> currentlifestatuses) {
		this.currentlifestatuses = currentlifestatuses;
	}

	public Currentlifestatus addCurrentlifestatus(Currentlifestatus currentlifestatus) {
		getCurrentlifestatuses().add(currentlifestatus);
		currentlifestatus.setUserprofile(this);

		return currentlifestatus;
	}

	public Currentlifestatus removeCurrentlifestatus(Currentlifestatus currentlifestatus) {
		getCurrentlifestatuses().remove(currentlifestatus);
		currentlifestatus.setUserprofile(null);

		return currentlifestatus;
	}
	@XmlTransient
	public List<Goalsetting> getGoalsettings() {
		return this.goalsettings;
	}

	public void setGoalsettings(List<Goalsetting> goalsettings) {
		this.goalsettings = goalsettings;
	}

	public Goalsetting addGoalsetting(Goalsetting goalsetting) {
		getGoalsettings().add(goalsetting);
		goalsetting.setUserprofile(this);

		return goalsetting;
	}

	public Goalsetting removeGoalsetting(Goalsetting goalsetting) {
		getGoalsettings().remove(goalsetting);
		goalsetting.setUserprofile(null);

		return goalsetting;
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
		healthmeasurehistory.setUserprofile(this);

		return healthmeasurehistory;
	}

	public Healthmeasurehistory removeHealthmeasurehistory(Healthmeasurehistory healthmeasurehistory) {
		getHealthmeasurehistories().remove(healthmeasurehistory);
		healthmeasurehistory.setUserprofile(null);

		return healthmeasurehistory;
	}

}