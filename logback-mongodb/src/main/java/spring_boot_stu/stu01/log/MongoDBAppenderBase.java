package spring_boot_stu.stu01.log;

import com.mongodb.BasicDBObject;

import ch.qos.logback.core.UnsynchronizedAppenderBase;

/**
 * An abstract appender handling connection to MongoDB. Subclasses should
 * implement {@link #toMongoDocument(Object)}.
 * 
 * @author Tomasz Nurkiewicz
 * @author Christian Trutz
 * @since 0.1
 * 
 * @author Vincent Fung
 * @since 0.2
 * 
 */
public abstract class MongoDBAppenderBase<E> extends UnsynchronizedAppenderBase<E> {

	// see also http://www.mongodb.org/display/DOCS/Connections
	private String uri = null;

	/**
	 * If appender starts, create a new MongoDB connection and authenticate
	 * user. A MongoDB database and collection in {@link #setUri(String)} is
	 * mandatory, username and password are optional.
	 */
	@Override
	public void start() {
		try {
			if (uri == null) {
				addError("Please set a non-null MongoDB URI.");
				return;
			}
			super.start();

		} catch (Exception exception) {
			addError("Error connecting to MongoDB URI: " + uri, exception);
		}
	}

	/**
	 * Inserts a new MongoDB document representing {@code eventObject} into
	 * MongoDB database.
	 * 
	 * @param event
	 *            a logging event, containing all log data
	 */
	@Override
	protected void append(E event) {
		MongoDbClientManager.getMongoCollection(BasicDBObject.class).insertOne(toMongoDocument(event));
	}

	/**
	 * Creates a new MongoDB document {@link BasicDBObject} from a logging
	 * event, containing all log data.
	 * 
	 * @param event
	 *            a logging event, containing all log data
	 * @return a {@link BasicDBObject} to be inserted into MongoDB
	 */
	protected abstract BasicDBObject toMongoDocument(E event);

	/**
	 * If appender stops, close also the MongoDB connection.
	 */
	@Override
	public void stop() {
		super.stop();
	}

	/**
	 * A {@code uri} contains all MongoDB connection data.
	 * 
	 * @param uri
	 *            <a href="http://www.mongodb.org/display/DOCS/Connections">a
	 *            MongoDB URI</a>
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

}
