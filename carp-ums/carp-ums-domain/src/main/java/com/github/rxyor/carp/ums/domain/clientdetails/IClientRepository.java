package com.github.rxyor.carp.ums.domain.clientdetails;

public interface IClientRepository  {

    Client find(Long id);

    void save(Client client);

    void update(Client client);

    void delete(Long id);
}