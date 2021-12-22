package ink.aos.boot.ap.rest;

public abstract class AbstractFlatRefResource implements InterfaceRefResource {

    public static final String HOT_FILE_CODE = "hot"; //常用分组列编码

//    @RequestMapping(value = "/getRefModelInfo", method = RequestMethod.POST)
//    public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
//        refViewModel.setRefUIType(RefUITypeEnum.RefFlat);
//        refViewModel.setStrFieldCode(new String[]{HOT_FILE_CODE});
//        refViewModel.setStrFieldName(new String[]{"常用"});
//        return refViewModel;
//    }

//    /**
//     * 获取参照平铺数据，按数据中的name（或者编码、或这其他字段）的拼音或者英文首字母进行分组
//     *
//     * @param refViewModel
//     * @return
//     */
//    @RequestMapping(value = "/getFlatRefData", method = RequestMethod.POST)
//    public abstract @ResponseBody
//    Object getFlatRefData(@RequestBody RefViewModelVO refViewModel);
}
