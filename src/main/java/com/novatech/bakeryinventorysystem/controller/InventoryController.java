package com.novatech.bakeryinventorysystem.controller;

import com.novatech.bakeryinventorysystem.model.MovementType;
import com.novatech.bakeryinventorysystem.service.InventoryService;
import com.novatech.bakeryinventorysystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/movement/{productId}")
    public String showMovementForm(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.getProductById(productId));
        return "inventory/movement-form";
    }

    @PostMapping("/movement")
    public String registerMovement(@RequestParam Long productId,
                                   @RequestParam MovementType type,
                                   @RequestParam Integer quantity,
                                   @RequestParam(required = false) String note,
                                   Authentication authentication,
                                   Model model) {
        try {
            inventoryService.registerMovement(productId, type, quantity, authentication.getName(), note);
            return "redirect:/products";
        } catch (RuntimeException e) {
            model.addAttribute("product", productService.getProductById(productId));
            model.addAttribute("error", e.getMessage());
            return "inventory/movement-form";
        }
    }

    @GetMapping("/history")
    public String showHistory(@RequestParam(required = false) Long productId,
                              @RequestParam(required = false) String startDate,
                              @RequestParam(required = false) String endDate,
                              Model model) {
        LocalDateTime start = (startDate != null && !startDate.isBlank())
                ? LocalDate.parse(startDate).atStartOfDay()
                : LocalDateTime.now().minusMonths(1);
        LocalDateTime end = (endDate != null && !endDate.isBlank())
                ? LocalDate.parse(endDate).atTime(23, 59, 59)
                : LocalDateTime.now();

        model.addAttribute("movements", inventoryService.getFilteredMovements(productId, start, end));
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("selectedProductId", productId);
        model.addAttribute("startDate", start.toLocalDate());
        model.addAttribute("endDate", end.toLocalDate());
        return "inventory/history";
    }
}