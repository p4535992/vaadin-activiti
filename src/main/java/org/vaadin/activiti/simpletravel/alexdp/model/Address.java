package org.vaadin.activiti.simpletravel.alexdp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.vaadin.activiti.simpletravel.domain.AbstractEntity;

@Entity
public class Address extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8046000038553626918L;
	
//	@Id
//	@GeneratedValue
//	private Long id;

	private String street;
	private String zipcode;
	private String country;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
