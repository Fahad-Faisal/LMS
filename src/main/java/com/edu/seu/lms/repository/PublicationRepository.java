package com.edu.seu.lms.repository;

import com.edu.seu.lms.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication,Long> {
    Publication findByPublisherName(String publisherName);
}
