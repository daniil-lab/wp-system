package com.wp.system.services.help;

import com.wp.system.entity.help.HelpLead;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.help.HelpErrorCode;
import com.wp.system.repository.help.HelpLeadRepository;
import com.wp.system.request.help.CreateHelpLeadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HelpService {
    @Autowired
    private HelpLeadRepository helpLeadRepository;

    public HelpLead createHelpLead(CreateHelpLeadRequest request) {
        HelpLead lead = new HelpLead(request.getPhone(), request.getEmail(), request.getContent());

        helpLeadRepository.save(lead);

        return lead;
    }

    public List<HelpLead> getAllHelpLeads() {
        Iterable<HelpLead> foundLeads = this.helpLeadRepository.findAll();
        List<HelpLead> leads = new ArrayList<>();

        foundLeads.forEach(leads::add);

        return leads;
    }

    public HelpLead getHeldLeadById(UUID id) {
        Optional<HelpLead> foundLead = this.helpLeadRepository.findById(id);

        if(foundLead.isEmpty())
            throw new ServiceException(HelpErrorCode.NOT_FOUND);

        return foundLead.get();
    }

    @Transactional
    public HelpLead removeHelpLead(UUID id) {
        HelpLead lead = this.getHeldLeadById(id);

        this.helpLeadRepository.delete(lead);

        return lead;
    }
}
