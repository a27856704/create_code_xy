package ${pubPackage}.pubInter;

/**
* @author ${author}
* @Date ${createTime}
* @description Source search都继承此类
*/


public abstract class AbstractSourceBaseSearch extends BaseSearch {


    public abstract String getSourceField();

    private Integer sourceBit;

    public Integer getSourceBit() {
        return sourceBit;
    }

    public AbstractSourceBaseSearch setSourceBit(Integer sourceBit) {
        this.sourceBit = sourceBit;
        if (sourceBit == null)
            return this;
        if (sourceBit.intValue() > 0)
            setBitField(getSourceField(), sourceBit);
        return this;
    }


}
