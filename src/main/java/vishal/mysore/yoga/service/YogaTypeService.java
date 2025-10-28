package vishal.mysore.yoga.service;

import vishal.mysore.yoga.model.YogaType;
import vishal.mysore.yoga.repository.YogaTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class YogaTypeService {
    private final YogaTypeRepository yogaTypeRepository;

    @Autowired
    public YogaTypeService(YogaTypeRepository yogaTypeRepository) {
        this.yogaTypeRepository = yogaTypeRepository;
    }

    @Transactional
    public YogaType createYogaType(YogaType yogaType) {
        return yogaTypeRepository.save(yogaType);
    }

    @Transactional
    public YogaType updateYogaType(YogaType yogaType) {
        return yogaTypeRepository.save(yogaType);
    }

    @Transactional(readOnly = true)
    public List<YogaType> getAllYogaTypes() {
        return yogaTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<YogaType> getYogaTypeById(Long id) {
        return yogaTypeRepository.findById(id);
    }

    @Transactional
    public void deleteYogaType(Long id) {
        yogaTypeRepository.deleteById(id);
    }
}
