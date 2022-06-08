package com.taosdata.jdbc.ws;

import com.taosdata.jdbc.TSDBDriver;
import com.taosdata.jdbc.annotation.CatalogRunner;
import com.taosdata.jdbc.annotation.Description;
import com.taosdata.jdbc.annotation.TestTarget;
import com.taosdata.jdbc.utils.SpecifyAddress;
import org.junit.*;
import org.junit.runner.RunWith;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@RunWith(CatalogRunner.class)
@TestTarget(alias = "query test", author = "huolibo", version = "2.0.38")
@FixMethodOrder
public class WSQueryTest {
    private static final String host = "127.0.0.1";
    private static final int port = 6041;
    private static final String databaseName = "ws_query";
    private static final String tableName = "wq";
    private Connection connection;
    private long now;

    @Description("query")
    @Test
    public void queryBlock() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1000);
        IntStream.range(1, 10000).limit(1000).parallel().forEach(x -> {
            try (Statement statement = connection.createStatement()) {
                statement.execute("insert into " + databaseName + "." + tableName + " values(now+100s, 100)");

                ResultSet resultSet = statement.executeQuery("select * from " + databaseName + "." + tableName);
                resultSet.next();
                Assert.assertEquals(100, resultSet.getInt(2));
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });
        latch.await();
    }

    @Before
    public void before() throws SQLException {
        String url = SpecifyAddress.getInstance().getRestWithoutUrl();
        if (url == null) {
            url = "jdbc:TAOS-RS://" + host + ":" + port + "/log?user=root&password=taosdata";
        } else {
            url += "log?user=root&password=taosdata";
        }
        Properties properties = new Properties();
        properties.setProperty(TSDBDriver.PROPERTY_KEY_BATCH_LOAD, "true");
        connection = DriverManager.getConnection(url, properties);
        Statement statement = connection.createStatement();
        statement.execute("drop database if exists " + databaseName);
        statement.execute("create database " + databaseName);
        statement.execute("use " + databaseName);
        statement.execute("create table if not exists " + databaseName + "." + tableName + "(ts timestamp, f int)");
        statement.close();
    }
}
