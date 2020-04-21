package org.vaadin.activiti.simpletravel.alexdp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.activiti.simpletravel.alexdp.model.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

}
