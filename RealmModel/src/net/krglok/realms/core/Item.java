package net.krglok.realms.core;


/**
 * 
 * @author oduda
 *
 */
public class Item 
{
   private String sRef;
   private int iValue;
   
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
   
   public int value()
   {
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

