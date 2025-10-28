package vishal.mysore.yoga.service;

import vishal.mysore.yoga.model.YogaBenefit;
import vishal.mysore.yoga.repository.YogaBenefitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class YogaBenefitService {
    private final YogaBenefitRepository yogaBenefitRepository;

    @Autowired
    public YogaBenefitService(YogaBenefitRepository yogaBenefitRepository) {
        this.yogaBenefitRepository = yogaBenefitRepository;
    }

    @Transactional
    public YogaBenefit createYogaBenefit(YogaBenefit yogaBenefit) {
        return yogaBenefitRepository.save(yogaBenefit);
    }

    @Transactional
    public YogaBenefit updateYogaBenefit(YogaBenefit yogaBenefit) {
        return yogaBenefitRepository.save(yogaBenefit);
    }

    @Transactional(readOnly = true)
    public List<YogaBenefit> getAllYogaBenefits() {
        return yogaBenefitRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<YogaBenefit> getYogaBenefitById(Long id) {
        return yogaBenefitRepository.findById(id);
    }

    @Transactional
    public void deleteYogaBenefit(Long id) {
        yogaBenefitRepository.deleteById(id);
    }
}
