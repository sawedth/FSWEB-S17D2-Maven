package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.Experience;
import com.workintech.s17d2.tax.DeveloperTax;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/developers")
public class DeveloperController {
    public Map<Integer, Developer> developers= new HashMap<>();
    private Taxable taxable;
    @PostConstruct
    public void init(){

    }

    @Autowired
    public DeveloperController(Taxable taxable){
        this.taxable = taxable;
    }

    @GetMapping
    public List<Developer> developerList(){
        return developers.values().stream().toList();
    }
    @GetMapping("/sa")
    public List<Developer> developerListaaa(){
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Developer developerId(@PathVariable Integer id){
        if(developers.containsKey(id)){
            return developers.get(id);
        }else {
            return null;
        }
    }

    @PostMapping
    public Developer newDev(@RequestBody Developer developer){
        if(developer.getExperience() == Experience.JUNIOR){
            double newSalary = (1-taxable.getSimpleTaxRate()) * developer.getSalary();
            Developer modifiedDev = new Developer(developer.getId(), developer.getName(), newSalary, developer.getExperience());
            developers.put(developer.getId(), modifiedDev);
            return modifiedDev;
        }else if (developer.getExperience() == Experience.MID){
            double newSalary = (1-taxable.getMiddleTaxRate()) * developer.getSalary();
            Developer modifiedDev = new Developer(developer.getId(), developer.getName(), newSalary, developer.getExperience());
            developers.put(developer.getId(), modifiedDev);
            return modifiedDev;
        }else {
            double newSalary = (1-taxable.getUpperTaxRate()) * developer.getSalary();
            Developer modifiedDev = new Developer(developer.getId(), developer.getName(), newSalary, developer.getExperience());
            developers.put(developer.getId(), modifiedDev);
            return modifiedDev;
        }
    }

    @PutMapping("/{id}")
    public Developer updateDev(@PathVariable Integer id,@RequestBody Developer developer){
        developers.put(id, developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    public Developer deleteDev(@PathVariable Integer id){
        Developer deleted = developers.get(id);
        developers.remove(id);
        return deleted;
    }


}
