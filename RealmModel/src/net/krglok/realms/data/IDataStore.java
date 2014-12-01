package net.krglok.realms.data;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;

/**
 * <pre>
 * define the interface for data storage in yml files
 * the type of stored object are given in constructor
 * the  process is defined as:
 * - read the list of data object, read the whole datafile, but return only list of refId
 * - read a dataobject with a given refId
 * - write a data object 
 * 
 * the data object stored as a list with a string identifier
 * the identifier are strings for simplicity
 * 
 * @author Windu
 *
 * @param <T>
 * </pre>
 */
public interface IDataStore <T>
{
	/**
	 * <pre>
	 * init the dataSections with the object data
	 * this is individual for everyObject and DataStore instance
	 * Hint: will be used from writeData
	 * 
	 * @param dataObject
	 * <pre>
	 */
	public void initDataSection(ConfigurationSection section, T dataObject);
	
	/**
	 * <pre>
	 * init the dataSection with object data 
	 * and write to an yml file
	 * Hint: refID should be also present in the dataObject
	 * 
	 * @param dataObject
	 * </pre>
	 */
	public void writeData(T dataObject, String refId);
	
	/**
	 * <pre>
	 * initialize dataObject with data from dataSection
	 * will used from readData to to initialize dataObject
	 * this is individual for everyObject and DataStore instance
	 * 
	 * @return initialize dataObject | null
	 * <pre>
	 */
	public T initDataObject(ConfigurationSection data);
	
	/**
	 * <pre>
	 * read data from dataSection
	 * and initialize the DataObject
	 *   
	 * @param refId
	 * @return initialized dataObject | null
	 * </pre>
	 */
	public T readData(String refId);

	/**
	 * <pre>
	 * read the list of dataObjects from yml file into List<String>
	 * the ÖList<String> used to read the dataObject from file
	 * Hint: You must do this before readData !!
	 * 
	 * @return list of refId | could be empty
	 * </pre>
	 */
	public ArrayList<String> readDataList();

}
