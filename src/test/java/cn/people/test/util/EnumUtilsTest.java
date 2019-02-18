package cn.people.test.util;

import cn.people.commons.enumeration.ContentStatusEnum;
import cn.people.commons.enumeration.ContentTypeEnum;
import cn.people.commons.utils.EnumUtils;
import org.junit.Test;

/**
 * Created by wilson on 2018-12-05.
 */
public class EnumUtilsTest {

    @Test
    public void testEquals() {
        ContentStatusEnum e1 = ContentStatusEnum.CREATED;
        ContentStatusEnum e2 = ContentStatusEnum.DELETED;
        ContentTypeEnum e3 = ContentTypeEnum.ARTICLE;
        ContentStatusEnum e4 = null;

        String s1 = "CREATED";
        String s2 = "created";
        String s3 = "DELETED";
        String s4 = null;

        Integer ig1 = null;
        Integer ig2 = new Integer(1);
        Integer ig3 = 1;
        Integer ig4 = 2;

        int i1 = 1;
        int i2 = 2;

        // e1 == e1 : true
        assert EnumUtils.equals(e1, e1);

        // e1 == e2 : false
        assert !EnumUtils.equals(e1, e2);

        // e1 == e3 : false
        assert !EnumUtils.equals(e1, e3);

        // e1 == e4 : false
        assert !EnumUtils.equals(e1, e4);

        // e1 == s1 : true
        assert EnumUtils.equals(e1, s1);

        // e1 == s2 : false
        assert !EnumUtils.equals(e1, s2);

        // e1 == s3 : false
        assert !EnumUtils.equals(e1, s3);

        // e1 == s4 : false
        assert !EnumUtils.equals(e1, s4);

        // e1 == ig1 : false
        assert !EnumUtils.equals(e1, ig1);

        // e1 == ig2 : true
        assert EnumUtils.equals(e1, ig2);

        // e1 == ig3 : true
        assert EnumUtils.equals(e1, ig3);

        // e1 == ig4 : false
        assert !EnumUtils.equals(e1, ig4);

        // e1 == i1 : true
        assert EnumUtils.equals(e1, i1);

        // e1 == i2 : false
        assert !EnumUtils.equals(e1, i2);

        // e4 == e4 : true
        assert EnumUtils.equals(e4, e4);

        // e4 == s4 : true
        assert EnumUtils.equals(e4, s4);

        // e4 == ig1 : true
        assert EnumUtils.equals(e4, ig1);

        // e4 == s2 : true
        assert EnumUtils.equalsIgnoreCase(e1, s2);

        // e4 == s3 : false
        assert !EnumUtils.equalsIgnoreCase(e1, s3);

    }

}
