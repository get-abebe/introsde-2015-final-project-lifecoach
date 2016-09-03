package ehealth.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "sqlite_sequence" database table.
 * 
 */
@Entity
@Table(name="\"sqlite_sequence\"")
@NamedQuery(name="SqliteSequence.findAll", query="SELECT s FROM SqliteSequence s")
public class SqliteSequence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"name\"")
	private Object name;

	@Column(name="\"seq\"")
	private Object seq;

	public SqliteSequence() {
	}

	public Object getName() {
		return this.name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getSeq() {
		return this.seq;
	}

	public void setSeq(Object seq) {
		this.seq = seq;
	}

}