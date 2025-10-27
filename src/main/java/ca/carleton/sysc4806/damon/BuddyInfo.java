package ca.carleton.sysc4806.damon;

import jakarta.persistence.*;
import static java.util.Objects.hash;
@Entity
public class BuddyInfo {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String phoneNumber;


    public BuddyInfo(String n, String p){
        name = n;
        phoneNumber = p;
    }

    public BuddyInfo(){
    }


    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public void setName(String newName){
        name = newName;
    }
    public void setPhoneNumber(String newNumber){
        phoneNumber = newNumber;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        //System.out.println(Name + " -- " + phoneNumber);
        return name + " -- " + phoneNumber;
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof BuddyInfo)) return false;
        BuddyInfo that=(BuddyInfo)o;
        return java.util.Objects.equals(name, that.name)
                && java.util.Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return hash(name, phoneNumber);
    }
}
