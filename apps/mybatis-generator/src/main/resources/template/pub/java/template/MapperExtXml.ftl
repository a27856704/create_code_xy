<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${namespace}">
    <resultMap extends="${mapperClass}.oneResultMap" id="baseResultMap" type="${modelExt}">

    </resultMap>
    <sql id="fromTable">
        from ${table}
    </sql>

    <sql id="baseSmallColumnList">
        <include refid="${mapperClass}.oneSmallColumnList"/>
    </sql>

    <sql id="baseColumnList">
        <include refid="${mapperClass}.oneColumnList"/>
    </sql>

    <sql id="includeWhereSql">
        <include refid="${searchWhere}"></include>
    </sql>
    <sql id="whereSql">
        <where>
            <include refid="${namespace}.includeWhereSql"></include>
        </where>
    </sql>

    <sql id="getListSql">
        select
        <include refid="${namespace}.baseSmallColumnList"/>
        <include refid="${namespace}.fromTable"></include>
        <include refid="${namespace}.whereSql"></include>
        <include refid="${searchOrder}"></include>
    </sql>

</mapper>