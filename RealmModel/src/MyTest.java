import net.krglok.realms.core.Realm;
import net.krglok.realms.core.RealmList;
import net.krglok.realms.core.RealmModel;


public class MyTest
{

	private static RealmModel realmModel;
	private static InitTestOwner NPCOwner;
	private static String exceptionText = "";
	RealmList realmList = new RealmList(0);

	private  void initTest()
	{
		System.out.println("Modultest 1 ");
		
		Realm realm = new Realm();
		realmList.addRealm(realm);
		realmList.addRealm(new Realm());
		realmList.addRealm(new Realm());
		realmList.addRealm(new Realm());
		int expected = 4;
		int actual = realmList.size();
		System.out.println("actual "+actual);
	}
	

	private  void checkTest()
	{
		MyList itemList = new MyList();
		 
		String key = "1";
		int value  = 1 * 10;
		itemList.put(key, value);
		for (int i = 0; i < 5; i++)
		{
			itemList.put(String.valueOf(i), i*10);
		}
		

		System.out.println("Modultest 2 ");
		int expected = 0;
		int actual = realmList.size();
		System.out.println("actual "+actual);
		for (String itemRef : itemList.keySet())
		{
			System.out.println(itemRef+" : "+itemList.get(itemRef));
		}
		
	}
	
	public  MyTest ( )
	{
		
	}
	
	 public static void main(String[] args)
	 {
		 MyTest test = new MyTest();
		 
		 
			try
			{
				test.initTest();
				test.checkTest();
			}
			finally
			{
				exceptionText = "Keine Exception";
				
			}
			
			// halo welt
			System.out.println("Exception          "+exceptionText);
		 
	 }
	
}
