package pl.edu.pjwstk.s20309.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s20309.entity.Comment;
import pl.edu.pjwstk.s20309.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment getOneComment(Long id){
        return commentRepository.getOne(id);
    }

    public void deleteById(Long id){
        commentRepository.deleteById(id);
    }

    public List<Comment> getCommentsByTask(){
        return commentRepository.getAllByTask();
    }
}
