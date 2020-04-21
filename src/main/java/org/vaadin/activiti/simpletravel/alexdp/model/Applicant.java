package org.vaadin.activiti.simpletravel.alexdp.model;

import java.io.File;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.vaadin.activiti.simpletravel.alexdp.util.vaadin.WorkflowFormLayout.TextAreaField;
import org.vaadin.activiti.simpletravel.domain.AbstractEntity;

@Entity
public class Applicant extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6716966757695362747L;
//	@Id
//	@GeneratedValue
//	private Long id;


	private String firstname;
	private String lastname;
	private String email;
	private String phoneNumber;
	private File cvFile;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Address address;
	
	@TextAreaField(columns=25, rows=10)
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public Address getAddress() {
		if (address == null) address = new Address();
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	

}
