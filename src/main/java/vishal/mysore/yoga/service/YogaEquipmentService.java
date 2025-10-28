package vishal.mysore.yoga.service;

import vishal.mysore.yoga.model.YogaEquipment;
import vishal.mysore.yoga.repository.YogaEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class YogaEquipmentService {
    private final YogaEquipmentRepository yogaEquipmentRepository;

    @Autowired
    public YogaEquipmentService(YogaEquipmentRepository yogaEquipmentRepository) {
        this.yogaEquipmentRepository = yogaEquipmentRepository;
    }

    @Transactional
    public YogaEquipment createYogaEquipment(YogaEquipment yogaEquipment) {
        return yogaEquipmentRepository.save(yogaEquipment);
    }

    @Transactional
    public YogaEquipment updateYogaEquipment(YogaEquipment yogaEquipment) {
        return yogaEquipmentRepository.save(yogaEquipment);
    }

    @Transactional(readOnly = true)
    public List<YogaEquipment> getAllYogaEquipment() {
        return yogaEquipmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<YogaEquipment> getYogaEquipmentById(Long id) {
        return yogaEquipmentRepository.findById(id);
    }

    @Transactional
    public void deleteYogaEquipment(Long id) {
        yogaEquipmentRepository.deleteById(id);
    }
}
