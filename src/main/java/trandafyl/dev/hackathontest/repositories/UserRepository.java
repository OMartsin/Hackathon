package trandafyl.dev.hackathontest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import trandafyl.dev.hackathontest.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
