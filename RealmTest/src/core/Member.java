package core;


/**
 * 
 * @author oduda
 *
 */
public class Member 
{

	private int id;
	private MemberLevel level;
	private int capital;
	private String owner;
	
	public Member ()
	{
		setId(0);
		setLevel(new MemberLevel());
		setCapital(0);
		setOwner("");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MemberLevel getLevel() {
		return level;
	}

	public void setLevel(MemberLevel level) {
		this.level = level;
	}

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
     	

}
