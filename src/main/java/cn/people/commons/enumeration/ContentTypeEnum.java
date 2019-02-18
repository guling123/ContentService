package cn.people.commons.enumeration;

/**
 * 文章类型
 * <p>
 * Created by wilson on 2018-12-05.
 */
public enum ContentTypeEnum implements BaseEnum {

    ARTICLE(1),             // 图文
    IMAGES(2),              // 图集
    LIVE(3),                // 直播
    DEMAND(4);              // 点播

    private int value;

    ContentTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

}
