package cn.people.commons.utils;

import cn.people.commons.enumeration.BaseEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 枚举工具类
 * <p>
 * Created by wilson on 2018-12-05.
 */
public class EnumUtils extends org.apache.commons.lang3.EnumUtils {

    /**
     * <p>比较两个枚举是否相同</p>
     * <pre>
     * EnumUtils.equals(null, null)  = true
     * EnumUtils.equals(null, ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, null)  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, ContentStatusEnum.AUDIT)  = true
     * EnumUtils.equals(ContentStatusEnum.AUDIT, ContentTypeEnum.ARTICLE)  = false
     * </pre>
     *
     * @param e1 the first enum, must be implemented {@link cn.people.commons.enumeration.BaseEnum}
     * @param e2 the second enum, must be implemented {@link cn.people.commons.enumeration.BaseEnum}
     * @return <code>true</code> if the Enums are equal, or both <code>null</code>
     * @author tianweisong
     */
    public static boolean equals(BaseEnum e1, BaseEnum e2) {
        if (e1 == e2) {
            return true;
        }

        if (e1 == null) {
            return false;
        }

        if (e2 == null) {
            return false;
        }

        if (!e1.getClass().equals(e2.getClass())) {
            return false;
        }

        return e1.value() == e2.value();
    }

    /**
     * <p>对比enum和string是否相同, 大小写敏感</p>
     * <p>注意和string比较的是enum.name()</p>
     * <pre>
     * EnumUtils.equals(null, null)   = true
     * EnumUtils.equals(null, "")  = false
     * EnumUtils.equals(null, " ")  = false
     * EnumUtils.equals(null, "AUDIT")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, null)  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, "")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, " ")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, "AUDIT")  = true
     * EnumUtils.equals(ContentStatusEnum.AUDIT, "PUBLISHED")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, " AUDIT ")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, "audit")  = false
     * </pre>
     *
     * @param e1 the enum param
     * @param e2 the string param
     * @return <code>true</code> if the Enums are equal, or both <code>null</code>
     * @author tianweisong
     */
    public static boolean equals(Enum e1, String e2) {
        if (e1 == null && e2 == null) {
            return true;
        }

        if (e1 == null || e2 == null) {
            return false;
        }

        if (StringUtils.isBlank(e2)) {
            return false;
        }

        return StringUtils.equals(String.valueOf(e1.name()), e2);
    }

    /**
     * <p>对比enum和string是否相同, 大小写敏感</p>
     * <p>注意和string比较的是enum.name()</p>
     * <pre>
     * EnumUtils.equals(null, null)   = true
     * EnumUtils.equals("", null)  = false
     * EnumUtils.equals(" ", null)  = false
     * EnumUtils.equals("AUDIT", null)  = false
     * EnumUtils.equals(null, ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equals("", ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equals(" ", ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equals("AUDIT", ContentStatusEnum.AUDIT)  = true
     * EnumUtils.equals("PUBLISHED", ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equals(" AUDIT ", ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equals("audit", ContentStatusEnum.AUDIT)  = false
     * </pre>
     *
     * @param e1 the enum param
     * @param e2 the string param
     * @return <code>true</code> if the Enums are equal, or both <code>null</code>
     * @author tianweisong
     */
    public static boolean equals(String e1, Enum e2) {
        return equals(e2, e1);
    }

    /**
     * <p>对比enum和string是否相同</p>
     * <p>注意和string比较的是enum.name()</p>
     * <pre>
     * EnumUtils.equals(null, null)   = true
     * EnumUtils.equals(null, "")  = false
     * EnumUtils.equals(null, " ")  = false
     * EnumUtils.equals(null, "AUDIT")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, null)  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, "")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, " ")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, "AUDIT")  = true
     * EnumUtils.equals(ContentStatusEnum.AUDIT, "PUBLISHED")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, " AUDIT ")  = false
     * EnumUtils.equals(ContentStatusEnum.AUDIT, "audit")  = true
     * </pre>
     *
     * @param e1 the enum param
     * @param e2 the string param
     * @return <code>true</code> if the Enums are equal, or both <code>null</code>
     * @author tianweisong
     */
    public static boolean equalsIgnoreCase(Enum e1, String e2) {
        if (e1 == null && e2 == null) {
            return true;
        }

        if (e1 == null || e2 == null) {
            return false;
        }

        if (StringUtils.isBlank(e2)) {
            return false;
        }

        return StringUtils.equalsIgnoreCase(String.valueOf(e1.name()), e2);
    }

