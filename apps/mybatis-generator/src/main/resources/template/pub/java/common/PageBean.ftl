package ${pubPackage}.pubInter.common;
import java.io.Serializable;

/**
* @author ${author}
* @Date ${createTime}
* @description 分页
*/

public class PageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private long currentPage = 1;

    private long pageSize = PAGE_SIZE;//每页显示条数

    private long begin;// 起始行

    private long totalCount;// 总行数

    private long totalPage;// 总页数

    public static int PAGE_SIZE = 20;


    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public PageBean(long pageSize, long currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public PageBean() {
    }

    /**
     * 获取开始条数（当前页-1*每页条数）
     *
     * @return
     */
    public long getBegin() {
        begin = (currentPage - 1) * pageSize;
        if (begin < 0)
            begin = 0;
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 获取最大页数（已判断是否整除+1）
     *
     * @return
     */
    public long getTotalPage() {
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }
}
