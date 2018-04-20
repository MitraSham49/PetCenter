package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    PetRepository petRepo;

     @RequestMapping("/")
    public String showIndex (Model model){
         model.addAttribute("inventory", petRepo.findAll());
       return"index"  ;
     }

     @RequestMapping("/add")
    public  String addPet(Model model){
         model.addAttribute( "aPet", new Pet());
           return"addPet" ;
     }

     @RequestMapping("/savepet")
    public  String savePet( @Valid @ModelAttribute ("aPet") Pet toSave, BindingResult result){
    if (result.hasErrors()){
        return"addpet";
    }
     petRepo.save (toSave);
    return "redirect:/";
     }


    @RequestMapping("/changestatus/{id}")

    public String borrowReturn(@PathVariable("id") long id) {
         Pet thisPet = petRepo.findById(id).get();
         thisPet.setLost(!thisPet.isLost());
         petRepo.save(thisPet);
         return "redirect:/";
    }


    @RequestMapping("/update/{id}")
    public String updatePet(@PathVariable ("id") long id, Model model) {
        model.addAttribute("aPet", petRepo.findById(id).get());
        return "addpet";
    }
    @RequestMapping("/detail/{id}")
    public String showList(@PathVariable("id")long id, Model model){
        model.addAttribute("aPet", petRepo.findById(id).get());
         return"redirect:/";
    }
}
