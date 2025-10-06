package ca.carleton.sysc4806.damon;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long> {
    List<BuddyInfo> findByName(String name);
}
