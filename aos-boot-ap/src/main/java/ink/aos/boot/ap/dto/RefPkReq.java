package ink.aos.boot.ap.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RefPkReq implements Serializable {

    private int defaultFieldCount = 2;//默认显示字段中的显示字段数----表示显示前几个字段

    private String condition;//class id

    private String[] strFieldCode;// 业务表的字段名

    private String[] strFieldName; // 业务表的字段中文名

    private String[] strHiddenFieldCode;

    private String[] refCodeNamePK;//参照编码|名称|主键

    private boolean isUseDataPower = true;

    private boolean isMultiSelectedEnabled = false;

    private boolean isNotLeafSelected = true;

    private String rootName;

    private String dataPowerOperation_Code = null;

    private boolean isIncludeSub;

    private boolean isDisabledDataShow;

    private boolean isReturnCode;

    /**
     * 参照是否支持分页，后端控制参考是否分页，前端如果拿不到该字段值或者该字段值为flase 按不分页处理
     */
    private boolean isPageable = false;

    /**
     * 树参照是否支持懒加载，后端控制参照是否懒加载，前端如果拿不到该字段值或者该字段值为flase 按不懒加载处理
     * 支持懒加载的情况下，前端点击某个树节点之后，根据cfgParam的配置，给trasmitParam中丢入参数名为lazy_param参数，如果没有cfgParam则默认丢入的值为id,lazy_object,当前节点的json对象字符串
     * cfgParam 配置点击树节点时，给后端返回的数据属性名，例如是id/refpk/refcode等。
     */
    private boolean isLazyload = false;

    /**
     * 是否启用常用，默认是开启常用，除非显式设置成false
     */
    private boolean isHotspot = true;

    private String pk_group;

    private String pk_org;

    private String pk_user;

    //参照setpk匹配数据时，是否包含参照WherePart的开关
    private boolean isMatchPkWithWherePart = true;

    //提供按给定的Pks进行过滤
    private String codes[];

    //联动参数，树表参照时，点击分类返回的字段值
    private String clientParam;

    //配置参数,树表参照时，点击分类需要返回的字段名称
    private String cfgParam;

    //模糊查询
    private String content;

    private String refModelClassName;

    private String refModelUrl;

    private String refModelHandlerClass;

    private String refName;

    //传递各种参数
    private String transmitParam;

}
