package com.example.demo.bounded_context.questionBoard.repository;

import com.example.demo.bounded_context.questionBoard.entity.QuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long> {

}
