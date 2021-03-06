package com.storage.cameras.dao;

import com.storage.cameras.dao.datajpa.DataJpaCommentRepository;
import com.storage.cameras.model.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CommentDaoImpl implements CommentDao {
    private final DataJpaCommentRepository dataJpaCommentRepository;

    @Override
    public Comment save(final Comment comment) {
        return dataJpaCommentRepository.save(comment);
    }
}
