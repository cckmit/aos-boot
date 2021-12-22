package ink.aos.boot.ap.rest;

import ink.aos.boot.ap.dto.RefDetailDTO;
import ink.aos.boot.ap.dto.ref.RefViewModelVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface InterfaceRefResource<T> {

    /**
     * 模糊搜索界面输入数据
     */
    @PostMapping(value = "/simple-search")
    List<RefDetailDTO> simpleSearch(@RequestBody RefViewModelVO refViewModelVO);

    /**
     * 通过Code等信息获取参照值对象,同步校验常用数据，防止无效数据
     *
     * @param codes
     * @return List
     */
    @PostMapping(value = "/translate")
    List<T> translate(@RequestParam(name = "code") String[] codes);

//    /**
//     * 通过name等信息获取参照值对象,对手工录入数据的校验,防止无效数据
//     *
//     * @param refViewModelVO
//     * @return List
//     */
//    @PostMapping(value = "/matchBlurRefJSON")
//    List<T> matchBlurRefJSON(@RequestBody RefViewModelVO refViewModelVO);

}
