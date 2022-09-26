package com.wp.system.repository.help;

import com.wp.system.entity.help.HelpLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HelpLeadRepository extends JpaRepository<HelpLead, UUID> {
}
