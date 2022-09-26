package com.wp.system.services.activity;

import com.wp.system.entity.activity.Activity;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.activity.ActivityRepository;
import com.wp.system.request.activity.CreateActivityRequest;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.AuthHelper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthHelper authHelper;

    @Transactional
    public Activity createActivity(CreateActivityRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        Activity activity = new Activity(request.getScreenName(), user, request.getStartTime(), request.getEndTime());

        activity.setSubscription(user.getSubscription());

        activityRepository.save(activity);

        return activity;
    }

    public List<Activity> getActivities() {
        return activityRepository.findAll();
    }

    public Activity getActivityById(UUID id) {
        return activityRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("User activity not found", HttpStatus.NOT_FOUND);
        });
    }

    @Transactional
    public Activity removeActivity(UUID id) {
        Activity activity = getActivityById(id);

        activityRepository.delete(activity);

        return activity;
    }
//
//    public List<Activity> getActivitiesByPeriod(
//            Instant startTime,
//            Instant endTime,
//            UUID subscriptionId,
//            UUID userId
//    ) {
//        if(startTime == null)
//            throw new ServiceException("Pass startTime to request", HttpStatus.BAD_REQUEST);
//
//        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();;
//        CriteriaQuery<Activity> cr = cb.createQuery(Activity.class);
//        Root<Activity> root = cr.from(Activity.class);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        predicates.add(cb.greaterThan(root.get("startTime"), startTime));
//        predicates.add(cb.lessThan(root.get("endTime"), endTime == null ? Instant.now() : endTime));
//
//        if(subscriptionId != null)
//            predicates.add(cb.equal(root.join("subscription").join("variant").get("id"), subscriptionId));
//
//        if(userId != null)
//            predicates.add(cb.equal(root.join("user").get("id"), userId));
//
//        return entityManager.createQuery(cr.select(root).where(predicates.toArray(new Predicate[0]))).getResultList();
//    }

}
