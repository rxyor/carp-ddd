package com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.criteria;

import com.github.rxyor.carp.query.qry.clientdetails.ClientQry;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dao.ClientDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dataobj.ClientDO;
import com.github.rxyor.carp.ums.shared.common.uitl.PageUtil;
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
 * @date 2020/2/19 周三 21:41:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Repository
public class ClientCriteria {

    private final ClientDAO clientDAO;

    public ClientDAO dao() {
        return clientDAO;
    }

    public Page<ClientDO> selectPage(ClientQry qry) {
        Preconditions.checkArgument(qry != null,
            "参数不能为空");

        Specification<ClientDO> spec = (Specification<ClientDO>) (root, query, cb) -> {
            List<Predicate> conditions = new ArrayList<>(4);
            if (qry.getId() != null) {
                conditions.add(cb.equal(root.get("id"), qry.getId()));
            }
            if (StringUtils.isNotBlank(qry.getClient())) {
                conditions.add(cb.equal(root.get("appId"), qry.getClient()));
            }

            Predicate[] predicates = conditions.toArray(new Predicate[conditions.size()]);
            return cb.and(predicates);
        };

        return dao().findAll(spec, PageUtil.cast(qry));
    }

}
