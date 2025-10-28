package vishal.mysore.hc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vishal.mysore.hc.model.Procedure;
import vishal.mysore.hc.repository.ProcedureRepository;

import java.util.List;

@Service
public class ProcedureService {
    private final ProcedureRepository procedureRepository;

    @Autowired
    public ProcedureService(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    public Procedure createProcedure(Procedure procedure) {
        return procedureRepository.save(procedure);
    }

    public List<Procedure> getAllProcedures() {
        return procedureRepository.findAll();
    }

    public Procedure getProcedureByName(String name) {
        return procedureRepository.findByName(name);
    }

    public Procedure updateProcedure(Procedure procedure) {
        return procedureRepository.save(procedure);
    }

    public void deleteProcedure(Long id) {
        procedureRepository.deleteById(id);
    }
}
