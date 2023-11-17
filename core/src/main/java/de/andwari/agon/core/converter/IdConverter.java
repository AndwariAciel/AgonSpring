package de.andwari.agon.core.converter;

import jakarta.persistence.AttributeConverter;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class IdConverter implements AttributeConverter<List<Long>, String> {

    @Override
    public String convertToDatabaseColumn(List<Long> ids) {
        return ids.stream().map(id -> Long.toString(id)).collect(joining(";"));
    }

    @Override
    public List<Long> convertToEntityAttribute(String ids) {
        return of(ids.split(";")).map(Long::parseLong).collect(toList());
    }
}
