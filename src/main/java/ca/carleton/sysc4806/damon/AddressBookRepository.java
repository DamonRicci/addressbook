package ca.carleton.sysc4806.damon;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {
}
