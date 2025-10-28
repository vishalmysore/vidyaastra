package vishal.mysore.fd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.fd.model.FraudIndicator;
import vishal.mysore.fd.repository.FraudIndicatorRepository;

import java.util.List;

@Service
public class FraudIndicatorService {
    private final FraudIndicatorRepository fraudIndicatorRepository;

    @Autowired
    public FraudIndicatorService(FraudIndicatorRepository fraudIndicatorRepository) {
        this.fraudIndicatorRepository = fraudIndicatorRepository;
    }

    public FraudIndicator createFraudIndicator(FraudIndicator fraudIndicator) {
        return fraudIndicatorRepository.save(fraudIndicator);
    }

    public List<FraudIndicator> getAllFraudIndicators() {
        return fraudIndicatorRepository.findAll();
    }

    public FraudIndicator getFraudIndicatorByName(String name) {
        return fraudIndicatorRepository.findByName(name);
    }

    public FraudIndicator updateFraudIndicator(FraudIndicator fraudIndicator) {
        return fraudIndicatorRepository.save(fraudIndicator);
    }

    public void deleteFraudIndicator(Long id) {
        fraudIndicatorRepository.deleteById(id);
    }
}
