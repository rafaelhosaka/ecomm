package com.rafaelhosaka.ecomm.shop;

import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
@RequestMapping("/shop")
@SessionAttributes("buyer")
public class ShopController {
    private final ShopService shopService;
    private final BuyerService buyerService;

    @Autowired
    public ShopController(ShopService shopService, BuyerService buyerService) {
        this.shopService = shopService;
        this.buyerService = buyerService;
    }

    @GetMapping("/showNewShopForm/{id}")
    public String showNewShopForm(@PathVariable("id") Long buyerId, Model model) {
        Buyer buyer = buyerService.getBuyerById(buyerId);
        buyer.setShop(new Shop());
        model
                .addAttribute("title", "Add New Shop")
                .addAttribute("buyer", buyer);
        return "/shop/addForm";
    }

    @PostMapping("/save")
    public String insertBuyer(@ModelAttribute("buyer") Buyer buyer, RedirectAttributes redirectAttributes){
        buyerService.save(buyer);
        redirectAttributes.addFlashAttribute("successAlert","The shop has been opened for "+buyer.getFirstName()+" successfully");
        return "redirect:/buyer/list";
    }

}