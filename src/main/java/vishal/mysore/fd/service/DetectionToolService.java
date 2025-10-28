package vishal.mysore.fd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.fd.model.DetectionTool;
import vishal.mysore.fd.repository.DetectionToolRepository;

import java.util.List;

@Service
public class DetectionToolService {
    private final DetectionToolRepository detectionToolRepository;

    @Autowired
    public DetectionToolService(DetectionToolRepository detectionToolRepository) {
        this.detectionToolRepository = detectionToolRepository;
    }

    public DetectionTool createDetectionTool(DetectionTool detectionTool) {
        return detectionToolRepository.save(detectionTool);
    }

    public List<DetectionTool> getAllDetectionTools() {
        return detectionToolRepository.findAll();
    }

    public DetectionTool getDetectionToolByName(String name) {
        return detectionToolRepository.findByName(name);
    }

    public DetectionTool updateDetectionTool(DetectionTool detectionTool) {
        return detectionToolRepository.save(detectionTool);
    }

    public void deleteDetectionTool(Long id) {
        detectionToolRepository.deleteById(id);
    }
}
