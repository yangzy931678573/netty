package vert.enumconst;

/**
 * Created by Administrator on 2018/2/2.
 * Description: 查询sql枚举类
 */
public enum QuerySql {
    CREATE_PAGES_TABLE,
    ALL_PAGES,
    GET_PAGE,
    CREATE_PAGE,
    SAVE_PAGE,
    DELETE_PAGE;

    private String sql;

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String sql() {
        return this.sql;
    }
}
