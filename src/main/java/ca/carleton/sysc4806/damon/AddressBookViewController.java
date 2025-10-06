package ca.carleton.sysc4806.damon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AddressBookViewController {
    private final AddressBookRepository abRepo;
    private final BuddyInfoRepository biRepo;

    public AddressBookViewController(AddressBookRepository rep1, BuddyInfoRepository rep2) {
        abRepo = rep1;
        biRepo = rep2;
    }

    @GetMapping("/ab/find/{id}")
    public String viewFind(@PathVariable Long id, Model model){
        AddressBook ab = abRepo.findById(id).orElseThrow();
        model.addAttribute("addressbook", ab);
        return "addressbook";
    }

    @GetMapping("ab/list")
    public String viewList(Model model){
        List<AddressBook> abL = (List<AddressBook>) abRepo.findAll();
        model.addAttribute("addressbooks", abL);
        return "ablist";
    }
}
