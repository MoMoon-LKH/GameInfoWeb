package com.project.gameInfo.config;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MysqlCustomDialect extends MySQL57Dialect {

    public MysqlCustomDialect() {
        registerFunction("GROUP_CONCAT", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));

    }
}