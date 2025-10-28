package vishal.mysore.hc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.hc.model.Condition;
import vishal.mysore.hc.repository.ConditionRepository;

import java.util.List;

@Service
public class ConditionService {
    private final ConditionRepository conditionRepository;

    @Autowired
    public ConditionService(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    public Condition createCondition(Condition condition) {
        return conditionRepository.save(condition);
    }

    public List<Condition> getAllConditions() {
        return conditionRepository.findAll();
    }

    public Condition getConditionByName(String name) {
        return conditionRepository.findByName(name);
    }

    public Condition updateCondition(Condition condition) {
        return conditionRepository.save(condition);
    }

    public void deleteCondition(Long id) {
        conditionRepository.deleteById(id);
    }
}
