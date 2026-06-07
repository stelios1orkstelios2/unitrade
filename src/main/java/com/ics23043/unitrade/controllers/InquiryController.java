package com.ics23043.unitrade.controllers;

import com.ics23043.unitrade.models.Inquiry;
import com.ics23043.unitrade.models.Listing;
import com.ics23043.unitrade.models.User;
import com.ics23043.unitrade.repositories.InquiryRepository;
import com.ics23043.unitrade.repositories.ListingRepository;
import com.ics23043.unitrade.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class InquiryController {

    private final InquiryRepository inquiryRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public InquiryController(InquiryRepository inquiryRepository, ListingRepository listingRepository, UserRepository userRepository) {
        this.inquiryRepository = inquiryRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/listings/{id}/inquire")
    public String sendInquiry(@PathVariable("id") Long listingId,
                              @RequestParam("messageText") String messageText,
                              Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User buyer = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        // Do not allow students to inquire about their own listings
        if (listing.getSeller().getId().equals(buyer.getId())) {
            return "redirect:/?error=cannot_inquire_own";
        }

        Inquiry inquiry = new Inquiry();
        inquiry.setBuyer(buyer);
        inquiry.setListing(listing);
        inquiry.setMessageText(messageText);

        inquiryRepository.save(inquiry);

        return "redirect:/?inquirySent=true";
    }

    @GetMapping("/inquiries")
    public String showInbox(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

        List<Inquiry> receivedInquiries = inquiryRepository.findByListingSellerIdOrderBySentAtDesc(currentUser.getId());
        model.addAttribute("inquiries", receivedInquiries);

        return "inquiries";
    }
}
