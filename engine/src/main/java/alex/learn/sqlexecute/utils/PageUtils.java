package alex.learn.sqlexecute.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import alex.learn.common.stmt.beans.*;

import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/21
 * 分页服务
 */
@Service
@Scope(value = "prototype")
public class PageUtils {

    DataBaseClient client = new DataBaseClient();

    //分页
    //参数应该传入，本例作参考
    public Page getPage(Page page, ConnectionInfo info) throws Exception {
        client.getConnection(info);
        //获取元数据
        List<String> data = client.getTables(client.getConnection(info), info);
        client.closedbconnection();
        //初始页面为第一页
        if (page.getCurrentPage() == null) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(page.getCurrentPage());
        }
        //分页参数设置
        page.setPageSize(10);
        page.setStart((page.getCurrentPage() - 1) * page.getPageSize());
        int count = data.size();
        page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
        page.setDataList(data.subList(page.getStart(), count - page.getStart() > page.getPageSize() ? page.getStart() + page.getPageSize() : count));

        return page;
    }

}