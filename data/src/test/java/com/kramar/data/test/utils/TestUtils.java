package com.kramar.data.test.utils;

import com.kramar.data.dbo.*;
import com.kramar.data.listener.AdvertEntityListener;
import com.kramar.data.type.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

public class TestUtils {

    public static final String ANY_WORD = "String";
    public static final String DESCRIPTION = "Description";
    public static final BigDecimal PRICE = BigDecimal.valueOf(99.99);
    public static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final UUID INVALID_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    public static final byte[] RANDOM_BYTE = new byte[]{0, 1, 0, 1};

    public static AdvertDbo createAdvert() {
        final AdvertDbo advertDbo = new AdvertDbo();
        advertDbo.setId(ID);
        advertDbo.setAdvertStatus(AdvertStatus.ACTIVE);
        advertDbo.setAdvertType(AdvertType.SALE);
        advertDbo.setHeadLine(ANY_WORD);
        advertDbo.setPrice(PRICE);
        advertDbo.setCurrencyType(CurrencyType.USD);
        advertDbo.setDescription(DESCRIPTION);
        advertDbo.setOwner(new UserDbo());
        return advertDbo;
    }

    public static AdvertDbo createAdvert(UserDbo userDbo) {
        final AdvertDbo advertDbo = createAdvert();
        advertDbo.setOwner(userDbo);
        return advertDbo;
    }

    public static UserDbo createUser() {
        final UserDbo userDbo = new UserDbo();
        userDbo.setId(ID);
        userDbo.setEmail(ANY_WORD);
        userDbo.setUserName(ANY_WORD);
        userDbo.setUserSurname(ANY_WORD);
        userDbo.setPassword(ANY_WORD);
        userDbo.setStatus(UserStatus.ACTIVE);
        userDbo.setUserRoles(Collections.singletonList(UserRole.ADMIN));
        return userDbo;
    }

    public static ImageDbo createImage() {
        final ImageDbo imageDbo = new ImageDbo();
        imageDbo.setId(TestUtils.ID);
        imageDbo.setMimeType("image/png");
        imageDbo.setImageType(ImageType.COMMON);
        imageDbo.setContent(RANDOM_BYTE);
        return imageDbo;
    }

    public static AdvertHistoryDbo createAdvertHistory(AdvertDbo advertDbo) {
        Class[] parameterTypes = new Class[]{AbstractAuditableEntity.class};
        try {
            Method method = AdvertEntityListener.class.getDeclaredMethod("createAdvertHistoryEntity", parameterTypes);
            method.setAccessible(true);
            AdvertHistoryDbo advertHistoryDbo = (AdvertHistoryDbo) method.invoke(AdvertEntityListener.class.newInstance(), advertDbo);
            advertHistoryDbo.setAdvertHistoryStatus(AdvertHistoryStatus.ADDED);
            return advertHistoryDbo;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return new AdvertHistoryDbo();
    }
}
