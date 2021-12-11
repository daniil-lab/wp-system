package com.wp.system.services.help;

import com.wp.system.entity.help.HelpLead;
import com.wp.system.repository.help.HelpLeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelpService {
    @Autowired
    private HelpLeadRepository helpLeadRepository;

    public HelpLead createHelpLead()
}
