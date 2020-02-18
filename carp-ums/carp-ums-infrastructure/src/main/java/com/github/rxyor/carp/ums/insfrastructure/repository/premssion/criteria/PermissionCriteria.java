package com.github.rxyor.carp.ums.insfrastructure.repository.premssion.criteria;

import com.github.rxyor.carp.query.qry.permission.PermissionQry;
import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dao.PermissionDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dataobj.PermissionDO;
import com.github.rxyor.carp.ums.shared.common.uitl.PageUtil;
import com.github.rxyor.carp.ums.shared.common.uitl.SqlUtil;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/18 周二 14:49:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Repository
public class PermissionCriteria {

    private final PermissionDAO permissionDAO;

    public PermissionDAO dao() {
        return permissionDAO;
    }

    public Page<PermissionDO> selectPage(PermissionQry qry){
        Preconditions.checkArgument(qry != null,
            "查询参数不能为空");

        Specification<PermissionDO> spec = (Specification<PermissionDO>) (root, query, cb) -> {
            List<Predicate> conditions = new ArrayList<>(4);
            if (qry.getId() != null) {
                conditions.add(cb.equal(root.get("id"), qry.getId()));
            }
            if (qry.getDisable() != null) {
                conditions.add(cb.equal(root.get("disable"), qry.getDisable()));
            }
            if (StringUtils.isNotBlank(qry.getPermissionName())) {
                conditions.add(cb.like(root.get("permissionName"), SqlUtil.allLike(qry.getPermissionName())));
            }
            if (StringUtils.isNotBlank(qry.getPermissionCode())) {
                conditions.add(cb.like(root.get("permissionCode"), SqlUtil.allLike(qry.getPermissionCode())));
            }

            Predicate[] predicates = conditions.toArray(new Predicate[conditions.size()]);
            return cb.and(predicates);
        };

        return dao().findAll(spec, PageUtil.cast(qry));
    }

}
