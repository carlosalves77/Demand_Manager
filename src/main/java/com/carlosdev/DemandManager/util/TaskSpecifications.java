package com.carlosdev.DemandManager.util;

import com.carlosdev.DemandManager.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class TaskSpecifications {

    public static Specification<Task> hasStatus(String status) {

        return ((root, query, criteriaBuilder) ->
        {
            if (!StringUtils.hasText(status)) {
                criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.upper(root.get("status")), status.toUpperCase());
        }
        );
    }

    public static Specification<Task> hasPriority(String priority) {

        return ((root, query, criteriaBuilder) ->
        {
            if (!StringUtils.hasText(priority)) {
                criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.upper(root.get("priority")), priority.toUpperCase()
            );
        }
        );
    }

    public static Specification<Task> hasProjectId(UUID projectId) {

        return ((root, query, criteriaBuilder) ->
        {
            if (projectId == null) {
                criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("project").get("id"), projectId);
        }
        );
    }


}
