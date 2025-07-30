package com.seek.client.adapter.mapper;

import com.seek.client.adapter.out.persistence.entity.ClientEntity;
import com.seek.client.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper interface for converting between {@link ClientEntity} and {@link Client} domain model.
 * This mapper is used to isolate persistence layer representations from the domain layer.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {

    /**
     * Singleton instance of the mapper for manual usage if needed.
     */
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    /**
     * Converts a persistence entity into a domain model object.
     *
     * @param entity the {@link ClientEntity} to convert
     * @return the corresponding {@link Client} domain object
     */
    Client toDomain(ClientEntity entity);

    /**
     * Converts a domain model object into a persistence entity.
     *
     * @param domain the {@link Client} domain object to convert
     * @return the corresponding {@link ClientEntity}
     */
    ClientEntity toEntity(Client domain);
}