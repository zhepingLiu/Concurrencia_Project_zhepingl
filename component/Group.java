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


	//create a new vessel with a given Id
	protected Group(int id) {
		this.id = id;
	}

	//get a new component.Group instance with a unique Id
	public static Group getNewGroup() {
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
}
