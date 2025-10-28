package vishal.mysore.yoga.service;

import vishal.mysore.yoga.model.YogaTechnique;
import vishal.mysore.yoga.repository.YogaTechniqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class YogaTechniqueService {
    private final YogaTechniqueRepository yogaTechniqueRepository;

    @Autowired
    public YogaTechniqueService(YogaTechniqueRepository yogaTechniqueRepository) {
        this.yogaTechniqueRepository = yogaTechniqueRepository;
    }

    @Transactional
    public YogaTechnique createYogaTechnique(YogaTechnique yogaTechnique) {
        return yogaTechniqueRepository.save(yogaTechnique);
    }

    @Transactional
    public YogaTechnique updateYogaTechnique(YogaTechnique yogaTechnique) {
        return yogaTechniqueRepository.save(yogaTechnique);
    }

    @Transactional(readOnly = true)
    public List<YogaTechnique> getAllYogaTechniques() {
        return yogaTechniqueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<YogaTechnique> getYogaTechniqueById(Long id) {
        return yogaTechniqueRepository.findById(id);
    }

    @Transactional
    public void deleteYogaTechnique(Long id) {
        yogaTechniqueRepository.deleteById(id);
    }
}
