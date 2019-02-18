package cn.people.commons.enumeration;

/**
 * 文章状态
 * <p>
 * Created by wilson on 2018-12-05.
 */
public enum ContentStatusEnum implements BaseEnum {

    CREATED(1),             // 创建文章
    AUDIT(2),               // 提审        （初审完成）
    FIRST_PASS(3),          // 一审通过     （二审完成）
    FIRST_REJECT(4),        // 一审驳回     （二审驳回）
    SECOND_PASS(5),         // 二审通过     （三审通过）
    SECOND_REJECT(6),       // 二审驳回     （三审驳回）
    PUBLISHED(7),           // 已发布       （上线）
    DELETED(8);             // 已删除

    private int value;

    ContentStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

}
