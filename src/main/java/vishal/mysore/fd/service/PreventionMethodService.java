package vishal.mysore.fd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.fd.model.PreventionMethod;
import vishal.mysore.fd.repository.PreventionMethodRepository;

import java.util.List;

@Service
public class PreventionMethodService {
    private final PreventionMethodRepository preventionMethodRepository;

    @Autowired
    public PreventionMethodService(PreventionMethodRepository preventionMethodRepository) {
        this.preventionMethodRepository = preventionMethodRepository;
    }

    public PreventionMethod createPreventionMethod(PreventionMethod preventionMethod) {
        return preventionMethodRepository.save(preventionMethod);
    }

    public List<PreventionMethod> getAllPreventionMethods() {
        return preventionMethodRepository.findAll();
    }

    public PreventionMethod getPreventionMethodByName(String name) {
        return preventionMethodRepository.findByName(name);
    }

    public PreventionMethod updatePreventionMethod(PreventionMethod preventionMethod) {
        return preventionMethodRepository.save(preventionMethod);
    }

    public void deletePreventionMethod(Long id) {
        preventionMethodRepository.deleteById(id);
    }
}
