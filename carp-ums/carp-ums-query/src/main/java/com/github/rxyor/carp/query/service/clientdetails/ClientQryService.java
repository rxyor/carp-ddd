package com.github.rxyor.carp.query.service.clientdetails;

import com.github.rxyor.carp.query.dto.clientdetails.ClientDTO;
import com.github.rxyor.carp.query.qry.clientdetails.ClientQry;
import org.springframework.data.domain.Page;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 22:26:00
 * @since 1.0.0
 */
public interface ClientQryService {

    Page<ClientDTO> page(ClientQry qry);

    ClientDTO find(Long id);
}