    /**
     * <p>对比enum和string是否相同</p>
     * <p>注意和string比较的是enum.name()</p>
     * <pre>
     * EnumUtils.equalsIgnoreCase(null, null)   = true
     * EnumUtils.equalsIgnoreCase("", null)  = false
     * EnumUtils.equalsIgnoreCase(" ", null)  = false
     * EnumUtils.equalsIgnoreCase("AUDIT", null)  = false
     * EnumUtils.equalsIgnoreCase(null, ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equalsIgnoreCase("", ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equalsIgnoreCase(" ", ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equalsIgnoreCase("AUDIT", ContentStatusEnum.AUDIT)  = true
     * EnumUtils.equalsIgnoreCase("PUBLISHED", ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equalsIgnoreCase(" AUDIT ", ContentStatusEnum.AUDIT)  = false
     * EnumUtils.equalsIgnoreCase("audit", ContentStatusEnum.AUDIT)  = true
     * </pre>
     *
     * @param e1 the enum param
     * @param e2 the string param
     * @return <code>true</code> if the Enums are equal, or both <code>null</code>
     * @author tianweisong
     */
    public static boolean equalsIgnoreCase(String e1, Enum e2) {
        return equalsIgnoreCase(e2, e1);
    }

    /**
     * <p>对比enum和Integer是否相同</p>
     * <p>注意和Integer比较的是enum.value()</p>
     * <pre>
     * EnumUtils.equals(null, null)  = true
     * EnumUtils.equals(null, new Integer(1))  = false
     * EnumUtils.equals(null, 1)  = false
     * EnumUtils.equals(ContentStatusEnum.CREATED, null)  = false
     * EnumUtils.equals(ContentStatusEnum.CREATED, new Integer(1))  = true
     * EnumUtils.equals(ContentStatusEnum.CREATED, 1)  = true
     * EnumUtils.equals(ContentStatusEnum.CREATED, new Integer(2))  = false
     * EnumUtils.equals(ContentStatusEnum.CREATED, 2)  = false
     * </pre>
     *
     * @param e1 the int param, must be implemented {@link cn.people.commons.enumeration.BaseEnum}
     * @param e2 the enum param
     * @return <code>true</code> if the Enums are equal, or both <code>null</code>
     * @author tianweisong
     */
    public static boolean equals(BaseEnum e1, Integer e2) {
        if (e1 == null && e2 == null) {
            return true;
        }

        if (e1 == null || e2 == null) {
            return false;
        }

        return e2.equals(e1.value());
    }

    /**
     * <p>对比enum和Integer是否相同</p>
     * <p>注意和Integer比较的是enum.value()</p>
     * <pre>
     * EnumUtils.equals(null, null)  = true
     * EnumUtils.equals(new Integer(1), null)  = false
     * EnumUtils.equals(1, null)  = false
     * EnumUtils.equals(null, ContentStatusEnum.CREATED)  = false
     * EnumUtils.equals(new Integer(1), ContentStatusEnum.CREATED)  = true
     * EnumUtils.equals(1, ContentStatusEnum.CREATED)  = true
     * EnumUtils.equals(new Integer(2), ContentStatusEnum.CREATED)  = false
     * EnumUtils.equals(2, ContentStatusEnum.CREATED)  = false
     * </pre>
     *
     * @param e1 the int param, must be implemented {@link cn.people.commons.enumeration.BaseEnum}
     * @param e2 the enum param
     * @return <code>true</code> if the Enums are equal, or both <code>null</code>
     * @author tianweisong
     */
    public static boolean equals(Integer e1, BaseEnum e2) {
        return equals(e2, e1);
    }

    /**
     * <p>对比enum和string是否相同</p>
     * <p>注意和int比较的是enum.value()</p>
     * <pre>
     * EnumUtils.equals(null, 1)  = false
     * EnumUtils.equals(ContentStatusEnum.CREATED, 1)  = true
     * EnumUtils.equals(ContentStatusEnum.CREATED, 2)  = false
     * </pre>
     *
     * @param e1 the enum param, must be implemented {@link cn.people.commons.enumeration.BaseEnum}
     * @param e2 the string param
     * @return <code>true</code> if the Enums are equal, or both <code>null</code>
     * @author tianweisong
     */
    public static boolean equals(BaseEnum e1, int e2) {
        if (e1 == null) {
            return false;
        }

        return e1.value() == e2;
    }

    /**
     * <p>对比enum和int是否相同</p>
     * <p>注意和int比较的是enum.value()</p>
     * <pre>
     * EnumUtils.equals(1, null)  = false
     * EnumUtils.equals(1, ContentStatusEnum.CREATED)  = true
     * EnumUtils.equals(2, ContentStatusEnum.CREATED)  = false
     * </pre>
     *
     * @param e1 the int param, must be implemented {@link cn.people.commons.enumeration.BaseEnum}
     * @param e2 the enum param
     * @return <code>true</code> if the Enums are equal, or both <code>null</code>
     * @author tianweisong
     */
    public static boolean equals(int e1, BaseEnum e2) {
        return equals(e2, e1);
    }

}
