package component;

/**
 * A group of tourists, each with its unique id, who travel 
 * between the villages by train.
 * 
 * @author ngeard@unimelb.edu.au
 *
 */
public class Group {

	//a unique identifier for this tour group
	protected int id;
	//the next ID to be allocated
	protected static int nextId = 1;

	//EXTENSION : indicator of being short type
	private boolean shortType;

	//create a new vessel with a given Id
	protected Group(int id) {
		this.id = id;
	}

	//EXTENSION : create a new vessel  with a given id and
    //can be short or full type
	protected Group(int id, boolean shortType) {
	    this.id = id;
	    this.shortType = shortType;
    }

	//get a new component.Group instance with a unique Id
	public static Group getNewGroup() {
	    //EXTENSION : get one short type group every 7 groups
	    if (nextId % 2 == 0) {
	        return new Group(nextId++, true);
        }
		return new Group(nextId++);
	}

	//produce the Id of this group
	public int getId() {
		return id;
	}

	//produce an identifying string for the group
	public String toString() {
		return "group [" + id + "]";
	}

	//return true if this group is short type
	public boolean isShort() {
        return shortType;
	}
}
