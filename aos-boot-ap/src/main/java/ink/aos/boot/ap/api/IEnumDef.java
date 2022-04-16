package ink.aos.boot.ap.api;

public interface IEnumDef {

    String URL_PRE = "/enumdef/";

    default String getName() {
        return name();
    }

    default String name() {
        return null;
    }

    String getLabel();

}
