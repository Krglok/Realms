package net.krglok.realms.Common;


/**
 * <pre>
 * Independent type for blocks and items
 * make it possible to separate the model from the minecraft server
 * property:
 *  String sRef;
 *  Integer iValue;
 * @author krglok
 *
 * </pre>
 */
public class Item 
{
   private String sRef;
   private Integer iValue;
   
   public Item()
   {
	   sRef = "";
	   iValue = 0;
   }
   
   public Item(String itemRef, int value)
   {
	   this.sRef = itemRef;
	   this.iValue = value;
	   
   }
   
   public  String ItemRef()
   {
	   return sRef;
   }
   
   public Integer value()
   {
//	   System.out.println(iValue);
	   return iValue;
   }
   
   public void setItemRef(String itemRef)
   {
	   sRef = itemRef;
   }
   
   public void setValue(int value)
   {
	   iValue = value;
   }
   
}

