/***************************************************************************
 * Copyright (c) 2019 TAOS Data, Inc. <jhtao@taosdata.com>
 *
 * This program is free software: you can use, redistribute, and/or modify
 * it under the terms of the GNU Affero General Public License, version 3
 * or later ("AGPL"), as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/
package com.taosdata.jdbc;

import java.sql.SQLException;
import java.sql.Types;

public abstract class TSDBConstants {

    public static final long JNI_NULL_POINTER = 0L;
    // JNI_ERROR_NUMBER
    public static final int JNI_SUCCESS = 0;
    public static final int JNI_TDENGINE_ERROR = -1;
    public static final int JNI_CONNECTION_NULL = -2;
    public static final int JNI_RESULT_SET_NULL = -3;
    public static final int JNI_NUM_OF_FIELDS_0 = -4;
    public static final int JNI_SQL_NULL = -5;
    public static final int JNI_FETCH_END = -6;
    public static final int JNI_OUT_OF_MEMORY = -7;
    // TSDB Data Types
    public static final int TSDB_DATA_TYPE_NULL = 0;
    public static final int TSDB_DATA_TYPE_BOOL = 1;
    public static final int TSDB_DATA_TYPE_TINYINT = 2;
    public static final int TSDB_DATA_TYPE_SMALLINT = 3;
    public static final int TSDB_DATA_TYPE_INT = 4;
    public static final int TSDB_DATA_TYPE_BIGINT = 5;
    public static final int TSDB_DATA_TYPE_FLOAT = 6;
    public static final int TSDB_DATA_TYPE_DOUBLE = 7;
    public static final int TSDB_DATA_TYPE_VARCHAR = 8;
    public static final int TSDB_DATA_TYPE_BINARY = TSDB_DATA_TYPE_VARCHAR;
    public static final int TSDB_DATA_TYPE_TIMESTAMP = 9;
    public static final int TSDB_DATA_TYPE_NCHAR = 10;
    /**
     * 系统增加新的无符号数据类型，分别是：
     * unsigned tinyint， 数值范围：0-254, NULL 为255
     * unsigned smallint，数值范围： 0-65534， NULL 为65535
     * unsigned int，数值范围：0-4294967294，NULL 为4294967295u
     * unsigned bigint，数值范围：0-18446744073709551614u，NULL 为18446744073709551615u。
     * example:
     * create table tb(ts timestamp, a tinyint unsigned, b smallint unsigned, c int unsigned, d bigint unsigned);
     */
    public static final int TSDB_DATA_TYPE_UTINYINT = 11;       //unsigned tinyint
    public static final int TSDB_DATA_TYPE_USMALLINT = 12;      //unsigned smallint
    public static final int TSDB_DATA_TYPE_UINT = 13;           //unsigned int
    public static final int TSDB_DATA_TYPE_UBIGINT = 14;        //unsigned bigint
    public static final int TSDB_DATA_TYPE_JSON = 15;           //json
    // nchar column max length
    public static final int maxFieldSize = 16 * 1024;

    // precision for data types, this is used for metadata
    public static final int BOOLEAN_PRECISION = 1;
    public static final int TINYINT_PRECISION = 4;
    public static final int SMALLINT_PRECISION = 6;
    public static final int INT_PRECISION = 11;
    public static final int BIGINT_PRECISION = 20;
    public static final int FLOAT_PRECISION = 12;
    public static final int DOUBLE_PRECISION = 22;
    public static final int TIMESTAMP_MS_PRECISION = 23;
    public static final int TIMESTAMP_US_PRECISION = 26;
    // scale for data types, this is used for metadata
    public static final int FLOAT_SCALE = 31;
    public static final int DOUBLE_SCALE = 31;

    public static final String DEFAULT_PRECISION = "ms";

    public static final boolean DEFAULT_BATCH_ERROR_IGNORE = false;

    public static String jdbcType2TaosTypeName(int jdbcType) throws SQLException {
        switch (jdbcType) {
            case Types.BOOLEAN:
                return "BOOL";
            case Types.TINYINT:
                return "TINYINT";
            case Types.SMALLINT:
                return "SMALLINT";
            case Types.INTEGER:
                return "INT";
            case Types.BIGINT:
                return "BIGINT";
            case Types.FLOAT:
                return "FLOAT";
            case Types.DOUBLE:
                return "DOUBLE";
            case Types.BINARY:
                return "BINARY";
            case Types.TIMESTAMP:
                return "TIMESTAMP";
            case Types.NCHAR:
                return "NCHAR";
            default:
                throw TSDBError.createSQLException(TSDBErrorNumbers.ERROR_UNKNOWN_SQL_TYPE_IN_TDENGINE, "unknown sql type: " + jdbcType + " in tdengine");
        }
    }

}
