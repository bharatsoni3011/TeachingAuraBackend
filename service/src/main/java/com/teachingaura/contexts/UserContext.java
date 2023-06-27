package com.teachingaura.contexts;

public class UserContext {

    private static final ThreadLocal<FirebaseUserInfo> CONTEXT = new ThreadLocal<>();

    public static void setInfo(FirebaseUserInfo info) {
        CONTEXT.set(info);
    }

    public static String getUid() {
        FirebaseUserInfo firebaseUserInfo = CONTEXT.get();
        if (firebaseUserInfo != null)
            return firebaseUserInfo.getUid();
        else
            return "";
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
