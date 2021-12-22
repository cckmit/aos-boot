package ink.aos.boot.ap.rest;

/**
 * 表格型参照抽象模型类
 */
public abstract class AbstractGridRefResource<T> implements InterfaceRefResource<T> {
//
//    @RequestMapping(value = "/getRefModelInfo", method = RequestMethod.POST)
//    public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
//        refViewModel.setRefUIType(RefUITypeEnum.RefGrid);
//        return refViewModel;
//    }

    /**
     * 获取参照数据
     * @param refViewModel
     * @return
     */
    //	@RequestMapping(value = "/getCommonRefData", method = RequestMethod.POST)
    //	public abstract @ResponseBody List<Map<String, String>> getCommonRefData(@RequestBody RefViewModelVO refViewModel);

//    /**
//     * 获取参照数据
//     *
//     * @param refViewModel
//     * @return
//     */
//    @RequestMapping(value = "/getCommonRefData", method = RequestMethod.POST)
//    public abstract @ResponseBody
//    Map<String, Object> getCommonRefData(@RequestBody RefViewModelVO refViewModel);

}
