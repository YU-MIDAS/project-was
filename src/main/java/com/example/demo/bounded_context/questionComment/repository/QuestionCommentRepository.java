package com.example.demo.bounded_context.questionComment.repository;

import com.example.demo.bounded_context.questionComment.entity.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {
}
