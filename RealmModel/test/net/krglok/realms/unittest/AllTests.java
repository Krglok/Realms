package net.krglok.realms.unittest;




import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	PositionTest.class,
	TestNPCOwner.class, 
	TestPCOwner.class, 
	OwnerListTest.class,
	RealmTest.class,
	RealmListTest.class,

	UnitListTest.class, 
	BankTest.class,
	BuildingListTest.class,
	ItemListTest.class,
	TownhallTest.class,
	
	DataTestTest.class,

	SettlementTest.class,
	SettlementListTest.class
	
	})
public class AllTests
{

}
