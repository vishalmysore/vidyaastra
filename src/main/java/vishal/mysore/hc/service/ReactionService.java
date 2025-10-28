package vishal.mysore.hc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.hc.model.Reaction;
import vishal.mysore.hc.repository.ReactionRepository;

import java.util.List;

@Service
public class ReactionService {
    private final ReactionRepository reactionRepository;

    @Autowired
    public ReactionService(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    public Reaction createReaction(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    public List<Reaction> getAllReactions() {
        return reactionRepository.findAll();
    }

    public Reaction getReactionByName(String name) {
        return reactionRepository.findByName(name);
    }

    public Reaction updateReaction(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    public void deleteReaction(Long id) {
        reactionRepository.deleteById(id);
    }
}
