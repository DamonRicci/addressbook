package ca.carleton.sysc4806.damon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/ab")
    public String createAb() {
        AddressBook ab = abRepo.save(new AddressBook());
        return "redirect:/ab/find/" + ab.getId();
    }

    @PostMapping("/ab/{id}/buddy")
    public String addBuddy(@PathVariable Long id,
                           @RequestParam String name,
                           @RequestParam String phone) {
        AddressBook ab = abRepo.findById(id).orElseThrow();
        ab.addBuddy(new BuddyInfo(name, phone));
        abRepo.save(ab);
        return "redirect:/ab/find/" + id;
    }

    @PostMapping("/ab/{id}/buddy/{buddyId}/delete")
    public String removeBuddy(@PathVariable Long id, @PathVariable Long buddyId) {
        AddressBook ab = abRepo.findById(id).orElseThrow();
        ab.removeBuddyById(buddyId);
        abRepo.save(ab);
        return "redirect:/ab/find/" + id;
    }

    @PostMapping("/ab/{id}/delete")
    public String deleteAb(@PathVariable Long id) {
        abRepo.deleteById(id);
        return "redirect:/ab/list";
    }

}
