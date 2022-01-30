package com.wp.system.services.help;

import com.wp.system.dto.help.HelpLeadDTO;
import com.wp.system.entity.help.HelpLead;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.help.HelpLeadRepository;
import com.wp.system.request.help.CreateHelpLeadRequest;
import com.wp.system.response.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HelpService {
    @Autowired
    private HelpLeadRepository helpLeadRepository;

    public PagingResponse<HelpLeadDTO> getHelpLeadsByPages(int page, int pageSize) {
        return new PagingResponse<>(helpLeadRepository.findAll(PageRequest.of(page, pageSize)).stream().map(HelpLeadDTO::new).collect(Collectors.toList()),
            getAllHelpLeads().size());
    }

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
            throw new ServiceException("Help Lead not found", HttpStatus.NOT_FOUND);

        return foundLead.get();
    }

    @Transactional
    public HelpLead removeHelpLead(UUID id) {
        HelpLead lead = this.getHeldLeadById(id);

        this.helpLeadRepository.delete(lead);

        return lead;
    }
}
