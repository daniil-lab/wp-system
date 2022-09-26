package com.wp.system.services.logging;

import com.wp.system.dto.logging.SystemErrorLogDTO;
import com.wp.system.entity.logging.ErrorLogSource;
import com.wp.system.entity.logging.SystemErrorLog;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.logging.SystemErrorLogRepository;
import com.wp.system.request.logging.CreateErrorLogRequest;
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
public class SystemErrorLogger {
    @Autowired
    private SystemErrorLogRepository systemErrorLogRepository;

    public List<SystemErrorLog> getAllErrorLogs() {
        Iterable<SystemErrorLog> foundLogs = systemErrorLogRepository.findAll();
        List<SystemErrorLog> logs = new ArrayList<>();

        foundLogs.forEach(logs::add);

        return logs;
    }

    public SystemErrorLog getErrorLogById(UUID id) {
        Optional<SystemErrorLog> foundLog = systemErrorLogRepository.findById(id);

        if(foundLog.isEmpty())
            throw new ServiceException("Error log not found", HttpStatus.NOT_FOUND);

        return foundLog.get();
    }

    public SystemErrorLog createErrorLog(CreateErrorLogRequest request) {
        SystemErrorLog log = new SystemErrorLog(
                request.getName(),
                request.getSource(),
                request.getAdditional()
        );

        log.setSource(request.getSource());

        systemErrorLogRepository.save(log);

        return log;
    }

    public SystemErrorLog createErrorLog(ServiceException e) {
        SystemErrorLog log = new SystemErrorLog(e);

        log.setSource(ErrorLogSource.BACK);

        systemErrorLogRepository.save(log);

        return log;
    }

    @Transactional
    public SystemErrorLog removeErrorLog(UUID id) {
        SystemErrorLog log = this.getErrorLogById(id);

        systemErrorLogRepository.delete(log);

        return log;
    }

    public PagingResponse<SystemErrorLogDTO> getErrorLogsByPages(int pageSize, int page) {
        return new PagingResponse<>(systemErrorLogRepository.findAll(PageRequest.of(page, pageSize)).stream().map(SystemErrorLogDTO::new).collect(Collectors.toList()),
                getAllErrorLogs().size());
    }
}
