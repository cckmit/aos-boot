package ink.aos.boot.ap.dto.ref;

import lombok.Data;

@Data
public class RefViewModelVO {

    //提供按给定的Pks进行过滤
    private String[] codes;

    private String[] filters;

    private String parentId;// 存在树形关系的参照可以使用

    //模糊查询
    private String content;

}
