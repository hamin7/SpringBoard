package com.example.board.repository;

import com.example.board.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Board b set b.readcnt=b.readcnt+1 where b.num=:num")
    public int readCntInc(@Param("num") Integer num);
}
