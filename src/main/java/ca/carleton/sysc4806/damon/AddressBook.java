package ca.carleton.sysc4806.damon;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id @GeneratedValue
    private Long id;
    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "addressbook_id")
    private List<BuddyInfo> buddylist;

    public AddressBook(){
        buddylist = new ArrayList<>();
    }
    public void addBuddy(BuddyInfo buddy){
        buddylist.add(buddy);
    }

    public void addBuddy(String name, String phoneNumber){
        BuddyInfo newB = new BuddyInfo(name, phoneNumber);
        buddylist.add(newB);
    }

    public Boolean existsBuddy(String name){
        for(BuddyInfo buddy : buddylist){
            if(buddy.getName().toLowerCase() == name.toLowerCase()){
                return true;
            }
        }
        return false;
    }
    public boolean removeBuddy(BuddyInfo buddy){
        if(buddylist.contains(buddy)){
            buddylist.remove(buddy);
            return true;
        } else {
            return false;
        }
    }

    public void printBuddies(){
        System.out.println(buddylist);
    }

    public List<BuddyInfo> getBuddies(){
        return buddylist;
    }

    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }


    public void removeBuddyById(Long id){
        buddylist.removeIf(b -> b.getId().equals(id));
    }
}
