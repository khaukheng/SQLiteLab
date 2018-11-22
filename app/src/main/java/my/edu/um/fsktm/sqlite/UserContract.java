package my.edu.um.fsktm.sqlite;

import android.provider.BaseColumns;

public class UserContract {
    public UserContract(){}

    public static abstract class User implements BaseColumns {
        public static final String TABLE_NAME="user";
        public static final String COLUMN_PHONE="phone";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_EMAIL="email";
    }
}
