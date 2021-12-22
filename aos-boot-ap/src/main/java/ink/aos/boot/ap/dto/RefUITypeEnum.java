package ink.aos.boot.ap.dto;

public enum RefUITypeEnum {

    CommonRef("CommonRef"),
    RefGrid("RefGrid"),
    RefTree("RefTree"),
    RefGridTree("RefGridTree"),
    Custom("Custom"),
    RefFlat("RefFlat");

    // 类型名称
    private String name;

    RefUITypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
