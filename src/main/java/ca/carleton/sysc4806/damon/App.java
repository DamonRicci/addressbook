package ca.carleton.sysc4806.damon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import ca.carleton.sysc4806.damon.AddressBookRepository;
import ca.carleton.sysc4806.damon.BuddyInfoRepository;

import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public void searchUntil(BuddyInfoRepository b){
        System.out.print("type exit to close server: ");
        String nameSearch = scanner.nextLine();
        if(nameSearch.toLowerCase().equals("exit")){
            return;
        }
        System.out.println("Found: " + b.findByName(nameSearch));
        searchUntil(b);
        return;
    }
    Scanner scanner = new Scanner(System.in);
    @Bean
    CommandLineRunner demo(AddressBookRepository books, BuddyInfoRepository buddies) {
        return args -> {
            AddressBook ab = new AddressBook();
            AddressBook ab2 = new AddressBook();
            ab2.addBuddy(new BuddyInfo("Damon","5551234"));
            ab2.addBuddy(new BuddyInfo("Alice","124234325"));
            ab2.addBuddy(new BuddyInfo("bob","124234325"));
            ab.addBuddy(new BuddyInfo("Damon","5551234"));
            ab.addBuddy(new BuddyInfo("Dean","5551234674532"));
            ab.addBuddy(new BuddyInfo("Daniel","009450824309"));
            books.save(ab);
            books.save(ab2);

            System.out.println("Books: " + books.count());
            System.out.println("Buddies: " + buddies.count());
            //System.out.println("Find Damon: " + buddies.findByName("Damon"));

            //searchUntil(buddies);
        };
    }
}
