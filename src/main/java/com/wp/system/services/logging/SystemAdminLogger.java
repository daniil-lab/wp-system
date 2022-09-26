package com.wp.system.services.logging;

import com.wp.system.dto.logging.SystemAdminLogDTO;
import com.wp.system.entity.logging.SystemAdminLog;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.logging.SystemAdminLogRepository;
import com.wp.system.request.logging.CreateAdminLogRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SystemAdminLogger {
    @Autowired
    private SystemAdminLogRepository systemAdminLogRepository;

    @Autowired
    private UserService userService;

    public List<SystemAdminLog> getAllAdminLogs() {
        return systemAdminLogRepository.findAll().stream().collect(Collectors.toList());
    }

    public PagingResponse<SystemAdminLogDTO> getPagedAdminLogs(int page, int pageSize) {
        return new PagingResponse<>(systemAdminLogRepository.findAll(PageRequest.of(page, pageSize)).stream().map(SystemAdminLogDTO::new).collect(Collectors.toList()),
                getAllAdminLogs().size());
    }

    public SystemAdminLog getAdminLogById(UUID id) {
        Optional<SystemAdminLog> log = systemAdminLogRepository.findById(id);

        if(log.isEmpty())
            throw new ServiceException("Admin log not found", HttpStatus.NOT_FOUND);

        return log.get();
    }

    @Transactional
    public SystemAdminLog removeAdminLog(UUID id) {
        SystemAdminLog log = this.getAdminLogById(id);

        systemAdminLogRepository.delete(log);

        return log;
    }

    public SystemAdminLog createAdminLog(CreateAdminLogRequest request) {
        SystemAdminLog log = new SystemAdminLog(
                request.getAction(),
                request.getDescription(),
                userService.getUserById(request.getAdminId())
        );

        systemAdminLogRepository.save(log);

        return log;
    }
}
