package org.chatting.server.database.mapper;

import java.sql.ResultSet;
import java.util.List;

public interface EntityMapper<T> {
    T extractSingle(ResultSet resultSet);

    List<T> extractList(ResultSet resultSet);
}
