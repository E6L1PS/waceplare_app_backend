package com.itacademy.waceplare.mapper;

import java.util.List;

/**
 * Создан: 16.03.2024.
 *
 * @author Pesternikov Danil
 */
public interface Mappable<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDto(List<E> entities);

    List<E> toEntity(List<D> dtos);

}
