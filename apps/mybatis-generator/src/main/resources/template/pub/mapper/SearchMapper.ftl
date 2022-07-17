<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${pubPackage}.pubInter.SearchMapper">

    <sql id="searchWhere">
        <if test="search.likeMap!=null">
            <foreach collection="search.likeMap" index="likeFiled" item="likeItem">
                and ${r'${likeFiled}'} like CONCAT(CONCAT('%',${r'#{likeItem}'}), '%')
            </foreach>
        </if>
        <if test="search.equalMap!=null">
            <foreach collection="search.equalMap" index="equalFiled" item="equalItem">
                and ${r'${equalFiled}'} =${r'#{equalItem}'}
            </foreach>
        </if>
        <if test="search.numberGreaterMap!=null">
            <foreach collection="search.numberGreaterMap" index="numberGreaterFiled" item="numberGreaterItem">
                and ${r'${numberGreaterFiled}'} >=${r'#{numberGreaterItem}'}
            </foreach>
        </if>
        <if test="search.numberLessMap!=null">
            <foreach collection="search.numberLessMap" index="numberLessFiled" item="numberLessItem">
                and ${r'${numberLessFiled}'} &lt;=${r'#{numberLessItem}'}
            </foreach>
        </if>
        <if test="search.inMap!=null">
            <foreach collection="search.inMap" index="inFiled" item="inListItem">
                and ${r'${inFiled}'} in
                <foreach collection="inListItem" item="inItem" separator="," open="(" close=")">
                    ${r'#{inItem}'}
                </foreach>
            </foreach>
        </if>
        <if test="search.notInMap!=null">
            <foreach collection="search.notInMap" index="notInFiled" item="notInListItem">
                and ${r'${notInFiled}'} not in
                <foreach collection="notInListItem" item="notInItem" separator="," open="(" close=")">
                    ${r'#{notInItem}'}
                </foreach>
            </foreach>
        </if>
        <if test="search.dateStartMap!=null">
            <foreach collection="search.dateStartMap" index="dateStartFiled" item="dateStartItem">
                and ${r'${dateStartFiled}'} >=${r'#{dateStartItem}'}
            </foreach>
        </if>
        <if test="search.dateEndMap!=null">
            <foreach collection="search.dateEndMap" index="dateEndFiled" item="dateEndItem">
                and ${r'${dateEndFiled}'} &lt;=${r'#{dateEndItem}'}
            </foreach>
        </if>
        <if test="search.bitMap!=null">
            <foreach collection="search.bitMap" index="bitFiled" item="bitItem">
                and (${r'${bitFiled}'} &amp; ${r'#{bitItem}'})=${r'#{bitItem}'}
            </foreach>
        </if>

        <if test="search.notBitMap!=null">
            <foreach collection="search.notBitMap" index="bitFiled" item="bitItem">
                and (${r'${bitFiled}'} &amp; ${r'#{bitItem}'})!=${r'#{bitItem}'}
            </foreach>
        </if>

        <if test="search.orBitMap!=null">
            <foreach collection="search.orBitMap" index="bitFiled" item="bitItem">
                and (${r'${bitFiled}'} | ${r'#{bitItem}'})!=${r'#{bitFiled}'}
            </foreach>
        </if>
        <if test="search.nullSet!=null">
            <foreach collection="search.nullSet" item="nullFiled">
                and ${r'${nullFiled}'} is  null
            </foreach>
        </if>
        <if test="search.notNullSet!=null">
            <foreach collection="search.notNullSet" item="notNullField">
                and ${r'${notNullField}'} is not null
            </foreach>
        </if>

        <if test="search.customSql!=null">
            and  ${r'${search.customSql}'}
        </if>


    </sql>





    <sql id="searchGroupByAndOrderBy">


        <if test="search.groupBy!=null and search.groupBy!=''">
            GROUP by ${r'${search.groupBy}'}

        </if>


        ORDER BY
        <if test="search.orderBy!=null and search.orderBy!=''">
            ${r'${search.orderBy}'}
        </if>
        <if test="search.orderBy==null or search.orderBy==''">
            ${r'${search.defaultOrderField}'}
        </if>
        ${r'${search.orderDesc}'}


        <if test="search.orderBy1!=null and search.orderBy1!=''">
            ,${r'${search.orderBy1}'} ${r'${search.orderDesc1}'}
        </if>
        <if test="search.orderBy2!=null and search.orderBy2!=''">
            ,${r'${search.orderBy2}'} ${r'${search.orderDesc2}'}
        </if>


    </sql>


    <sql id="searchOtherOrder">

        <include refid="${pubPackage}.pubInter.SearchMapper.searchGroupByAndOrderBy"></include>


    </sql>


</mapper>
