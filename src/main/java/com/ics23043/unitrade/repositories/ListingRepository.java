package com.ics23043.unitrade.repositories;

import com.ics23043.unitrade.models.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByStatus(String status);
    List<Listing> findByCategoryId(Long categoryId);
    
    List<Listing> findBySellerIdOrderByPostedAtDesc(Long sellerId);
    
    @Query("SELECT l FROM Listing l WHERE l.status = 'AVAILABLE' " +
           "AND (:categoryId IS NULL OR l.category.id = :categoryId) " +
           "AND (:search IS NULL OR :search = '' OR LOWER(l.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(l.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY l.postedAt DESC")
    List<Listing> searchListings(@Param("search") String search, @Param("categoryId") Long categoryId);
}