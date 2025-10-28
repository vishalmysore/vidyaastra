package vishal.mysore.hc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.hc.model.Symptom;
import vishal.mysore.hc.repository.SymptomRepository;

import java.util.List;

@Service
public class SymptomService {
    private final SymptomRepository symptomRepository;

    @Autowired
    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    public Symptom createSymptom(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    public List<Symptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }

    public Symptom getSymptomByName(String name) {
        return symptomRepository.findByName(name);
    }

    public Symptom updateSymptom(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    public void deleteSymptom(Long id) {
        symptomRepository.deleteById(id);
    }
}
