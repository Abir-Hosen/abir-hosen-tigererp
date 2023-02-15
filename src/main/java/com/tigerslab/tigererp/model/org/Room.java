package com.tigerslab.tigererp.model.org;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigerslab.tigererp.model.AbstractData;

@Entity
@Table(name="room")
public class Room  extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "roomIdGenerator", sequenceName = "roomSeq")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "roomName")
	private String roomName;
	
	@Column(name = "alius")
	private String alius;
	
	@Column(name = "roomNumber")
	private String roomNumber;
	
	@Column(name = "roomDescription")
	private String roomDescription;

	@JsonIgnoreProperties(value= {"rooms"}, allowSetters = true)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn
	private Branch branch;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getAlius() {
		return alius;
	}

	public void setAlius(String alius) {
		this.alius = alius;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

//	@Override
//	public String toString() {
//		return "Room [id=" + id + ", roomName=" + roomName + ", alius=" + alius + ", roomNumber=" + roomNumber
//				+ ", roomDescription=" + roomDescription + ", branch=" + branch + "]";
//	}
}
