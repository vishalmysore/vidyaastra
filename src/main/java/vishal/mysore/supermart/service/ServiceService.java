package vishal.mysore.supermart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.supermart.model.StoreService;
import vishal.mysore.supermart.repository.StoreServiceRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {
    private final StoreServiceRepository storeServiceRepository;

    @Autowired
    public ServiceService(StoreServiceRepository storeServiceRepository) {
        this.storeServiceRepository = storeServiceRepository;
    }

    public StoreService createService(StoreService service) {
        return storeServiceRepository.save(service);
    }

    public List<StoreService> getAllServices() {
        return storeServiceRepository.findAll();
    }

    public StoreService getServiceByName(String name) {
        return storeServiceRepository.findByName(name);
    }

    public StoreService updateService(StoreService service) {
        return storeServiceRepository.save(service);
    }

    public void deleteService(Long id) {
        storeServiceRepository.deleteById(id);
    }
}
