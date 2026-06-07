package com.ics23043.unitrade.repositories;

import com.ics23043.unitrade.models.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByListingSellerIdOrderBySentAtDesc(Long sellerId);
}
