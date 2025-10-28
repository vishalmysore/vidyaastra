package vishal.mysore.hc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.hc.model.Diagnosis;
import vishal.mysore.hc.repository.DiagnosisRepository;

import java.util.List;

@Service
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;

    @Autowired
    public DiagnosisService(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    public Diagnosis getDiagnosisByName(String name) {
        return diagnosisRepository.findByName(name);
    }

    public Diagnosis updateDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    public void deleteDiagnosis(Long id) {
        diagnosisRepository.deleteById(id);
    }
}
