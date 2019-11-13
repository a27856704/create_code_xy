<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${pubPackage}.pubInter.SearchMapper">

    <sql id="searchWhere">
        <if test="likeMap!=null">
            <foreach collection="likeMap" index="likeFiled" item="likeItem">
                and ${r'${likeFiled}'} like CONCAT(CONCAT('%',${r'#{likeItem}'}), '%')
            </foreach>
        </if>
        <if test="equalMap!=null">
            <foreach collection="equalMap" index="equalFiled" item="equalItem">
                and ${r'${equalFiled}'} =${r'#{equalItem}'}
            </foreach>
        </if>
        <if test="numberGreaterMap!=null">
            <foreach collection="numberGreaterMap" index="numberGreaterFiled" item="numberGreaterItem">
                and ${r'${numberGreaterFiled}'} >=${r'#{numberGreaterItem}'}
            </foreach>
        </if>
        <if test="numberLessMap!=null">
            <foreach collection="numberLessMap" index="numberLessFiled" item="numberLessItem">
                and ${r'${numberLessFiled}'} &lt;=${r'#{numberLessItem}'}
            </foreach>
        </if>
        <if test="inMap!=null">
            <foreach collection="inMap" index="inFiled" item="inListItem">
                and ${r'${inFiled}'} in
                <foreach collection="inListItem" item="inItem" separator="," open="(" close=")">
                    ${r'#{inItem}'}
                </foreach>
            </foreach>
        </if>
        <if test="notInMap!=null">
            <foreach collection="notInMap" index="notInFiled" item="notInListItem">
                and ${r'${notInFiled}'} not in
                <foreach collection="notInListItem" item="notInItem" separator="," open="(" close=")">
                    ${r'#{notInItem}'}
                </foreach>
            </foreach>
        </if>
        <if test="dateStartMap!=null">
            <foreach collection="dateStartMap" index="dateStartFiled" item="dateStartItem">
                and ${r'${dateStartFiled}'} >=${r'#{dateStartItem}'}
            </foreach>
        </if>
        <if test="dateEndMap!=null">
            <foreach collection="dateEndMap" index="dateEndFiled" item="dateEndItem">
                and ${r'${dateEndFiled}'} &lt;=${r'#{dateEndItem}'}
            </foreach>
        </if>
        <if test="bitMap!=null">
            <foreach collection="bitMap" index="bitFiled" item="bitItem">
                and (${r'${bitFiled}'} &amp; ${r'#{bitItem}'})=${r'#{bitItem}'}
            </foreach>
        </if>
    </sql>


    <sql id="searchGroupByAndOrderBy">


        <if test="groupBy!=null and groupBy!=''">
            GROUP by ${r'${groupBy}'}

        </if>


        ORDER BY
        <if test="orderBy!=null and orderBy!=''">
            ${r'${orderBy}'}
        </if>
        <if test="orderBy==null or orderBy==''">
            ${r'${defaultOrderField}'}
        </if>
        ${r'${orderDesc}'}


        <if test="orderBy1!=null and orderBy1!=''">
            ,${r'${orderBy1}'} ${r'${orderDesc1}'}
        </if>
        <if test="orderBy2!=null and orderBy2!=''">
            ,${r'${orderBy2}'} ${r'${orderDesc2}'}
        </if>


    </sql>


    <sql id="searchOtherOrder">

        <include refid="${pubPackage}.pubInter.SearchMapper.searchGroupByAndOrderBy"></include>


    </sql>


</mapper>
