package com.example.pc.sqliteexample;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by PC on 16-Dec-17.
 */

public class Database {

    public static final String PROVIDER_AUTHORITY = "com.example.pc.sqliteexample";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + PROVIDER_AUTHORITY);

    public class PersonModel implements BaseColumns{

        public final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath("PersonModel").build();

        public static final String TABLE_NAME = "PersonModel";

        public static final String firstName = "firstName";

        public static final String lastName = "lastName";

        public static final String birthDate = "birthDate";

        public static final String gender = "gender";

        public static final String  idNumber = "idNumber";

    }

}
