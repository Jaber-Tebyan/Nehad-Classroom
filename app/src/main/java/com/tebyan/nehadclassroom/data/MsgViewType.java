package com.tebyan.nehadclassroom.data;

public enum MsgViewType {
    TEXT_TYPE, IMAGE_TYPE;

    public static MsgViewType from(int value) {
        switch (value) {
            case 0:
                return TEXT_TYPE;
            case 1:
                return IMAGE_TYPE;
            case -1:
                return null;
        }
        return null;
    }

    public static int to(MsgViewType type) {
        switch (type) {
            case TEXT_TYPE:
                return 0;
            case IMAGE_TYPE:
                return 1;
            default:
                return -1;
        }
    }
}
