package vishal.mysore.fd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.fd.model.FraudType;
import vishal.mysore.fd.repository.FraudTypeRepository;

import java.util.List;

@Service
public class FraudTypeService {
    private final FraudTypeRepository fraudTypeRepository;

    @Autowired
    public FraudTypeService(FraudTypeRepository fraudTypeRepository) {
        this.fraudTypeRepository = fraudTypeRepository;
    }

    public FraudType createFraudType(FraudType fraudType) {
        return fraudTypeRepository.save(fraudType);
    }

    public List<FraudType> getAllFraudTypes() {
        return fraudTypeRepository.findAll();
    }

    public FraudType getFraudTypeByName(String name) {
        return fraudTypeRepository.findByName(name);
    }

    public FraudType updateFraudType(FraudType fraudType) {
        return fraudTypeRepository.save(fraudType);
    }

    public void deleteFraudType(Long id) {
        fraudTypeRepository.deleteById(id);
    }
}
