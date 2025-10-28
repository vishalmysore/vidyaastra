package vishal.mysore.yoga.service;

import vishal.mysore.yoga.model.YogaPractice;
import vishal.mysore.yoga.repository.YogaPracticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class YogaPracticeService {
    private final YogaPracticeRepository yogaPracticeRepository;

    @Autowired
    public YogaPracticeService(YogaPracticeRepository yogaPracticeRepository) {
        this.yogaPracticeRepository = yogaPracticeRepository;
    }

    @Transactional
    public YogaPractice createYogaPractice(YogaPractice yogaPractice) {
        return yogaPracticeRepository.save(yogaPractice);
    }

    @Transactional
    public YogaPractice updateYogaPractice(YogaPractice yogaPractice) {
        return yogaPracticeRepository.save(yogaPractice);
    }

    @Transactional(readOnly = true)
    public List<YogaPractice> getAllYogaPractices() {
        return yogaPracticeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<YogaPractice> getYogaPracticeById(Long id) {
        return yogaPracticeRepository.findById(id);
    }

    @Transactional
    public void deleteYogaPractice(Long id) {
        yogaPracticeRepository.deleteById(id);
    }
}
