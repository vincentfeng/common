package spring_boot_stu.stu01.log;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;

/**
 * @author Vincent Fung
 * @since 0.1
 */
public class MongoDbClientManager {
	
	static final String DATABASE = "test";
	static final String COLLECTION = "logs";
	static final String URI = "mongodb://localhost:27017";
	static final int MAX_WAIT_TIME = 2000;
	static final int CONNECT_TIME_OUT = 2000;

	private static MongoClient mongoClient;

	static {
		MongoClientOptions.Builder options = MongoClientOptions.builder();
		options.maxWaitTime(MAX_WAIT_TIME);
		options.connectTimeout(CONNECT_TIME_OUT);
		MongoClientURI connectionString = new MongoClientURI(URI,options);
		mongoClient = new MongoClient(connectionString);
	}

	public MongoClient getClient() {
		return mongoClient;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static MongoCollection getMongoCollection(Class clazz) {
		return mongoClient.getDatabase(DATABASE).getCollection(COLLECTION, clazz);
	}

	@Override
	protected void finalize() throws Throwable {
		if (mongoClient != null)
			mongoClient.close();
		System.out.println("client close.....");
		super.finalize();
	}

}
