package ca.carleton.sysc4806.damon;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class AddressBookRESTController {
    private final AddressBookRepository abRepo;
    private final BuddyInfoRepository biRepo;

    public AddressBookRESTController(AddressBookRepository rep1, BuddyInfoRepository rep2) {
        abRepo = rep1;
        biRepo = rep2;
    }

    @PostMapping("/ab/add/{id}")
    public AddressBook addBuddyToAB(@PathVariable Long id, @RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber){
        AddressBook ab = abRepo.findById(id).orElseThrow();
        ab.addBuddy(name, phoneNumber);
        return abRepo.save(ab);
    }

    @PostMapping("/ab/new")
    public AddressBook newAB(){
        AddressBook ab = new AddressBook();
        return abRepo.save(ab);
    }

    @PostMapping("/ab/remove/{id}")
    public AddressBook newAB(@PathVariable Long id){
        AddressBook ab = abRepo.findById(id).orElseThrow();
        abRepo.delete(ab);
        return ab;
    }

    @DeleteMapping("/ab/remove/{id}")
    public Boolean newABname(@PathVariable Long id, @RequestParam("name") String name){
        AddressBook ab = abRepo.findById(id).orElseThrow();
        var re = ab.existsBuddy(name);
        if(re){abRepo.delete(ab);}
        return re;
    }
}
