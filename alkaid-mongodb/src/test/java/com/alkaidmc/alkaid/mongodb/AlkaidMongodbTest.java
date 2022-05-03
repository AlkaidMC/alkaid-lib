package com.alkaidmc.alkaid.mongodb;

import org.bson.Document;
import org.junit.jupiter.api.Test;

public class AlkaidMongodbTest {
    AsyncMongodbConnection asyncMongodbConnection = new AlkaidMongodb().database("test").async();
    String testCollection = "testAlkaid";

    @Test
    public void insertionTest() {
        asyncMongodbConnection.create(testCollection,
                new Document("name", "Neko")
                        .append("age", 4)
        );
    }
}
