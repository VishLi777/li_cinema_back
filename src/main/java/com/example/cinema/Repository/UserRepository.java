package com.example.cinema.Repository;

import com.example.cinema.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    @Query(value = """
            select cu.id, cu.email, cu.name, cu.password
            \tfrom ci_users cu\s
            \tJOIN ci_order co ON co.user_id = cu.id\s
            \twhere co.id = :orderId""", nativeQuery = true)
    UserEntity findByOrderId(@Param("orderId") Long orderId);
}
