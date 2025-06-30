package FoodSaver.foodsaver.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import FoodSaver.foodsaver.JPA.SQL.userLogin;

@Repository
public interface userLoginJPA extends JpaRepository<userLogin, Integer> {

    userLogin findByEmail(String email);

    boolean existsByEmail(String email);
}
