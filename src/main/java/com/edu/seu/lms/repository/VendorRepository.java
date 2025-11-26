package com.edu.seu.lms.repository;

import com.edu.seu.lms.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public interface VendorRepository extends JpaRepository<Vendor,Long> {
}
