package core;

/**
 * 
 * @author oduda
 *
 */
public class MemberLevel 
{
	final int MEMBER_NONE = -1;
	final int MEMBER_SETTLER = 0;
	final int MEMBER_KNIGHT = 10;
	final int MEMBER_LOWLORD = 20;
	final int MEMBER_LORD = 30;
	final int MEMBER_KING = 40;

	private int value;
	
	public MemberLevel ()
	{
		value = MEMBER_NONE;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
		
//	public int mlNone()
//	{
//		return MEMBER_NONE;
//	}
//	
//	public int mlSettler()
//	{
//		return MEMBER_SETTLER;
//	}
//	
//	public int mlKnight()
//	{
//		return MEMBER_KNIGHT;
//	}
//	
//	public int mlLowLord()
//	{
//		return MEMBER_LOWLORD;
//	}
//	
//	public int mlLord()
//	{
//		return MEMBER_LORD;
//	}
//	
//	public int mlKing()
//	{
//		return MEMBER_KING;
//	}
}
