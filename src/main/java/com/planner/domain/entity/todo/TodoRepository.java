package com.planner.domain.entity.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
    @Query(value = "select * from todo where p_no=:pno and s_time like :date%", nativeQuery = true)
    public List<TodoEntity> findByMonth(int pno, String date);
}
