package vip.sunke.common.uploadFile;

import java.io.Serializable;

/**
 * @author sunke
 * @Date 2019-07-19 09:10
 * @description
 */


public class MultiFileInfo implements Serializable {
    private String id; //文件id
    private String name; //文件名称
    private String type;//文件类型
    private String lastModifiedDate;//文件最后一次修改时间
    private Long size;//文件总大小
    private Integer chunk;//当前分片序号
    private Integer chunks;//分片总数
    private String savePath;

    private String ext;


    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getExt() {
        return ext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        ext = name.substring(name.lastIndexOf(".") + 1);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getChunk() {
        return chunk;
    }

    public void setChunk(Integer chunk) {
        this.chunk = chunk;
    }

    public Integer getChunks() {
        return chunks;
    }

    public void setChunks(Integer chunks) {
        this.chunks = chunks;
    }




}
