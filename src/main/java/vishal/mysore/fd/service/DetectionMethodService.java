package vishal.mysore.fd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.fd.model.DetectionMethod;
import vishal.mysore.fd.repository.DetectionMethodRepository;

import java.util.List;

@Service
public class DetectionMethodService {
    private final DetectionMethodRepository detectionMethodRepository;

    @Autowired
    public DetectionMethodService(DetectionMethodRepository detectionMethodRepository) {
        this.detectionMethodRepository = detectionMethodRepository;
    }

    public DetectionMethod createDetectionMethod(DetectionMethod detectionMethod) {
        return detectionMethodRepository.save(detectionMethod);
    }

    public List<DetectionMethod> getAllDetectionMethods() {
        return detectionMethodRepository.findAll();
    }

    public DetectionMethod getDetectionMethodByName(String name) {
        return detectionMethodRepository.findByName(name);
    }

    public DetectionMethod updateDetectionMethod(DetectionMethod detectionMethod) {
        return detectionMethodRepository.save(detectionMethod);
    }

    public void deleteDetectionMethod(Long id) {
        detectionMethodRepository.deleteById(id);
    }
}
