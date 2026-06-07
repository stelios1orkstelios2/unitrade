package com.ics23043.unitrade.controllers;

import com.ics23043.unitrade.models.Listing;
import com.ics23043.unitrade.models.User;
import com.ics23043.unitrade.repositories.CategoryRepository;
import com.ics23043.unitrade.repositories.ListingRepository;
import com.ics23043.unitrade.repositories.UserRepository;
import com.ics23043.unitrade.services.FileStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class ListingController {
    private final ListingRepository listingRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public ListingController(ListingRepository listingRepository, CategoryRepository categoryRepository, UserRepository userRepository, FileStorageService fileStorageService) {
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/")
    public String homePage(@RequestParam(value = "search", required = false) String search,
                           @RequestParam(value = "category", required = false) Long categoryId,
                           Model model) {
        List<Listing> filteredListings = listingRepository.searchListings(search, categoryId);
        model.addAttribute("listings", filteredListings);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("searchQuery", search);
        return "index";
    }

    @GetMapping("/new-listing")
    public String showCreateListingForm(Model model) {
        model.addAttribute("listing", new Listing());
        model.addAttribute("categories", categoryRepository.findAll());
        return "create-listing"; 
    }

    @PostMapping("/new-listing")
    public String saveListing(@ModelAttribute Listing listing, 
                              @RequestParam("imageFile") MultipartFile file,
                              Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        try {
            if (!file.isEmpty()) {
                String fileName = fileStorageService.saveImage(file);
                listing.setImageUrl("/uploads/" + fileName); 
            }

            User currentUser = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
            listing.setSeller(currentUser);

            listingRepository.save(listing);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/my-listings")
    public String myListings(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));
        List<Listing> userListings = listingRepository.findBySellerIdOrderByPostedAtDesc(currentUser.getId());
        model.addAttribute("listings", userListings);
        return "my-listings";
    }

    @PostMapping("/listings/{id}/sold")
    public String markAsSold(@PathVariable("id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        if (listing.getSeller().getEmail().equals(principal.getName())) {
            listing.setStatus("SOLD");
            listingRepository.save(listing);
        }
        return "redirect:/my-listings";
    }

    @PostMapping("/listings/{id}/delete")
    public String deleteListing(@PathVariable("id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        if (listing.getSeller().getEmail().equals(principal.getName())) {
            listingRepository.delete(listing);
        }
        return "redirect:/my-listings";
    }
}